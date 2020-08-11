package com.cyk.file;


import com.cyk.common.ParamsUtil;
import com.cyk.common.ex.NullResultException;
import com.cyk.common.ex.OptFailException;
import com.cyk.file.entity.PsFileEntity;
import com.cyk.file.rr.CacheImg;
import com.cyk.file.service.PsFileOptService;
import com.cyk.file.service.PsFileService;
import com.cyk.image.rr.SealImg;
import com.cyk.image.service.PersonSealService;
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
 * 文件操作类： 转图片、加载图片
 *
 * @author WangChengyu
 * 2020/5/22 15:52
 */
@RestController
public class FileOptController {

    Logger logger = LoggerFactory.getLogger(FileOptController.class);


    @Autowired
    PsFileOptService psFileOptService;


    /**
     * 加载文件分页
     */
    @RequestMapping(value = "/fileOpt/cachePsFileImgs", method = {RequestMethod.POST})
    @ResponseBody
    public Object cacheFileImgs(HttpServletRequest req) {
        String fileId = req.getParameter("fileId");
        Map<String, Object> rsMap = new HashMap<>();
        try {
            int pages = psFileOptService.cacheFile(fileId);
            if (pages < 1) {
                rsMap.put("code", -1);
                rsMap.put("msg", "文件不存在");
            } else {
                rsMap.put("code", 0);
                rsMap.put("msg", "SUCCESS");
                rsMap.put("data", pages);
            }
        } catch (NullResultException oe) {
            rsMap.put("code", 0);
            rsMap.put("msg", "文件为空：" + oe.getMessage());
        } catch (Exception e) {
            logger.info("文件转换失败", e);
            rsMap.put("code", -1);
            rsMap.put("msg", "文件转换失败:" + e.getMessage());
        }
        return rsMap;
    }

    /**
     * 加载文件分页
     */
    @RequestMapping(value = "/fileOpt/getCachePsFileImg", method = {RequestMethod.POST})
    @ResponseBody
    public Object loadCacheFileImg(HttpServletRequest req) {
        String fileId = req.getParameter("fileId");
        String pageNo = req.getParameter("pageNo");
        Map<String, Object> rsMap = new HashMap<>();
        try {
            CacheImg cacheImg = psFileOptService.loadCacheFile(fileId, Integer.parseInt(pageNo));
            if (null == cacheImg) {
                rsMap.put("code", -1);
                rsMap.put("msg", "文件不存在");
            } else {
                rsMap.put("code", 0);
                rsMap.put("msg", "SUCCESS");
                rsMap.put("data", cacheImg);
            }
        } catch (NullResultException oe) {
            rsMap.put("code", 0);
            rsMap.put("msg", "文件为空：" + oe.getMessage());
        } catch (Exception e) {
            logger.info("文件转换失败", e);
            rsMap.put("code", -1);
            rsMap.put("msg", "文件转换失败:" + e.getMessage());
        }
        return rsMap;
    }


    @Autowired
    PersonSealService personSealService;


    /**
     * 获得用户所有有效权限的印章图片列表（有图，有章且有效）
     */
    @RequestMapping(value = "/fileOpt/getPsAvaSeals", method = {RequestMethod.POST})
    @ResponseBody
    public Object loadAvaSeals(HttpServletRequest req) {
        String uid = req.getParameter("uid");
        Map<String, Object> rsMap = new HashMap<>();
        try {
            List<SealImg> sealList = personSealService.getAvaliable(uid);
            if (null == sealList) throw new NullResultException("没有可用的章");

            rsMap.put("code", 0);
            rsMap.put("msg", "SUCCESS");
            rsMap.put("data", sealList);

        } catch (NullResultException oe) {
            rsMap.put("code", -1);
            rsMap.put("msg", oe.getMessage());
        } catch (Exception e) {
            logger.info("文件转换失败", e);
            rsMap.put("code", -1);
            rsMap.put("msg", "文件转换失败:" + e.getMessage());
        }
        return rsMap;
    }







}
