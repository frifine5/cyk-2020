package com.cyk.t.protocol.mws;

import com.cyk.t.protocol.util.ExceptionUtil;
import com.cyk.t.protocol.util.ParamsUtil;
import com.cyk.t.protocol.util.WebsocketUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;


@Component
@ServerEndpoint("/mms/{name}")
public class WebSocketMmsServer {

    Logger log = LoggerFactory.getLogger(WebSocketMmsServer.class);


    /**
     * 与某个客户端的连接对话，需要通过它来给客户端发送消息
     */
    private Session session;

    /**
     * 标识当前连接客户端的用户名
     */
    private String name;

    /**
     * 用于存所有的连接服务的客户端，这个对象存储是安全的
     */
    private static ConcurrentHashMap<String, WebSocketMmsServer> webSocketSet = new ConcurrentHashMap<>();


    @OnOpen // 客端连接
    public void OnOpen(Session session, @PathParam(value = "name") String name) {
        this.session = session;
        this.name = name;
        String rmtIp = WebsocketUtil.getRemoteAddress(session).toString();
        // name是用来表示唯一客户端，如果需要指定发送，需要指定发送通过name来区分
        webSocketSet.put(name, this);
        log.info("[WebSocket][name={}, ip={}] 连接成功，当前连接人数为：={}", name, rmtIp, webSocketSet.size());
    }


    @OnClose
    public void OnClose() {
        webSocketSet.remove(this.name);
        String rmtIp = WebsocketUtil.getRemoteAddress(session).toString();
        log.info("[WebSocket][ip={}] 退出成功，当前连接人数为：={}", rmtIp, webSocketSet.size());
        // list rest connect name
        log.info("[WebSocket] 剩余连接名:\t" + Arrays.toString( webSocketSet.keySet().toArray() ));
    }

    @OnMessage
    public void OnMessage(String message) {
        log.info("[WebSocket] 收到消息：{}", message);
        String func = "";
        JSONObject rtnJson = new JSONObject();
        try {
            JSONObject reqJson = JSONObject.fromObject(message);
            func = reqJson.getString("function");
            if (ParamsUtil.checkNull(func) || "null".equalsIgnoreCase(func)) {
                rtnJson.put("code", -1);
                rtnJson.put("msg", "function is null");
            }

        } catch (Exception e) {
            rtnJson.put("code", -1);
            rtnJson.put("msg", "your parameter is illegal");
        }
        if (ParamsUtil.checkNull(func)) {
            // to return result;

        } else {
            try {
                Object result = null;
                MmsServiceNavigate mmsServiceNavigate = new MmsServiceNavigate(); // 敲黑板，执行类必须是已经实例化的对象。

                boolean hasHunc = false;
                for (Method m : MmsServiceNavigate.class.getDeclaredMethods()) {
                    String funName = m.getName();
                    MmsFunc uc = m.getAnnotation(MmsFunc.class);
                    if (uc != null) {
                        if (funName.equals(func)) {// 带标记的方法不要重载
                            hasHunc = true;

                            result = m.invoke(mmsServiceNavigate, message);

                        }
                    }
                }
                if (!hasHunc) {
                    rtnJson.put("code", -1);
                    rtnJson.put("msg", "没有方法[" + func +"]的处理能力");
                } else {
                    if (null == result) {
                        rtnJson.put("code", 0);
                        rtnJson.put("msg", "执行结果为空");
                    } else {
                        rtnJson.put("code", 0);
                        rtnJson.put("msg", "SUCCESS");
                        rtnJson.put("data", result);
                    }
                }
            } catch (Exception e) {
                log.error("exception happen in function executing", e);
                rtnJson.put("code", -1);
                String errMsg = ExceptionUtil.getCouseStrackString(e, null);
                rtnJson.put("msg", errMsg);
            }

        }

        try {
            Session session = webSocketSet.get(name).session;
            synchronized (session) {
                session.getBasicRemote().sendText(rtnJson.toString());
            }
        } catch (Exception e) {
            log.error("exception happen in return handling", e);
        }
    }


}
