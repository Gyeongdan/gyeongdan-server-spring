package gyeongdan.chatBot.gpt.service;

import gyeongdan.chatBot.gpt.model.GptResponse;
import gyeongdan.chatBot.gpt.repository.ChatBotRepository;
import org.springframework.stereotype.Service;

@Service
public class ChatBotService {

    private final ChatBotRepository chatBotRepository;

    public ChatBotService(ChatBotRepository chatBotRepository) {
        this.chatBotRepository = chatBotRepository;
    }

    public void saveGptResponse(String prompt, String response) {
        GptResponse gptResponse = new GptResponse(prompt, response);
        chatBotRepository.save(gptResponse);
    }
}
