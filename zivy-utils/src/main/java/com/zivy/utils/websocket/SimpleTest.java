package com.zivy.utils.websocket;

import java.net.URI;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

/**
 * 用tomcat jar 的websocketClient
 * 
 * @author zivy
 * @date 2018年1月22日
 * @describe
 */
public class SimpleTest {

    public SimpleTest() {
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {
        f1();
    }

    public static void f1() {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer(); // 获取WebSocket连接器，其中具体实现可以参照websocket-api.jar的源码,Class.forName("org.apache.tomcat.websocket.WsWebSocketContainer");
            String uri = "ws://echo.websocket.org";
            Session session = container.connectToServer(WebsocketClientByTomcat.class, new URI(uri)); // 连接会话
            session.getBasicRemote().sendText("123132132131"); // 发送文本消息
            session.getBasicRemote().sendText("4564546");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
