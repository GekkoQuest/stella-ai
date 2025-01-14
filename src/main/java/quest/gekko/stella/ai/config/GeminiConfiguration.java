package quest.gekko.stella.ai.config;

import com.google.cloud.vertexai.api.GenerationConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeminiConfiguration {
    @Value("${vertex.ai.project.id}")
    private String projectId;

    @Value("${vertex.ai.region}")
    private String region;

    @Value("${vertex.ai.model.name}")
    private String modelName;

    @Value("${vertex.ai.generation.max-output-tokens}")
    private int maxOutputTokens;

    @Value("${vertex.ai.generation.temperature}")
    private float temperature;

    @Value("${vertex.ai.generation.top-p}")
    private float topP;

    @Bean
    public String projectId() {
        return projectId;
    }

    @Bean
    public String region() {
        return region;
    }

    @Bean
    public String modelName() {
        return modelName;
    }

    @Bean
    public GenerationConfig generationConfig() {
        return GenerationConfig.newBuilder()
                .setMaxOutputTokens(maxOutputTokens)
                .setTemperature(temperature)
                .setTopP(topP)
                .build();
    }
}
