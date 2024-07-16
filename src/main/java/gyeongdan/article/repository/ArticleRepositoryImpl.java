package gyeongdan.article.repository;

import gyeongdan.article.domain.Article;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepository {

    private final ArticleJpaRepository articleJpaRepository;
    private final ArticleViewHistoryJpaRepository articleViewHistoryJpaRepository;

    public Article findById(Long id) {
        return articleJpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 게시글이 존재하지 않습니다."));
    }

    public List<Article> findAll() {
        return articleJpaRepository.findAll();
    }

    public Article save(Article article) {
        return articleJpaRepository.save(article);
    }


}
