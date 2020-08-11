package com.cyk.ioclient.wskt;

import com.cyk.common.ParamsUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.concurrent.*;

public class WebSocketClient {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketClient.class);
    private static ExecutorService executor = Executors.newCachedThreadPool();

    static Channel channel = null;
    static WebSocketClientHandler handler = null;

    private static String uri ;
    private static CountDownLatch latch;
    private static ClientInitializer clientInitializer;

    private WebSocketClient(){
    }
    public WebSocketClient(String uri){
        this.uri = uri;
        latch = new CountDownLatch(0);
        clientInitializer = new ClientInitializer(latch);
    }

    private static class SingletonHolder {
        static final WebSocketClient instance = new WebSocketClient();
    }
    public static WebSocketClient getInstance(){
        return SingletonHolder.instance;
    }


    public static void flushConn(){
        try{// 关闭旧的
           channel.close();
        }catch (Exception e){}

        // new
        try{// 关闭旧的
            initClientLoop(uri);
        }catch (Exception e){

        }

    }

    public static void initClientLoop(String uri ) throws URISyntaxException, InterruptedException {
        new WebSocketClient(uri);
        EventLoopGroup group=new NioEventLoopGroup();
        Bootstrap boot=new Bootstrap();
        boot.option(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.TCP_NODELAY,true)
                .option(ChannelOption.SO_BACKLOG,1024*1024*10)
                .group(group)
                .handler(new LoggingHandler(LogLevel.INFO))
                .channel(NioSocketChannel.class)
                .handler(new ClientInitializer(latch));
        URI websocketURI = new URI(uri);
        HttpHeaders httpHeaders = new DefaultHttpHeaders();

        //进行握手
         logger.info("connect to server....");
        channel = boot.connect(websocketURI.getHost(),websocketURI.getPort()).sync().channel();
        handler = (WebSocketClientHandler)channel.pipeline().get("websocketHandler");

        WebSocketClientHandshaker handshaker = WebSocketClientHandshakerFactory.newHandshaker(websocketURI, WebSocketVersion.V13, (String)null, true, httpHeaders);
        handler.setHandshaker(handshaker);
        handshaker.handshake(channel);
        //阻塞等待是否握手成功
        handler.handshakeFuture().sync();
    }

    public static String sendMsg(Channel channel, WebSocketClientHandler handler, String content) throws InterruptedException {

        logger.info("send msg:" + content);
        //发起线程发送消息
        executor.submit(new WebSocketCallable(channel, content));
        latch = new CountDownLatch(1);
        clientInitializer.setHandler(handler);
        clientInitializer.resetLathc(latch);
        //等待，当websocket服务端返回数据时唤醒屏障，并返回结果
        latch.await();
        return clientInitializer.getServerResult();

    }

    public static String sendMsg(String content) throws InterruptedException {
        if(!channel.isActive()){
            flushConn();
        }
        logger.info("send msg:" + content);
        //发起线程发送消息
        executor.submit(new WebSocketCallable(channel, content));
        latch = new CountDownLatch(1);
        clientInitializer.setHandler(handler);
        clientInitializer.resetLathc(latch);
        //等待，当websocket服务端返回数据时唤醒屏障，并返回结果
        latch.await();
        return clientInitializer.getServerResult();

    }

    public static void main(String[] args) throws URISyntaxException, InterruptedException, UnsupportedEncodingException {
        String result = "";
        String wsIpUrl = "ws://192.168.6.238:11003/mms/" + ParamsUtil.getUUIDStr();

        JSONObject reqJson = new JSONObject();
        String ctx = Base64.getEncoder().encodeToString("testdata-111111111111111111L".getBytes("UTF-8"));

        String svData = "{\"code\":0,\"msg\":\"SUCCESS\",\"data\":\"MEUCIQCJQIhw3HLUk1/gLdGidBvD8MjWABf+mRszTpX4iaXs3AIgLmMZ8D6Ub/ULAcJU20kS8TfkvPhN0dOnjjI0ze5nZwE=\"}";

        JSONObject svRtnJson = JSONObject.fromObject(svData);
        String data = svRtnJson.getString("data");
        String func = "eccSign";
        func = "eccVerify";

        reqJson.put("function", func);
        reqJson.put("index", 1);
        reqJson.put("data", ctx);
        reqJson.put("sv", data);
        reqJson.put("pkPre", true);

        WebSocketClient.initClientLoop(wsIpUrl);
        result = WebSocketClient.sendMsg(reqJson.toString());
        System.out.println("receive: " + result);

        System.exit(0);




    }

}
