package quest.gekko.stella.ai.service;

import org.springframework.stereotype.Service;
import quest.gekko.stella.ai.config.PromptProvider;
import quest.gekko.stella.ai.util.MessageParser;
import quest.gekko.stella.ai.model.PlatformMessage;

import java.util.List;

@Service
public class GeminiService {
    private final PromptProvider promptProvider;
    private final MessageParser messageParser;
    private final ConversationContextService conversationContextService;
    private final AIModelService aiModelService;

    public GeminiService(final PromptProvider promptProvider,
                         final MessageParser messageParser,
                         final ConversationContextService conversationContextService,
                         final AIModelService aiModelService) {
        this.promptProvider = promptProvider;
        this.messageParser = messageParser;
        this.conversationContextService = conversationContextService;
        this.aiModelService = aiModelService;
    }

    public String processMessage(final String rawMessage) {
        final PlatformMessage platformMessage = messageParser.parse(rawMessage);

        final String userId = conversationContextService.createContextKey(platformMessage.platform(), platformMessage.senderName());
        conversationContextService.updateConversationContext(userId, platformMessage.getFullMessage());

        final String prompt = buildPrompt(userId);
        final String aiResponse = aiModelService.generateResponse(prompt);

        conversationContextService.updateConversationContext(userId, "AI: " + aiResponse);
        return aiResponse;
    }

    private String buildPrompt(final String userId) {
        final List<String> context = conversationContextService.getConversationContext(userId);
        final StringBuilder prompt = new StringBuilder(promptProvider.prompt());

        if (!context.isEmpty()) {
            prompt.append("\n\nConversation Context:\n");
            prompt.append(String.join("\n", context));
        }

        prompt.append("\nAI:");
        return prompt.toString();
    }
}