package quest.gekko.stella.ai.service;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.Content;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.api.GenerationConfig;
import com.google.cloud.vertexai.generativeai.ContentMaker;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import io.grpc.StatusRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import quest.gekko.stella.ai.config.GeminiConfiguration;

import java.io.IOException;

@Service
public class AIModelService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AIModelService.class);

    private final GeminiConfiguration geminiConfiguration;

    public AIModelService(final GeminiConfiguration geminiConfiguration) {
        this.geminiConfiguration = geminiConfiguration;
    }

    public String generateResponse(final String prompt) {
        final String projectId = geminiConfiguration.projectId();
        final String region = geminiConfiguration.region();

        try (final VertexAI vertexAI = new VertexAI(projectId, region)) {
            final GenerationConfig config = geminiConfiguration.generationConfig();

            final String modelName = geminiConfiguration.modelName();
            final GenerativeModel model = new GenerativeModel.Builder()
                    .setModelName(modelName)
                    .setVertexAi(vertexAI)
                    .setGenerationConfig(config)
                    .build();

            final Content content = ContentMaker.fromMultiModalData(prompt);
            final GenerateContentResponse response = model.generateContent(content);

            return response.getCandidates(0)
                    .getContent()
                    .getParts(0)
                    .getText();
        } catch (final StatusRuntimeException ex) {
            /*
                Seems to only occurs when the AI is overwhelmed due to too many requests in a short timespan.
                TODO: Consider adding a message queue to handle rate-limiting more gracefully?
             */
            LOGGER.error("StatusRuntimeException occurred while attempting to generate a response: {}", ex.getMessage(), ex);
            return "Resources are currently exhausted. Please try again soon!";
        } catch (IOException ex) {
            LOGGER.error("IOException occurred while attempting to generate a response: {}", ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }
}