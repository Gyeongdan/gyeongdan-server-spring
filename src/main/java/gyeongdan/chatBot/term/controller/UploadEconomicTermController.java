package gyeongdan.chatBot.term.controller;

import gyeongdan.chatBot.term.service.UploadEcnomicTermService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/chatbot/terms")
@RequiredArgsConstructor
public class UploadEconomicTermController {
    private static final Logger logger = LoggerFactory.getLogger(UploadEconomicTermController.class);
    private final UploadEcnomicTermService economicTermService;

    @PostMapping("/upload")
    public String uploadEconomicTerms(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            logger.error("File is empty");
            return "File is empty";
        }

        try {
            // 임시 디렉토리에 파일 저장
            Path tempDir = Files.createTempDirectory("");
            Path tempFilePath = tempDir.resolve(file.getOriginalFilename());
            file.transferTo(tempFilePath.toFile());

            // 저장된 파일의 경로를 서비스 클래스에 전달하여 처리
            economicTermService.saveEconomicTermsFromJsonFile(tempFilePath.toString());

            return "Economic terms uploaded and saved successfully!";
        } catch (IOException e) {
            logger.error("Failed to upload and save economic terms", e);
            return "Failed to upload and save economic terms";
        }
    }
}
