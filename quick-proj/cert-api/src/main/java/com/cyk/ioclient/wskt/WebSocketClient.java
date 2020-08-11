package com.cyk.ioclient.wskt;

import com.cyk.cert.CertOidEnum;
import com.cyk.cert.SM2CertGenUtil;
import com.cyk.common.FileUtil;
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
import org.bouncycastle.asn1.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
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

    public static void main(String[] args) throws Exception {
        String result = "";
        String wsIpUrl = "ws://192.168.6.238:11003/mms/" + ParamsUtil.getUUIDStr();
        wsIpUrl = "ws://localhost:11002/mms/" + ParamsUtil.getUUIDStr();



        ASN1EncodableVector issueVector = new ASN1EncodableVector();
        ASN1Encodable[] issueName = {new ASN1ObjectIdentifier(CertOidEnum.COMMON.getOid()), new DERUTF8String("CYK SF-BUILD ROOT CA")};
        ASN1Encodable[] issueOrg = {new ASN1ObjectIdentifier(CertOidEnum.ORGANIZATION.getOid()), new DERUTF8String("自建测试")};
        ASN1Encodable[] issueDN = {new ASN1ObjectIdentifier(CertOidEnum.LOCALITY.getOid()), new DERUTF8String("海淀")};
        ASN1Encodable[] issueCity = {new ASN1ObjectIdentifier(CertOidEnum.STATE.getOid()), new DERUTF8String("北京")};
        ASN1Encodable[] issueCountry = {new ASN1ObjectIdentifier(CertOidEnum.COUNTRY.getOid()), new DERPrintableString("CN")};
        issueVector.add(new DERSet(new DERSequence(issueCountry)));
        issueVector.add(new DERSet(new DERSequence(issueCity)));
        issueVector.add(new DERSet(new DERSequence(issueDN)));
        issueVector.add(new DERSet(new DERSequence(issueOrg)));
        issueVector.add(new DERSet(new DERSequence(issueName)));
        DERSequence certIssue = new DERSequence(issueVector);

        // 主题
        ASN1Encodable[] subjectName = {new ASN1ObjectIdentifier(CertOidEnum.COMMON.getOid()), new DERUTF8String("CYK SF-BUILD CA")};
        ASN1EncodableVector subjectVector = new ASN1EncodableVector();
        subjectVector.add(new DERSet(new DERSequence(issueCountry)));
        subjectVector.add(new DERSet(new DERSequence(issueCity)));
        subjectVector.add(new DERSet(new DERSequence(issueDN)));
        subjectVector.add(new DERSet(new DERSequence(issueOrg)));
        subjectVector.add(new DERSet(new DERSequence(subjectName)));
        DERSequence certSubject = new DERSequence(subjectVector);

        byte[] pk = FileUtil.fromDATfile("/mnt/ecckey/puk-1.bin");
        Date st = new Date();
        Calendar cld = Calendar.getInstance();
        cld.setTime(st);
        cld.set(Calendar.YEAR, cld.get(Calendar.YEAR) + 3 );
        Date et = cld.getTime();

        byte[] tbsByts = SM2CertGenUtil.generateCertTBSCert(1, certIssue, certSubject, st, et, pk);

        String ctx = Base64.getEncoder().encodeToString(tbsByts);

        String func = "eccSign";
//        func = "eccVerify";
        JSONObject reqJson = new JSONObject();
        reqJson.put("function", func);
        reqJson.put("index", 0);
        reqJson.put("data", ctx);
        reqJson.put("pkPre", true);

        WebSocketClient.initClientLoop(wsIpUrl);
        result = WebSocketClient.sendMsg(reqJson.toString());
        System.out.println("receive: " + result);


        JSONObject rtnJson = JSONObject.fromObject(result);
        String svStr = rtnJson.getString("data");
        byte[] sv = Base64.getDecoder().decode(svStr);

        byte[] cerByt = SM2CertGenUtil.makeSM2Cert(tbsByts, (ASN1Sequence) ASN1Sequence.fromByteArray(sv));
        FileUtil.writeInFiles("C:\\Users\\49762\\Desktop\\issue.cer", cerByt);


        System.exit(0);

    }

}
