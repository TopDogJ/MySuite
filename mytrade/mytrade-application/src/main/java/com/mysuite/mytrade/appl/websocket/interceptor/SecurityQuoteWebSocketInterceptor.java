package com.mysuite.mytrade.appl.websocket.interceptor;

import com.mysuite.commons.log.AbstractLoggable;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by jianl on 1/06/2017.
 */
public class SecurityQuoteWebSocketInterceptor extends AbstractLoggable implements HandshakeInterceptor {

    public SecurityQuoteWebSocketInterceptor() {
        super(LogFactory.getLog(SecurityQuoteWebSocketInterceptor.class));
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler webSocketHandler, Map<String, Object> attributes) throws Exception {

        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) request;
            HttpSession httpSession = servletServerHttpRequest.getServletRequest().getSession();
            if(httpSession.getAttribute("username") != null){
                attributes.put("username", httpSession.getAttribute("username"));
            }
        }

        this.getLogger().info("Security quote websocket handshake complete for: " + attributes.get("username"));
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }
}
