package com.cyk.image;


import com.cyk.common.FileUtil;
import com.cyk.common.ParamsUtil;
import com.cyk.common.ex.NullResultException;
import com.cyk.common.ex.OptFailException;
import com.cyk.image.entity.PsSealImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Base64;
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
public class PsSealController {

    Logger logger = LoggerFactory.getLogger(PsSealController.class);


    @Autowired
    SealImageService sealImageService;




    @RequestMapping(value = "/seal/psPreImage", method = {RequestMethod.POST})
    @ResponseBody
    public Object psPreSealImage(HttpServletRequest req) {
        String uid = req.getParameter("uid");

        Map<String, Object> rsMap = new HashMap<>();
        try {
            List<PsSealImage> psImages = sealImageService.getPsImages(uid);

            rsMap.put("code", 0);
            rsMap.put("msg", "SUCCESS");
            rsMap.put("data", psImages);
        } catch (Exception e) {
            logger.info("预览操作失败", e);
            rsMap.put("code", -1);
            rsMap.put("msg", "查找失败:" + e.getMessage());
        }
        return rsMap;
    }


    @RequestMapping(value = "/seal/psImageGen", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object genPsSeal(HttpServletRequest req) {
        String uid = req.getParameter("uid");
        Map<String, Object> rsMap = new HashMap<>();
        try {
            sealImageService.genPsSealImage(uid, true, 100, 100, 0);
            rsMap.put("code", 0);
            rsMap.put("msg", "SUCCESS");
        } catch (Exception e) {
            logger.info("操作失败", e);
            rsMap.put("code", -1);
            rsMap.put("msg", "生成图片失败:" + e.getMessage());
        }
        return rsMap;
    }


    @RequestMapping(value = "/seal/optPsImage", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object optPsSeal(HttpServletRequest req) {
        String id = req.getParameter("id");
        String status = req.getParameter("sts");
        Map<String, Object> rsMap = new HashMap<>();
        if(ParamsUtil.checkNull(id, status)){
            rsMap.put("code", -1);
            rsMap.put("msg", "您的参数有误");
        }
        try {
            sealImageService.optPsImage(id, Integer.parseInt(status));
            rsMap.put("code", 0);
            rsMap.put("msg", "SUCCESS");
        } catch (Exception e) {
            logger.info("操作失败", e);
            rsMap.put("code", -1);
            rsMap.put("msg", "更新失败:" + e.getMessage());
        }
        return rsMap;
    }


    @RequestMapping(value = "/seal/delPsImage", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object delPsSeal(HttpServletRequest req) {
        String id = req.getParameter("id");
        Map<String, Object> rsMap = new HashMap<>();
        if(ParamsUtil.checkNull(id)){
            rsMap.put("code", -1);
            rsMap.put("msg", "您的参数有误");
        }
        try {
            sealImageService.delPsImage(id);
            rsMap.put("code", 0);
            rsMap.put("msg", "SUCCESS");
        } catch (Exception e) {
            logger.info("操作失败", e);
            rsMap.put("code", -1);
            rsMap.put("msg", "生成图片失败:" + e.getMessage());
        }
        return rsMap;
    }



    @RequestMapping(value = "/file/psSealUpload", method = {RequestMethod.POST})
    @ResponseBody
    public Object uploadPsSealFile(@RequestParam(value = "file") MultipartFile file, HttpServletRequest req) {
        String uid = req.getParameter("uid");

        Map<String, Object> rsMap = new HashMap<>();
        try {
            byte[] fileData = file.getBytes();
            String oriFileName = file.getOriginalFilename();
            logger.info("fileName= {}, fileLength= {}, uid= {}", oriFileName, fileData.length, uid);

            if (oriFileName.endsWith(".png") || oriFileName.endsWith(".PNG")) {
                String imgName = oriFileName.substring(0, oriFileName.length()-4);
                sealImageService.uploadSealImage(uid, fileData, imgName, 1);
                rsMap.put("code", 0);
                rsMap.put("msg", "SUCCESS");
            } else {
                rsMap.put("code", -1);
                rsMap.put("msg", "不是png文件，上传失败");
            }
        } catch (Exception e) {
            logger.info("文件上传失败", e);
            rsMap.put("code", -1);
            rsMap.put("msg", "上传失败:" + e.getMessage());
        }
        return rsMap;
    }



    @RequestMapping(value = "/file/pshSignUpload", method = {RequestMethod.POST})
    @ResponseBody
    public Object uploadPsSignFile(@RequestParam(value = "file") MultipartFile file, HttpServletRequest req) {
        String uid = req.getParameter("uid");

        Map<String, Object> rsMap = new HashMap<>();
        try {
            byte[] fileData = file.getBytes();
            String oriFileName = file.getOriginalFilename();
            logger.info("fileName= {}, fileLength= {}, uid= {}", oriFileName, fileData.length, uid);

            if (oriFileName.endsWith(".png") || oriFileName.endsWith(".PNG")) {
                String imgName = oriFileName.substring(0, oriFileName.length()-4);
                sealImageService.uploadSealImage(uid, fileData, imgName, 2);
                rsMap.put("code", 0);
                rsMap.put("msg", "SUCCESS");
            } else {
                rsMap.put("code", -1);
                rsMap.put("msg", "不是png文件，上传失败");
            }
        } catch (Exception e) {
            logger.info("文件上传失败", e);
            rsMap.put("code", -1);
            rsMap.put("msg", "上传失败:" + e.getMessage());
        }
        return rsMap;
    }





}
