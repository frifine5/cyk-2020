package com.cyk.http;


import com.cyk.per.p.dao.PersonUserDao;
import com.cyk.per.p.entity.PersonUser;
import com.cyk.per.p.service.PersonBizService;
import com.cyk.per.p.service.PersonKgsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AppTestController {

    @RequestMapping(value = "/app/rev", method = {RequestMethod.GET,RequestMethod.POST})
    public Object revMsg4lApp(HttpServletRequest request){
        String s = "I got you in time: "+new Date();
        String msg = request.getParameter("msg");
        System.out.println("I got msg from app: "+msg);
        return s;
    }


    @RequestMapping(value = "/app/revIp", method = {RequestMethod.GET,RequestMethod.POST})
    public Object revMsg4lApp2(HttpServletRequest request){
        String s = "I got you in time: "+new Date();

        String remoteAddr = request.getRemoteAddr();
        System.out.println(String.format("客户端IP = %s", remoteAddr));


        return s +"\n"+ remoteAddr;
    }



    @Autowired
    PersonUserDao personUserDao;

    @RequestMapping(value = "/test/addOneUser", method = {RequestMethod.GET,RequestMethod.POST})
    public Object testPUser(HttpServletRequest request){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        PersonUser pUser = new PersonUser();
        pUser.setName("测试人员");
        pUser.setCode("" + System.currentTimeMillis());
        pUser.setCrtTime(sdf.format(new Date()));

        int ra = personUserDao.addOne(pUser);

        return ra;
    }

    @Autowired
    PersonBizService personBizService;

    @RequestMapping(value = "/test/genUserStampImage", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Object testPUserImage(HttpServletRequest request){

        try{
            personBizService.genPsImageAndSave(Integer.parseInt(request.getParameter("id")));
            return "success";
        }catch (Exception e){
            return e.getMessage();
        }
    }

    @RequestMapping(value = "/test/uptUserStampHandSign", method = {RequestMethod.POST})
    @ResponseBody
    public Object testUptUserImage(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        try{
            byte[] imgData =  file.getBytes();
            int id = Integer.parseInt(request.getParameter("id"));

            personBizService.upPSignAndSave(id, imgData);

            return "success";
        }catch (Exception e){
            return e.getMessage();
        }
    }

    @Autowired
    PersonKgsService psKgsService;
    

    @RequestMapping(value = "/test/genUserStampOnRsa", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Object testPUserStampOnRsa(HttpServletRequest request){
        Map<String, Object> result = new HashMap<>();
        try{
            String stampId = psKgsService.genKpOfRsa(Integer.parseInt(request.getParameter("id")));
            
            result.put("code", 0);
            result.put("msg", "success");
            result.put("data", stampId);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.put("code", -1);
            result.put("msg", "私人章密钥申请失败");
            return result;
        }
    }


}
