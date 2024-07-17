package gyeongdan.article.article.repository;

import gyeongdan.article.article.domain.Article;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository {


    Article findById(Long id);


    List<Article> findAll();

    Article save(Article article);

}
