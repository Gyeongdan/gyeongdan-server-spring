package gyeongdan.chatBot.gpt.controller;

import gyeongdan.chatBot.gpt.service.ChatBotService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;

import java.util.List;

@RestController
public class ChatBotController {

    private final OpenAiService openAiService;
    private final ChatBotService chatBotService;

    public ChatBotController(@Value("${openai.api.key}") String apiKey, ChatBotService chatBotService) {
        this.openAiService = new OpenAiService(apiKey);
        this.chatBotService = chatBotService;
    }


    @GetMapping("/gpt4o")
    public String getGpt4oResponse(@RequestParam String prompt) {
        ChatMessage message = new ChatMessage(ChatMessageRole.USER.value(), prompt);
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model("gpt-4o")
                .messages(List.of(message))
                .maxTokens(100)
                .build();

        List<ChatCompletionChoice> choices = openAiService.createChatCompletion(chatCompletionRequest).getChoices();
        if (choices.isEmpty()) {
            return "No response from GPT-4o";
        }

        String response = choices.get(0).getMessage().getContent();
        chatBotService.saveGptResponse(prompt, response);

        return response;
    }
}
