package bdabackend.bda.Utils;

import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.ConcurrentHashMap;


public class WebSocketSessionRegistry {
    private static ConcurrentHashMap<String, WebSocketSession> activeSessions = new ConcurrentHashMap<>();

    public static void addSession(String userId, WebSocketSession session) {
        activeSessions.put(userId, session);
    }

    public static void removeSession(String userId) {
        activeSessions.remove(userId);
    }

    public static WebSocketSession getSession(String userId) {
        return activeSessions.get(userId);
    }

    public static void broadcastMessageToActiveUsers(String message) {
        activeSessions.values().forEach(session -> {
            try {
                session.sendMessage(new org.springframework.web.socket.TextMessage(message));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
