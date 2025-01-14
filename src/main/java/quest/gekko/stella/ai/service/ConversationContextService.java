package quest.gekko.stella.ai.service;

import org.springframework.stereotype.Service;
import quest.gekko.stella.ai.model.Platform;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ConversationContextService {
    private final Map<String, List<String>> conversationContexts = new ConcurrentHashMap<>();

    public void updateConversationContext(final String senderName, final String newMessage) {
        conversationContexts.putIfAbsent(senderName, new ArrayList<>());

        final List<String> context = conversationContexts.get(senderName);
        context.add(newMessage);

        if (context.size() > 20) {
            context.removeFirst();
        }
    }

    public String createContextKey(final Platform platform, final String senderName) {
        return platform.name() + "_" + senderName;
    }

    public List<String> getConversationContext(String userId) {
        return conversationContexts.getOrDefault(userId, new ArrayList<>());
    }
}