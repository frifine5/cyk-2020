package com.cyk.file.service;


import com.cyk.common.FileUtil;
import com.cyk.common.ParamsUtil;
import com.cyk.common.ex.NullResultException;
import com.cyk.common.ex.OptFailException;
import com.cyk.file.dao.PsFileDao;
import com.cyk.file.entity.FileEntity;
import com.cyk.file.entity.PsFileEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class PsFileService {

    Logger logger = LoggerFactory.getLogger(PsFileService.class);

    @Autowired
    PsFileDao psFileDao;


    @Value("${file.root.path:/mnt/uss/files/}")
    String rootPath;


    public void delOnePreFile(String uuid) throws OptFailException {

        PsFileEntity toDelFile = psFileDao.findOneByid(uuid);
        // how to delete file on disk: really or logical
        try {
            String fn = toDelFile.getFilePath() + toDelFile.getFileName();
            logger.info("fullFileName = " + fn);
            File f = new File(fn);
            f.delete();

            int di = psFileDao.deletePreFileById(uuid);
            if (di < 1) {
                throw new OptFailException("删除记录小于1");
            }
        }catch (OptFailException oe){
            throw oe;
        }catch (Exception e){
            logger.error("删除失败:" + e.getMessage());
        }

    }

    public List<PsFileEntity> queryPreFiles(String uid, int index) throws NullResultException{
        List<PsFileEntity> qList;
        if(index < 0){
            qList = psFileDao.getOnePreFiles(uid);
        }else{
            qList = psFileDao.getOneMorePreFiles(uid, index);
        }
        if(ParamsUtil.checkListNull(qList)){
            throw new NullResultException("查询结果为空");
        }
        return qList;
    }


    public List<PsFileEntity> querySignFiles(String uid, int index) throws NullResultException{
        List<PsFileEntity> qList;
        if(index < 0){
            qList = psFileDao.getOneAllSignFiles(uid);
        }else{
            qList = psFileDao.getOneMoreSignFiles(uid, index);
        }
        if(ParamsUtil.checkListNull(qList)){
            throw new NullResultException("查询结果为空");
        }
        return qList;
    }


    public void uploadFile(String uid, byte[] files, String fileName)throws OptFailException{
        String fp = String.format("%sps/pre/%s/", rootPath , uid );
        String uuid = ParamsUtil.getUUIDStr();
        Date rdTime = new Date();
        File f = new File(fp);
        if(!f.isDirectory()){
            f.mkdirs();
        }

        File f2 = new File(fp + fileName);
        if(f2.exists()){
            fileName = fileName.substring(0, fileName.lastIndexOf("."));
            fileName += "-" + System.currentTimeMillis() + ".pdf";
        }
        try {
            FileUtil.writeInFiles(fp + fileName, files);
        } catch (IOException e) {
            logger.error("上传文档：写文件失败", e);
            throw new OptFailException("文件写入失败");
        }
        PsFileEntity fileRnd = new PsFileEntity();
        fileRnd.setUuid(uuid);
        fileRnd.setOwnerId(uid);
        fileRnd.setFileName(fileName);
        fileRnd.setFilePath(fp);
        fileRnd.setRdTime(rdTime);
        fileRnd.setSignedCode(0);
        int ra = psFileDao.addFileRnd(fileRnd);
        if(ra<1){
            throw new OptFailException("文件存储失败");
        }
    }




}
