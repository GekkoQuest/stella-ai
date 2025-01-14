package quest.gekko.stella.ai.util;

import org.springframework.stereotype.Component;
import quest.gekko.stella.ai.model.Platform;
import quest.gekko.stella.ai.model.PlatformMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class MessageParser {

    public PlatformMessage parse(final String rawMessage) {
        final Pattern pattern = Pattern.compile("\\[(.*?)] (.*?): (.*)");
        final Matcher matcher = pattern.matcher(rawMessage);

        if (matcher.matches()) {
            final Platform platform = Platform.fromString(matcher.group(1));

            return new PlatformMessage(
                    platform,
                    matcher.group(2),
                    matcher.group(3)
            );
        } else {
            throw new IllegalArgumentException("Invalid message format: " + rawMessage);
        }
    }

}