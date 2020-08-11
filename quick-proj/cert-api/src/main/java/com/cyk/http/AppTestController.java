package com.cyk.http;


import com.cyk.common.ParamsUtil;
import com.cyk.ioclient.wskt.WebSocketClient;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


@RestController
public class AppTestController {


    @Value("${mms.ws.ipport:ws://192.168.6.238:11003/mms/}")
    String uri;





    @RequestMapping(value = "/app/mms/test", method = {RequestMethod.GET, RequestMethod.POST})
    public Object testNtWsktApiInvoke(HttpServletRequest request) {
        String flag = request.getParameter("flag");
        JSONObject reqJson = new JSONObject();
        String result = "";

        try{
            String ctx = Base64.getEncoder().encodeToString("testdata-111111111111111111L".getBytes("UTF-8"));
            String svData = "{\"code\":0,\"msg\":\"SUCCESS\",\"data\":\"MEUCIQCJQIhw3HLUk1/gLdGidBvD8MjWABf+mRszTpX4iaXs3AIgLmMZ8D6Ub/ULAcJU20kS8TfkvPhN0dOnjjI0ze5nZwE=\"}";

            JSONObject svRtnJson = JSONObject.fromObject(svData);
            String data = svRtnJson.getString("data");
            String func = "0".equals(flag)?  "eccSign":"eccVerify";

            reqJson.put("function", func);
            reqJson.put("index", 1);
            reqJson.put("data", ctx);
            reqJson.put("sv", data);
            reqJson.put("pkPre", true);

            result = WebSocketClient.sendMsg(reqJson.toString());


        }catch (Exception e){
            e.printStackTrace();
            result = e.getMessage();
        }

        Map<String, Object> rtnMap = new HashMap<>();
        if(ParamsUtil.checkNull(result)){
            rtnMap.put("code", -1);
            rtnMap.put("msg", "mms返回数据为空");
            rtnMap.put("data", result);
        }else{
            try{
                return JSONObject.fromObject(result);
            }catch (Exception e){
                e.printStackTrace();
                rtnMap.put("code", -1);
                rtnMap.put("msg", "mms反馈错误");
                rtnMap.put("data", e.getMessage());
            }


        }
        return rtnMap;
    }





}
