package gyeongdan.chatBot.gpt.service;

import gyeongdan.chatBot.gpt.model.GptResponse;
import gyeongdan.chatBot.gpt.repository.ChatBotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatBotService {

    private final ChatBotRepository chatBotRepository;

    public void saveGptResponse(String prompt, String response) {
        GptResponse gptResponse = new GptResponse(prompt, response);
        chatBotRepository.save(gptResponse);
    }
}
