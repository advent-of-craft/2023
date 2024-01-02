package blog;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static blog.ArticleBuilder.anArticle;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ArticleTests {
    @Test
    void should_add_comment_in_an_article() throws CommentAlreadyExistException {
        final var article = anArticle().build();
        article.addComment(ArticleBuilder.COMMENT_TEXT, ArticleBuilder.AUTHOR);

        assertThat(article.getComments()).hasSize(1);
        assertComment(article.getComments().get(0), ArticleBuilder.COMMENT_TEXT, ArticleBuilder.AUTHOR);
    }

    @Test
    void should_add_comment_in_an_article_containing_already_a_comment() throws CommentAlreadyExistException {
        final var newComment = "Finibus Bonorum et Malorum";
        final var newAuthor = "Al Capone";

        var article = anArticle()
                .commented()
                .build();

        article.addComment(newComment, newAuthor);

        assertThat(article.getComments()).hasSize(2);
        assertComment(article.getComments().getLast(), newComment, newAuthor);
    }

    @Nested
    class Fail {
        @Test
        void when_adding_an_existing_comment() throws CommentAlreadyExistException {
            final var article = anArticle().build();
            article.addComment(ArticleBuilder.COMMENT_TEXT, ArticleBuilder.AUTHOR);

            assertThatThrownBy(() ->
                    article.addComment(ArticleBuilder.COMMENT_TEXT, ArticleBuilder.AUTHOR))
                    .isInstanceOf(CommentAlreadyExistException.class);
        }
    }

    private static void assertComment(Comment comment, String commentText, String author) {
        assertThat(comment.text()).isEqualTo(commentText);
        assertThat(comment.author()).isEqualTo(author);
        assertThat(comment.creationDate()).isBeforeOrEqualTo(LocalDate.now());
    }
}
