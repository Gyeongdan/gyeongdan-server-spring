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

    public Optional<Article> findById(Long id) {
        return articleJpaRepository.findById(id);
    }

    public List<Article> findAll() {
        return articleJpaRepository.findAll();
    }

    public Article save(Article article) {
        return articleJpaRepository.save(article);
    }


}
