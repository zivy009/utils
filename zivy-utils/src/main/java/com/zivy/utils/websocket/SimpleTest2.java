package com.zivy.utils.websocket;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

import org.java_websocket.WebSocket.READYSTATE;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

/**
 *  不需要引入任何包。
 *  
 * @author zivy
 * @date 2018年1月19日
 * @describe
 */
public class SimpleTest2 {

    // volatile String t = "init ..";
    volatile WebSocketClient client;

    SimpleTest2(URI uri, String flag) {

        client = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake arg0) {
                System.out.println("打开链接");
            }

            @Override
            public void onMessage(String arg0) {
                System.out.println(flag + "  收到消息" + arg0);
            }

            @Override
            public void onError(Exception arg0) {
                arg0.printStackTrace();
                System.out.println("发生错误已关闭");
            }

            @Override
            public void onClose(int arg0, String arg1, boolean arg2) {
                System.out.println("链接已关闭");
            }

            @Override
            public void onMessage(ByteBuffer bytes) {
                try {
                    System.out.println(new String(bytes.array(), "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

        };
        client.connect();

    }

    public static void main(String[] args) {

        try {
            URI uri = new URI("ws://echo.websocket.org");
            SimpleTest2 simpleTest2 = new SimpleTest2(uri, "zivy ");

            while (!simpleTest2.client.getReadyState().equals(READYSTATE.OPEN)) {
                System.out.println("还没有打开");
            }
            System.out.println("打开了");
            // send("hello world".getBytes("utf-8"));
            simpleTest2.client.send("hello world");
            simpleTest2.client.send("hello wosssrld");
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
