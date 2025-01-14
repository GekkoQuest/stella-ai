package quest.gekko.stella.ai.service;

import org.springframework.stereotype.Service;
import quest.gekko.stella.ai.config.PromptProvider;
import quest.gekko.stella.ai.util.MessageParser;
import quest.gekko.stella.ai.model.PlatformMessage;

import java.util.List;

@Service
public class GeminiService {
    private final PromptProvider promptProvider;
    private final ConversationContextService conversationContextService;
    private final AIModelService aiModelService;

    public GeminiService(final PromptProvider promptProvider,
                         final ConversationContextService conversationContextService,
                         final AIModelService aiModelService) {
        this.promptProvider = promptProvider;
        this.conversationContextService = conversationContextService;
        this.aiModelService = aiModelService;
    }

    public String processMessage(final String message) {
        final PlatformMessage platformMessage = MessageParser.parse(message);

        final String userId = conversationContextService.createContextKey(platformMessage.platform(), platformMessage.senderName());
        conversationContextService.updateConversationContext(userId, platformMessage.getFullMessage());

        final String prompt = buildPrompt(userId);
        final String aiResponse = aiModelService.generateResponse(prompt);

        conversationContextService.updateConversationContext(userId, "AI: " + aiResponse);
        return aiResponse;
    }

    private String buildPrompt(final String senderName) {
        final List<String> context = conversationContextService.getConversationContext(senderName);

        String prompt = promptProvider.prompt();

        if (!context.isEmpty()) {
            final String contextStr = String.join("\n", context);
            prompt += "\n\nConversation Context:\n" + contextStr;
        }

        prompt += "\nAI:";
        return prompt;
    }
}