package gyeongdan.chatBot.term.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gyeongdan.chatBot.term.model.EconomicTerm;
import gyeongdan.chatBot.term.repository.EconomicTermRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UploadEcnomicTermService {
    private static final Logger logger = LoggerFactory.getLogger(UploadEcnomicTermService.class);
    private final EconomicTermRepository economicTermRepository;

    @Transactional
    public void saveEconomicTermsFromJsonFile(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            List<EconomicTerm> economicTerms = objectMapper.readValue(content, new TypeReference<List<EconomicTerm>>() {});

            Map<String, List<EconomicTerm>> termGroups = economicTerms.stream()
                    .collect(Collectors.groupingBy(EconomicTerm::getTerm));

            List<String> duplicateTermsInFile = termGroups.entrySet().stream()
                    .filter(entry -> entry.getValue().size() > 1)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            if (!duplicateTermsInFile.isEmpty()) {
                logger.error("Duplicate terms in file: " + duplicateTermsInFile);
                return;
            }

            List<EconomicTerm> existingTerms = economicTerms.stream()
                    .filter(term -> economicTermRepository.findByTerm(term.getTerm()).isPresent())
                    .collect(Collectors.toList());

            if (!existingTerms.isEmpty()) {
                List<String> duplicateTermsInDB = existingTerms.stream()
                        .map(EconomicTerm::getTerm)
                        .collect(Collectors.toList());
                logger.error("Duplicate terms in database: " + duplicateTermsInDB);
                return;
            }

            List<EconomicTerm> newTerms = economicTerms.stream()
                    .filter(term -> !economicTermRepository.findByTerm(term.getTerm()).isPresent())
                    .collect(Collectors.toList());

            economicTermRepository.saveAll(newTerms);
        } catch (IOException e) {
            logger.error("Failed to save economic terms from JSON file", e);
        }
    }
}
