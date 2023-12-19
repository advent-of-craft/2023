package blog;

import java.util.HashMap;

import static org.instancio.Instancio.create;

public class ArticleBuilder {
    public static final String AUTHOR = "Pablo Escobar";
    public static final String COMMENT_TEXT = "Amazing article !!!";
    private final HashMap<String, String> comments;

    public ArticleBuilder() {
        comments = new HashMap<>();
    }

    public static ArticleBuilder anArticle() {
        return new ArticleBuilder();
    }

    public ArticleBuilder commented() {
        this.comments.put(COMMENT_TEXT, AUTHOR);
        return this;
    }

    public Article build() {
        return comments.entrySet()
                .stream()
                .reduce(new Article(
                        create(String.class),
                        create(String.class)
                ), (a, e) -> a.addComment(e.getKey(), e.getValue()), (p, n) -> p);
    }
}
