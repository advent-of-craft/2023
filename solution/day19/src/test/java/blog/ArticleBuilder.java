package blog;

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;

import static org.instancio.Instancio.create;

public class ArticleBuilder {
    public static final String AUTHOR = "Pablo Escobar";
    public static final String COMMENT_TEXT = "Amazing article !!!";
    private Map<String, String> comments;

    public ArticleBuilder() {
        comments = HashMap.empty();
    }

    public static ArticleBuilder anArticle() {
        return new ArticleBuilder();
    }

    public ArticleBuilder commented() {
        this.comments = comments.put(COMMENT_TEXT, AUTHOR);
        return this;
    }

    public Article build() {
        var article = new Article(
                create(String.class),
                create(String.class)
        );
        return comments.foldLeft(article, (a, c) -> a.addComment(c._1, c._2).get());
    }
}
