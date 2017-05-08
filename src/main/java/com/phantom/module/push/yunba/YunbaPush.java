package com.phantom.module.push.yunba;

/**
 * @Author 张志凯 https://github.com/Law-God/phantom-module-push
 * phantom-module-push
 * com.phantom.module.YunbaPush
 * 2016-11-03 15:14
 */

import com.phantom.module.push.IPush;
import com.phantom.module.push.PushProperties;
import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.Exception;
import java.lang.Object;
import java.lang.System;
import java.util.List;
import java.nio.charset.Charset;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.UUID;

public class YunbaPush implements IOCallback,IPush {
    private final static Logger log = Logger.getLogger(YunbaPush.class);
    private SocketIO socket;

    /* 从 yunba.io 获取应用的 appkey */
    private static final String APPKEY = PushProperties.getProperty("appkey");

    public YunbaPush() throws Exception {
        socket = new SocketIO();
        socket.connect(PushProperties.getProperty("url"), this);
    }

    public void onMessage(JSONObject json, IOAcknowledge ack) {
        try {
            System.out.println("Server said:" + json.toString(2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onMessage(String data, IOAcknowledge ack) {
        try {
            socket.emit("publish", new JSONObject("{'topic': '111', 'msg': 'hello form java socket.io client1'}"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("Server said: " + data);
    }

    public void onError(SocketIOException socketIOException) {
        System.out.println("an Error occured");
        socketIOException.printStackTrace();
    }

    public void onDisconnect() {
        System.out.println("Connection terminated.");
    }

    public void onConnect() {
        System.out.println("Connection established");
    }

    public void on(String event, IOAcknowledge ack, Object... args) {
        System.out.println("Server triggered event '" + event + "'");

        try {
            if (event.equals("socketconnectack")) {
                onSocketConnectAck();
            } else if (event.equals("connack")) {
                onConnAck((JSONObject)args[0]);
            } else if (event.equals("puback")) {
                onPubAck((JSONObject)args[0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onSocketConnectAck() throws Exception {
        System.out.println("onSocketConnectAck");

        String customId = "";
        try {
            List<String> file = java.nio.file.Files.readAllLines(Paths.get("customid.dat"), Charset.defaultCharset());
            customId = file.get(0);
        } catch (Exception e) {
            customId = UUID.randomUUID().toString();
        }
        // emit connect
        socket.emit("connect", new JSONObject("{'appkey': '" + APPKEY + "', 'customid': '" + customId + "'}"));
        try {
            PrintWriter writer = new PrintWriter("customid.dat", Charset.defaultCharset().name());
            writer.println(customId);
            writer.close();
        } catch (Exception e) {}
    }

    public void onConnAck(JSONObject json) throws Exception {
        System.out.println("onConnAck success " + json.get("success"));
        for(int i=0;i<10;i++){
            socket.emit("publish", new JSONObject("{'topic': '111', 'msg': 'hello form java socket.io client2'}"));

        }
    }

    public void onPubAck(JSONObject json) throws Exception {
        System.out.println("conPubAck success " + json.get("success") + " msg id " + json.get("messageId"));
    }

}
