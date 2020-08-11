package com.cyk.cert;


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
 * ra： 页面用户入口
 */
@RestController
public class UserRaBizController {

    Logger logger = LoggerFactory.getLogger(UserRaBizController.class);

    @Autowired
    CertBusinessService certBusinessService;


    @RequestMapping(value = "/ura/certService/ra", method = {RequestMethod.POST})
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

            //  使用登录用的无需鉴定项目权限
            // todo
            Object rtObj = null;


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
