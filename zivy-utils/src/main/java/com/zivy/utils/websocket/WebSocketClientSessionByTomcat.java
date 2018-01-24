package com.zivy.utils.websocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

/**
* @author zivy
* @date 2018年1月15日
* @describe
*/
public class WebSocketClientSessionByTomcat {

    private static String uri = "ws://echo.websocket.org";
    private static Session session;
 
    private void start() {
        WebSocketContainer container = null;
        try {
            container = ContainerProvider.getWebSocketContainer();
        } catch (Exception ex) {
            System.out.println("error" + ex);
        }
 
        try {
            URI r = URI.create(uri);
            session = container.connectToServer(WebsocketClientByTomcat.class, r);
        } catch (DeploymentException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
 
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        WebSocketClientSessionByTomcat client = new WebSocketClientSessionByTomcat();
        client.start();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        try {
            do {
                input = br.readLine();
                if (!input.equals("exit"))
                    client.session.getBasicRemote().sendText(input);
            } while (!input.equals("exit"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
