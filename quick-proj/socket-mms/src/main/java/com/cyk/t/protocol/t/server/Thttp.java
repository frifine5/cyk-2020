package com.cyk.t.protocol.t.server;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class Thttp {

    Logger logger = LoggerFactory.getLogger(Thttp.class);

    @RequestMapping(value = "/test",method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object test(){
        String info = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        logger.info("收到消息[{}]", info);

        return info;
    }



    @CrossOrigin(origins = "*", allowCredentials="true", allowedHeaders = "", methods = {RequestMethod.PUT, RequestMethod.POST})
    @RequestMapping(value = "/test/param",method = { RequestMethod.POST})
    @ResponseBody
    public Object testPut(@RequestBody String param){
        String ts = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        logger.info("收到消息[{}]", param);
        Map<String, Object> result = new HashMap<>();
        result.put("errorCode", 0);
        result.put("message", "0k");
        result.put("time", ts);
        result.put("data", "");


        return result;
    }


    @Value("${data.mock.path:/mnt/mock/}")
    String mockRoot;


    private String extractPathFromPattern(final HttpServletRequest request) {
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        return new AntPathMatcher().extractPathWithinPattern(bestMatchPattern, path);
    }

    @CrossOrigin(origins = "*", allowCredentials="true", allowedHeaders = "", methods = {RequestMethod.PUT, RequestMethod.POST})
    @RequestMapping(value = "/mock/{v}/**",method = { RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Object testPut(@PathVariable("v")String version, HttpServletRequest request){
        String uris = extractPathFromPattern(request);
        // mock 数据 不检查请求参数
        Map<String, Object> result = new HashMap<>();
        if(null == version || "".equalsIgnoreCase(version)){
            result.put("errorCode", -1);
            result.put("message", "未指定mock版本");
            return result;
        }
        if(null == uris || "".equalsIgnoreCase(uris)){
            result.put("errorCode", -1);
            result.put("message", "未指定mock接口");
            return result;
        }
        logger.info("加载mock数据-目录:[/{}/{}]", version, uris);
        String wholePath = String.format("%s/%s/%s",  mockRoot , version , uris);
        File f = new File(wholePath);
        if(!f.exists()|| !f.isFile()){
            result.put("errorCode", -1);
            result.put("message", "没有准备mock数据");
            return result;
        }
        try (FileInputStream fis = new FileInputStream(wholePath)){
            int len = fis.available();
            byte[] dataByts = new byte[len];
            fis.read(dataByts, 0, len);
            String data = new String(dataByts, "UTF-8");
            if(null != data && !"".equalsIgnoreCase(data)){
                data = data.trim();
                if(data.startsWith("{")){
                    data = data.replaceAll("\n", "")
                            .replaceAll("\r", "")
                            .replaceAll("\t", "")
                            .replaceAll("\\\\", "");
                }
            }
            result.put("errorCode", 0);
            result.put("message", "0k");
            try {
                JSONObject jsonObject = JSONObject.fromObject(data);
                result.put("data", jsonObject);
            }catch (Exception e){
                result.put("data", data);
            }
        }catch (Exception e){
            logger.error("mock失败", e);
            result.put("errorCode", -1);
            result.put("message", "没有准备mock数据");
        }
        return result;
    }

    @CrossOrigin(origins = "*", allowCredentials="true", allowedHeaders = "", methods = {RequestMethod.PUT, RequestMethod.POST})
    @RequestMapping(value = "/mockUploadData",  method = {RequestMethod.POST})
    @ResponseBody
    public Object uploadEncKey(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        String path = request.getParameter("mockFilePath");
        String version = request.getParameter("version");
        if(file.isEmpty() || null == path || "".equals(path) || null == version || "".equals(version) ){
            result.put("errorCode", -1);
            result.put("message", "mock数据和参数为空");
            return result;
        }
        String fileName = file.getOriginalFilename();
        if(null == fileName || "".equalsIgnoreCase(fileName)){
            result.put("errorCode", -1);
            result.put("message", "mock数据和参数为空");
            return result;
        }else if(fileName.indexOf(".")<1){
            result.put("errorCode", -1);
            result.put("message", "mock数据文件未指定后缀");
        }
        if(fileName.matches("^[a-zA-Z][a-zA-Z0-9._-]?")){
            result.put("errorCode", -1);
            result.put("message", "mock数据文件名不合法");
        }
        String wholeDir = "/mnt/mock/" + version + (path.startsWith("/")? path: "/"+path);
        String wholePath = wholeDir + "/" + fileName;
        if(new File(wholePath).exists()&& !"1".equalsIgnoreCase(request.getParameter("upt"))){
            result.put("errorCode", -1);
            result.put("message", "mock数据已存在，如需覆盖，请指定为更新");
            return result;
        }
        File dir = new File(wholeDir);
        if(!dir.exists()){
            dir.mkdirs();
        }
        try(OutputStream fos = new FileOutputStream(new File(wholePath))){
            byte[] data = file.getBytes();
            fos.write(data, 0, data.length);
            fos.flush();
            result.put("errorCode", 0);
            result.put("message", "0k");
            result.put("data", wholePath.substring(wholePath.indexOf("/mnt/mock/")+10));
        }catch (Exception e){
            logger.error("mock数据更新失败", e);
            result.put("errorCode", -1);
            result.put("message", "mock数据上传失败");

        }
        return result;
    }




    }
