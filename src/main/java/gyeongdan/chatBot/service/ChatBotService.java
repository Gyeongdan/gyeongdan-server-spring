package gyeongdan.chatBot.service;

import gyeongdan.chatBot.model.GptResponse;
import gyeongdan.chatBot.repository.ChatBotRepository;
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
