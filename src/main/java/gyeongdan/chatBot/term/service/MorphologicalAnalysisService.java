package gyeongdan.chatBot.term.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import gyeongdan.chatBot.common.utils.CmmUtil;

@Service
public class MorphologicalAnalysisService {

    private static final Logger logger = LoggerFactory.getLogger(MorphologicalAnalysisService.class);

    // 자연어 처리 - 형태소 분석기인 Komoran를 메모리에 올리기 위해 WordAnalysisService 클래스 내 전역 변수로 설정합니다.
    Komoran nlp = null;

    // 생성자 사용함 - 톰켓에서 부팅할 때 @Service를 모두 메모리에 올립니다.
    // 톰켓이 메모리에 올릴 때, 생성자에 선언한 Komoran도 같이 메모리에 올라가도록 생성자에 코딩합니다.
    // 생성자에서 Komoran을 메모리에 올리면, 매번 메모리에 올려서 호출하는 것이 아니라,
    // 메모리에 올리간 객체만 불러와서 사용할 수 있기 때문에 처리 속도가 빠릅니다.
    public MorphologicalAnalysisService() {
        logger.info("{}.WordAnalysisService creator Start !", this.getClass().getName());

        // NLP 분석 객체 메모리 로딩합니다.
        this.nlp = new Komoran(DEFAULT_MODEL.LIGHT); // 학습데이터 경량화 버전( 웹 서비스에 적합합니다. )
        // this.nlp = new Komoran(DEFAULT_MODEL.FULL); // 학습데이터 전체 버전(일괄처리 : 배치 서비스에 적합합니다.)

        logger.info("난 톰켓이 부팅되면서 스프링 프렝미워크가 자동 실행되었고, 스프링 실행될 때 nlp 변수에 Komoran 객체를 생성하여 저장하였다.");

        logger.info("{}.WordAnalysisService creator End !", this.getClass().getName());
    }

    public List<String> doWordNouns(String text) throws Exception {
        logger.info("{}.doWordAnalysis Start !", this.getClass().getName());
        logger.info("분석할 문장 : {}", text);

        // 분석할 문장에 대해 정제(쓸데없는 특수문자 제거)
        String replace_text = text.replace("[^가-힣a-zA-Z0-9]", " ");
        logger.info("한국어, 영어, 숫자 제외 단어 모두 한 칸으로 변환시킨 문장 : {}", replace_text);

        // 분석할 문장의 앞, 뒤에 존재할 수 있는 필요없는 공백 제거
        String trim_text = replace_text.trim();
        logger.info("분석할 문장 앞, 뒤에 존재할 수 있는 필요 없는 공백 제거 : {}", trim_text);

        // 형태소 분석 시작
        KomoranResult analyzeResultList = this.nlp.analyze(trim_text);

        // 형태소 분석 결과 중 명사만 가져오기
        List<String> rList = analyzeResultList.getNouns();

        if (rList == null) {
            rList = new ArrayList<>();
        }

        // 분석 결과 확인을 위한 로그 찍기
        Iterator<String> it = rList.iterator();

        while (it.hasNext()) {
            // 추출된 명사
            String word = CmmUtil.nvl(it.next());

            logger.info("word : {}", CmmUtil.nvl(word));
        }

        logger.info("{}.doWordAnalysis End !", this.getClass().getName());

        return rList;
    }

    public Map<String, Integer> doWordCount(List<String> pList) throws Exception {
        logger.info("{}.doWordCount Start !", this.getClass().getName());

        if (pList == null) {
            pList = new ArrayList<>();
        }

        // 단어 빈도수(사과, 3) 결과를 저장하기 위해 Map객체 생성합니다.
        Map<String, Integer> rMap = new HashMap<>();

        // List에 존재하는 중복되는 단어들의 중복제거를 위해 set 데이터타입에 데이터를 저장합니다.
        // rSet 변수는 중복된 데이터가 저장되지 않기 때문에 중복되지 않은 단어만 저장하고 나머지는 자동 삭제합니다.
        Set<String> rSet = new HashSet<>(pList);

        // 중복이 제거된 단어 모음에 빈도수를 구하기 위해 반복문을 사용합니다.
        Iterator<String> it = rSet.iterator();

        while (it.hasNext()) {
            // 중복 제거된 단어
            String word = CmmUtil.nvl(it.next());

            // 단어가 중복 저장되어 있는 pList로부터 단어의 빈도수 가져오기
            int frequency = Collections.frequency(pList, word);

            logger.info("word : {}", word);
            logger.info("frequency : {}", frequency);

            rMap.put(word, frequency);
        }

        logger.info("{}.doWordCount End !", this.getClass().getName());
        return rMap;
    }

    public Map<String, Integer> extractKorWords(String text) throws Exception {
        // 문장의 명사를 추출하기 위한 형태소 분석 실행
        List<String> rList = this.doWordNouns(text);

        if (rList == null) {
            rList = new ArrayList<>();
        }

        // 추출된 명사 모음(리스트)의 명사 단어별 빈도수 계산
        Map<String, Integer> rMap = this.doWordCount(rList);

        if (rMap == null) {
            rMap = new HashMap<>();
        }

        return rMap;
    }

    public List<String> extractAndCombineNouns(String text) throws Exception {
        List<String> nounsList = this.doWordNouns(text);
        List<String> combinedNouns = new ArrayList<>();
        StringBuilder combined = new StringBuilder();

        for (String noun : nounsList) {
            combined.append(noun);
            combinedNouns.add(combined.toString());
        }

        return combinedNouns;
    }
}
