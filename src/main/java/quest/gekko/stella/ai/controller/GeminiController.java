package quest.gekko.stella.ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quest.gekko.stella.ai.service.GeminiService;

@RestController
@RequestMapping("/ai")
public class GeminiController {
    private final GeminiService geminiService;

    @Autowired
    public GeminiController(final GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping("/process-message")
    public String processMessage(@RequestBody final String message)  {
        return geminiService.processMessage(message);
    }
}
