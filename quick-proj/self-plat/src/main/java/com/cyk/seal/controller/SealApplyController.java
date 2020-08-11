package com.cyk.seal.controller;


import com.cyk.common.ParamsUtil;
import com.cyk.seal.entity.SealApplyEntity;
import com.cyk.seal.rr.ApplySealReq;
import com.cyk.seal.rr.ApySealItem;
import com.cyk.seal.service.SealApplyService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SealApplyController {

    Logger logger = LoggerFactory.getLogger(SealApplyController.class);


    @Autowired
    SealApplyService sealApplyService;


    @RequestMapping(value = "/user/seal/apply", method = { RequestMethod.POST})
    @ResponseBody
    public Object queryUserList(@RequestBody ApplySealReq req) {
        logger.info("获得的请求参数：" + JSONObject.fromObject(req));

        Map<String, Object> rtnMap = new HashMap<>();

        // check head
        if(ParamsUtil.checkNull(req.getDeptCode(), req.getDeptName(), req.getArea(), req.getTel(), req.getLinkman())){
            rtnMap.put("code", -1);
            rtnMap.put("msg", "参数错误或缺失");
            return rtnMap;
        }

        List<SealApplyEntity> apyList = new ArrayList<>();
        try {
            String deptName = req.getDeptName();
            String deptCode = req.getDeptCode();
            String area = req.getArea();
            String address = req.getAddress();
            String linkman = req.getLinkman();
            String tel = req.getTel();
            SealApplyEntity h = new SealApplyEntity();
            h.setDeptName(deptName);
            h.setDeptCode(deptCode);
            h.setAddress(address);
            h.setArea(area);
            h.setTel(tel);
            h.setLinkman(linkman);
            List<ApySealItem> items = req.getItems();
            // check items
            if(null == items || items.size()<1){
                rtnMap.put("code", -1);
                rtnMap.put("msg", "参数错误或缺失");
                return rtnMap;
            }else{
                List<String> sealNameList = new ArrayList<>();
                for (ApySealItem sealItem: items ) {
                    String theName = sealItem.getName();
                    if(sealNameList.contains(theName)){
                        rtnMap.put("code", -1);
                        rtnMap.put("msg", "参数错误：申请中印章名重复");
                        return rtnMap;
                    }else{
                        sealNameList.add(theName);
                        SealApplyEntity e = new SealApplyEntity(h);
                        e.setSealName(sealItem.getName());
                        e.setSealColor(ParamsUtil.checkNull(sealItem.getColor())?"红色":sealItem.getColor());
                        e.setValid(sealItem.getValid());
                        e.setPicWidth(sealItem.getWidth());
                        e.setPicHeight(sealItem.getHeight());
                        apyList.add(e);
                    }
                    if(sealApplyService.checkSealNameDupt(sealNameList)){
                        rtnMap.put("code", -1);
                        rtnMap.put("msg", "申请单错误：印章名已被占用");
                        return rtnMap;
                    }
                }
            }
            // arrive this meaning that all seal pass check, and then do apply record.
            sealApplyService.recordApplyList(apyList);

            logger.info(">>>>>>>>");
            logger.info(JSONArray.fromObject(apyList).toString());

            rtnMap.put("code", 0);
            rtnMap.put("msg", "SUCCESS");
            rtnMap.put("data", apyList);
        }catch (Exception e){
            logger.error("申请失败", e);
            rtnMap.put("code", -1);
            rtnMap.put("msg", "申请单参数错误或缺失，申请未被记录");
        }
        return rtnMap;
    }

    @RequestMapping(value = "/user/seal/upApplySealPic", method = { RequestMethod.POST})
    @ResponseBody
    public Object upApplySealPic(@RequestParam("file") MultipartFile file, HttpServletRequest request){
//        logger.info("获得的请求参数：" + req );
        logger.info(">> request: sealName::"+request.getParameter("sealName"));
        String orderId = request.getParameter("orderId");
        logger.info(">> request: orderId::"+ orderId);
        Map<String, Object> rtnMap = new HashMap<>();

        String fileName = file.getOriginalFilename();
        logger.info("上传的文件名：" + fileName);
        byte[] bytes = null;
        try(ByteArrayOutputStream oss = new ByteArrayOutputStream();){
            bytes = file.getBytes();
            logger.info("上传的文件长度：" + bytes.length);
            String type = fileName.substring(fileName.lastIndexOf(".")+1);
            logger.info(">>>> type=" + type);
            if("jpeg".equalsIgnoreCase(type) || "jpg".equalsIgnoreCase(type)){
                BufferedImage bi = ImageIO.read(file.getInputStream());
                ImageIO.write(bi, "png", oss);
                bytes = oss.toByteArray();
            }else if("png".equalsIgnoreCase(type)){
//                bytes = file.getBytes();  // bytes 不用动

            }else{
                logger.error("不支持的图片类型：" + type);
                rtnMap.put("code", -1);
                rtnMap.put("msg", "图片类型不支持");
                return rtnMap;
            }
            sealApplyService.uptSealPic(orderId, bytes, "png");
            rtnMap.put("code", 0);
            rtnMap.put("msg", "SUCCESS");
            rtnMap.put("data", orderId);
        }catch (Exception e){
            logger.error("", e);
            rtnMap.put("code", -1);
            rtnMap.put("msg", "图片上传失败");
            rtnMap.put("data", orderId);
        }
        return rtnMap;
    }



}
