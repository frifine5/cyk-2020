package com.cyk.file;


import com.cyk.common.ParamsUtil;
import com.cyk.common.ex.NullResultException;
import com.cyk.common.ex.OptFailException;
import com.cyk.file.entity.PsFileEntity;
import com.cyk.file.service.PsFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 文件上传下载
 *
 * @author WangChengyu
 * 2020/5/22 15:52
 */
@RestController
public class FileController {

    Logger logger = LoggerFactory.getLogger(FileController.class);


    @Autowired
    PsFileService psFileService;

    @RequestMapping(value = "/file/psFileUpload", method = {RequestMethod.POST})
    @ResponseBody
    public Object uploadPsFile(@RequestParam(value = "file") MultipartFile file, HttpServletRequest req) {
        String uid = req.getParameter("uid");

        Map<String, Object> rsMap = new HashMap<>();
        try {
            byte[] fileData = file.getBytes();
            String oriFileName = file.getOriginalFilename();
            logger.info("fileName= {}, fileLength= {}, uid= {}", oriFileName, fileData.length, uid);

            if (!oriFileName.endsWith(".pdf")) {
                rsMap.put("code", -1);
                rsMap.put("msg", "不是pdf文件，上传失败");
            } else {
                psFileService.uploadFile(uid, fileData, oriFileName);
                rsMap.put("code", 0);
                rsMap.put("msg", "SUCCESS");

            }

        } catch (OptFailException oe) {
            rsMap.put("code", -1);
            rsMap.put("msg", "上传失败:" + oe.getMessage());
        } catch (Exception e) {
            logger.info("文件上传失败", e);
            rsMap.put("code", -1);
            rsMap.put("msg", "上传失败:" + e.getMessage());
        }
        return rsMap;
    }


    @RequestMapping(value = "/file/psPreFiles", method = {RequestMethod.POST})
    @ResponseBody
    public Object loadPsPreFile(HttpServletRequest req) {
        String uid = req.getParameter("uid");
        String index = req.getParameter("index");

        Map<String, Object> rsMap = new HashMap<>();
        try {
            List<PsFileEntity> rtnList = psFileService.queryPreFiles(uid, ParamsUtil.checkNull(index) ? -1 : Integer.parseInt(index));
            rsMap.put("code", 0);
            rsMap.put("msg", "SUCCESS");
            rsMap.put("data", rtnList);

        } catch (NullResultException oe) {
            rsMap.put("code", 0);
            rsMap.put("msg", "您暂时没有待签文件");
        } catch (Exception e) {
            logger.info("文件集查询失败", e);
            rsMap.put("code", -1);
            rsMap.put("msg", "查询失败:" + e.getMessage());
        }
        return rsMap;
    }

    // delPreFile
    @RequestMapping(value = "/file/delPreFile", method = {RequestMethod.POST})
    @ResponseBody
    public Object delPreFile(HttpServletRequest req) {
        String uid = req.getParameter("uid");
        String uuid = req.getParameter("uuid");

        Map<String, Object> rsMap = new HashMap<>();
        try {
            psFileService.delOnePreFile(uuid);
            rsMap.put("code", 0);
            rsMap.put("msg", "SUCCESS");

        } catch (OptFailException oe) {
            rsMap.put("code", 0);
            rsMap.put("msg", "无效删除操作：" + oe.getMessage());
        } catch (Exception e) {
            logger.info("删除操作失败", e);
            rsMap.put("code", -1);
            rsMap.put("msg", "查询失败:" + e.getMessage());
        }
        return rsMap;
    }


    @RequestMapping(value = "/file/psSignFiles", method = {RequestMethod.POST})
    @ResponseBody
    public Object loadPsSignedFile(HttpServletRequest req) {
        String uid = req.getParameter("uid");
        String index = req.getParameter("index");

        Map<String, Object> rsMap = new HashMap<>();
        try {
            List<PsFileEntity> rtnList = psFileService.querySignFiles(uid, ParamsUtil.checkNull(index) ? -1 : Integer.parseInt(index));
            rsMap.put("code", 0);
            rsMap.put("msg", "SUCCESS");
            rsMap.put("data", rtnList);

        } catch (NullResultException oe) {
            rsMap.put("code", 0);
            rsMap.put("msg", "您还没有已签文件");
        } catch (Exception e) {
            logger.info("文件集查询失败", e);
            rsMap.put("code", -1);
            rsMap.put("msg", "查询失败:" + e.getMessage());
        }
        return rsMap;
    }





}
