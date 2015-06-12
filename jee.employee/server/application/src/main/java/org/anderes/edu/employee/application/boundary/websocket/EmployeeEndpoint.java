package org.anderes.edu.employee.application.boundary.websocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import static java.util.logging.Level.*;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.websocket.CloseReason;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.PongMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint(value = "/echo")
public class EmployeeEndpoint extends Endpoint {

    @Inject
    private Logger logger;
    
    @Override @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        logger.info("WebSocket opened: " + session.getId());
    }

    @OnMessage
    public void textMessage(Session session, String msg) {
        logger.info("Text message: " + msg);
        try {
            session.getBasicRemote().sendText(msg);
        } catch (IOException ex) {
            logger.log(WARNING, ex.getMessage(), ex);
        }
    }
    @OnMessage
    public void binaryMessage(Session session, ByteBuffer msg) {
        logger.info("Binary message: " + msg.toString());
        try {
            session.getBasicRemote().sendBinary(msg);
        } catch (IOException ex) {
            logger.log(WARNING, ex.getMessage(), ex);
        }
    }
    @OnMessage
    public void pongMessage(Session session, PongMessage msg) {
        logger.info("Pong message: " + msg.getApplicationData().toString());
        try {
            session.getBasicRemote().sendPong(msg.getApplicationData());
        } catch (IOException ex) {
            logger.log(WARNING, ex.getMessage(), ex);
        }
    }
    
    @Override
    public void onClose(Session session, CloseReason closeReason) {
        super.onClose(session, closeReason);
        logger.info("Session " + session.getId() + " has ended");
    }

    @Override
    public void onError(Session session, Throwable thr) {
        super.onError(session, thr);
        logger.log(WARNING, thr.getMessage(), thr);
    }

}
