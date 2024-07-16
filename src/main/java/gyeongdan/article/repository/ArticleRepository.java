package gyeongdan.article.repository;

import gyeongdan.article.domain.Article;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository {


    Article findById(Long id);


    List<Article> findAll();

    Article save(Article article);

}
