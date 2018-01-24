package com.zivy.utils.websocket;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

/**
 * 这个对象不是单例
 * 
 * @author zivy
 * @date 2018年1月15日
 * @describe
 */
@ClientEndpoint
public class WebsocketClientByTomcat {

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected to endpoint: " + session.getBasicRemote());
    }

    @OnMessage
    public void onMessage(Session session,String message) {
        System.out.println("Client onMessage: " + message);
    }

  
    @OnClose
    public void onClose() {
        System.out.println("onClose");
    }

    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }
}
