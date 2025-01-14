package quest.gekko.stella.ai.model;

public enum Platform {
    DISCORD {
        @Override
        public String formatMessage(final String senderName, final String message) {
            return "[Discord] " + senderName + ": " + message;
        }
    },

    TWITCH {
        @Override
        public String formatMessage(final String senderName, final String message) {
            return "[Twitch] " + senderName + ": " + message;
        }
    };

    public abstract String formatMessage(final String senderName, final String message);

    public static Platform fromString(final String platform) {
        try {
            return Platform.valueOf(platform.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unsupported platform: " + platform);
        }
    }
}