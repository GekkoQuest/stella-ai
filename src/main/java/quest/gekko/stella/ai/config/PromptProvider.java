package quest.gekko.stella.ai.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PromptProvider {
    @Value("${stella.ai.prompt}")
    private String prompt;

    @Bean
    public String prompt() {
        return prompt;
    }
}
