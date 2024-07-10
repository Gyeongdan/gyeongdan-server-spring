package gyeongdan.article.service;

import gyeongdan.article.domain.Article;
import gyeongdan.article.dto.ArticleCreateRequest;
import gyeongdan.article.repository.ArticleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public Article getValidArticle(Long id) {
        Article article = articleRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 ID의 게시글이 존재하지 않습니다."));
        if (article.getIsValid()) {
            return article;
        } else {
            throw new IllegalArgumentException("해당 게시글은 유효하지 않습니다.");
        }
    }

    public List<Article> getArticles() {
        return articleRepository.findAll();
    }

    public Article saveArticle(ArticleCreateRequest article) {
        return articleRepository.save(article.toEntity());
    }

}
