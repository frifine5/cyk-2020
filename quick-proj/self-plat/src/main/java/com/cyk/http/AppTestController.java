package com.cyk.http;


import com.cyk.common.ParamsUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
public class AppTestController {





    @RequestMapping(value = "/app/rev", method = {RequestMethod.GET, RequestMethod.POST})
    public Object revMsg4lApp(HttpServletRequest request) {
        String s = "I got you in time: " + new Date();
        String msg = request.getParameter("msg");
        System.out.println("I got msg from app: " + msg);


        return msg;
    }




}
