package quest.gekko.stella.ai.service;

import org.springframework.stereotype.Service;
import quest.gekko.stella.ai.model.Platform;
import quest.gekko.stella.ai.util.CircularQueue;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ConversationContextService {
    private final Map<String, CircularQueue<String>> conversationContexts = new ConcurrentHashMap<>();

    private static final int MAX_CONTEXT_SIZE = 20;

    // Synchronized to prevent concurrent access/modification of CircularQueue as it's not thread-safe.
    public synchronized void updateConversationContext(final String senderName, final String newMessage) {
        final CircularQueue<String> conversationContext = conversationContexts.computeIfAbsent(senderName, k -> new CircularQueue<>(MAX_CONTEXT_SIZE));
        conversationContext.add(newMessage);
    }

    public String createContextKey(final Platform platform, final String senderName) {
        return platform.name() + "_" + senderName;
    }

    public List<String> getConversationContext(String senderName) {
        return conversationContexts.getOrDefault(senderName, new CircularQueue<>(MAX_CONTEXT_SIZE)).getAll();
    }
}