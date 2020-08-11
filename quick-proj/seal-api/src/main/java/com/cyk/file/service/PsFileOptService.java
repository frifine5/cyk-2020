package com.cyk.file.service;


import com.cyk.common.ex.NullResultException;
import com.cyk.common.ex.OptFailException;
import com.cyk.common.global.LocalCacheRepo;
import com.cyk.file.dao.PsFileDao;
import com.cyk.file.entity.PsFileEntity;
import com.cyk.file.rr.CacheImg;
import com.itextpdf.text.pdf.PdfReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class PsFileOptService {

    Logger logger = LoggerFactory.getLogger(PsFileOptService.class);

    @Autowired
    PsFileDao psFileDao;


    @Value("${file.root.path:/mnt/uss/files/}")
    String rootPath;


    @Autowired
    AsyncCacheFileService asyncCacheFileService;

    @Autowired
    Pdf2PngService pdf2PngService;

    PsFileEntity findOneFile(String uuid) throws NullResultException, OptFailException{
        PsFileEntity ndFileRnd =  psFileDao.findOneByid(uuid);
        if(null == ndFileRnd) throw new NullResultException("未找到文件记录");
        String wfp = ndFileRnd.getFilePath() + ndFileRnd.getFileName();
        File f = new File(wfp);
        if(!f.exists() || !f.isFile())  throw new NullResultException("文件已删除");
        return ndFileRnd;
    }


    /**
     * 文件转图片并缓存
     * @param uuid
     * @return
     * @throws NullResultException
     * @throws OptFailException
     */
    public int cacheFile(String uuid) throws NullResultException, OptFailException{
        PsFileEntity ndFileRnd = findOneFile(uuid);
        String wfp = ndFileRnd.getFilePath() + ndFileRnd.getFileName();
        String cacheKey = uuid + "-pages";
        try {
            PdfReader pdfReader = new PdfReader(wfp);
            int pages = pdfReader.getNumberOfPages();
            LocalCacheRepo.putCache(cacheKey, pages, 30*60); // 30分钟
            pdfReader.close();

            asyncCacheFileService.cachePdfimgToLocalCache(uuid, wfp);
            return pages;
        } catch (IOException e) {
            logger.error("pdf文件读取失败", e);
            throw new OptFailException("pdf文件读取失败" + e.getMessage());
        }
    }




    public CacheImg loadCacheFile(String uuid, int pageNo) throws NullResultException, OptFailException{
        String cacheKey = uuid + "-no." + pageNo;
        Object cache = LocalCacheRepo.getCache(cacheKey);
        if(null == cache){// 重新转换
            PsFileEntity ndFileRnd = findOneFile(uuid);
            String wfp = ndFileRnd.getFilePath() + ndFileRnd.getFileName();
            try (OutputStream out = new ByteArrayOutputStream();){
                BufferedImage image = pdf2PngService.convertPdfToPngNum2Num(wfp, pageNo);
                int width = image.getWidth();
                int height = image.getHeight();
                ImageIO.write(image, "png", out);
                byte[] ecByts = ((ByteArrayOutputStream) out).toByteArray();
                CacheImg rtImg = new CacheImg(cacheKey, pageNo, width, height, ecByts);
                LocalCacheRepo.putCache(cacheKey, rtImg, 10*60);
                logger.info(String.format("文件[%s]: 缓存图片参数{no=%s, w=%s, h=%s}", uuid, pageNo, width, height ));
                return rtImg;
            } catch (IOException e) {
                logger.error("pdf文件读取失败", e);
                throw new OptFailException("pdf文件读取失败" + e.getMessage());
            }
        }else{
            try{
                return (CacheImg) cache;
            }catch (Exception e){
                throw new OptFailException("图片已失效");
            }
        }
    }





}
