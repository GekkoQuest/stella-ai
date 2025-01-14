package quest.gekko.stella.ai.model;

public record PlatformMessage(Platform platform, String senderName, String messageContent) {

    public String getFullMessage() {
        return platform.formatMessage(senderName, messageContent);
    }

}