package com.cyk.apira;


import com.cyk.cert.service.CertBusinessService;
import com.cyk.common.ParamsUtil;
import com.cyk.common.ex.NullResultException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * ra： api调用入口
 */
@RestController
public class ApiRaBizController {

    Logger logger = LoggerFactory.getLogger(ApiRaBizController.class);

    @Autowired
    CertBusinessService certBusinessService;

    @RequestMapping(value = "/api/certService/token", method = {RequestMethod.POST})
    @ResponseBody
    public Object getToken(@RequestBody String reqBody){
        Map<String, Object> rtnMap = new HashMap<>();
        try{
            JSONObject reqJson = JSONObject.fromObject(reqBody);
            String rentId = reqJson.getString("rentId");
            String projId = reqJson.getString("projId");
            String rndA = reqJson.getString("rndA");
            if(ParamsUtil.checkNullSymbal(rentId, projId, rndA) ){
                rtnMap.put("code", -1);
                rtnMap.put("msg", "参数错误");
                return rtnMap;
            }

            // 根据请求产生token
            String data = certBusinessService.chkRentAndGenToken(rentId, projId, rndA);
            if(ParamsUtil.checkNull(data)) throw new NullResultException("token生成为空");
            rtnMap.put("code", 0);
            rtnMap.put("msg", "成功");
            rtnMap.put("data", data);
            return rtnMap;
        }catch (Exception e){
            logger.error("获取token失败", e);
            rtnMap.put("code", -1);
            rtnMap.put("msg", "获取token失败：" + e.getMessage());
            return rtnMap;
        }
    }


    @RequestMapping(value = "/api/certService/ra", method = {RequestMethod.POST})
    @ResponseBody
    public Object businessHandle(@RequestBody String reqBody){
        Map<String, Object> rtnMap = new HashMap<>();
        try{
            JSONObject reqJson = JSONObject.fromObject(reqBody);
            String token = reqJson.getString("token");
            String data = reqJson.getString("data");
            String signValue = reqJson.getString("signValue");
            if(ParamsUtil.checkNullSymbal(token, data, signValue)){
                rtnMap.put("code", -1);
                rtnMap.put("msg", "参数错误");
                return rtnMap;
            }

            // token验证后分发任务
            Object rtObj = certBusinessService.vsAndBizDivide(token, data, signValue);
            rtnMap.put("code", 0);
            rtnMap.put("msg", "成功");
            if( null != rtObj){
                rtnMap.put("data", rtObj);
            }
            return rtnMap;
        }catch (Exception e){
            logger.error("ra业务执行异常", e);
            rtnMap.put("code", -1);
            rtnMap.put("msg", "请求失败：" + e.getMessage());
            return rtnMap;
        }
    }



}
