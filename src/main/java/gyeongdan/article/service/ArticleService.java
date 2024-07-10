package gyeongdan.article.service;

import gyeongdan.article.domain.Article;
import gyeongdan.article.dto.ArticleUpdateRequest;
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


    public Article validateArticle(Long id) {
        Article article = articleRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 ID의 게시글이 존재하지 않습니다."));
        article.setIsValid(true);
        return articleRepository.save(article);
    }

    public Long updateArticle(ArticleUpdateRequest articleUpdateRequest) {
        Article article = articleRepository.findById(articleUpdateRequest.getId())
            .orElseThrow(() -> new IllegalArgumentException("해당 ID의 게시글이 존재하지 않습니다."));
        article.setTitle(articleUpdateRequest.getTitle());
        article.setContent(articleUpdateRequest.getContent());
        return articleRepository.save(article).getId();
    }
}
