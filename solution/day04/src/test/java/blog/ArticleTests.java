package blog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ArticleTests {
    public static final String AUTHOR = "Pablo Escobar";
    private static final String COMMENT_TEXT = "Amazing article !!!";
    private Article article;

    @BeforeEach
    void setup() {
        article = new Article(
                "Lorem Ipsum",
                "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        );
    }

    @Test
    void should_add_comment_in_an_article() throws CommentAlreadyExistException {
        article.addComment(COMMENT_TEXT, AUTHOR);

        assertThat(article.getComments()).hasSize(1);

        var comment = article.getComments().get(0);
        assertThat(comment.text()).isEqualTo(COMMENT_TEXT);
        assertThat(comment.author()).isEqualTo(AUTHOR);
    }

    @Test
    void should_add_comment_in_an_article_containing_already_a_comment() throws CommentAlreadyExistException {
        var newComment = "Finibus Bonorum et Malorum";
        var newAuthor = "Al Capone";

        article.addComment(COMMENT_TEXT, AUTHOR);
        article.addComment(newComment, newAuthor);

        assertThat(article.getComments()).hasSize(2);

        var lastComment = article.getComments().getLast();
        assertThat(lastComment.text()).isEqualTo(newComment);
        assertThat(lastComment.author()).isEqualTo(newAuthor);
    }

    @Nested
    class Fail {
        @Test
        void when__adding_an_existing_comment() throws CommentAlreadyExistException {
            article.addComment(COMMENT_TEXT, AUTHOR);

            assertThatThrownBy(() -> {
                article.addComment(COMMENT_TEXT, AUTHOR);
            }).isInstanceOf(CommentAlreadyExistException.class);
        }
    }
}
