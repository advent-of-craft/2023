package blog;

import io.vavr.control.Either;
import org.assertj.vavr.api.VavrAssertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;
import java.util.function.Function;

import static blog.ArticleBuilder.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Instancio.create;

class ArticleTests {
    private Either<Error, Article> result;

    @Test
    void should_add_comment_in_an_article() {
        when(article -> article.addComment(COMMENT_TEXT, AUTHOR));
        then(result -> VavrAssertions.assertThat(result).hasRightValueSatisfying(article -> {
            assertComment(article.getComments().last(), COMMENT_TEXT, AUTHOR);
        }));
    }

    @Test
    void should_add_comment_in_an_article_containing_already_a_comment() {
        final var newComment = create(String.class);
        final var newAuthor = create(String.class);

        when(ArticleBuilder::commented, article -> article.addComment(newComment, newAuthor));
        then(result -> VavrAssertions.assertThat(result).hasRightValueSatisfying(article -> {
            assertThat(article.getComments()).hasSize(2);
            assertComment(article.getComments().last(), newComment, newAuthor);
        }));
    }

    @Nested
    class Fail {
        @Test
        void when_adding_an_existing_comment() {
            when(ArticleBuilder::commented, article -> article.addComment(article.getComments().get(0).text(), article.getComments().get(0).author()));
            then(result -> VavrAssertions.assertThat(result)
                    .containsOnLeft(new Error("This comment already exists in this article")))
            ;
        }
    }

    private static void assertComment(Comment comment, String commentText, String author) {
        assertThat(comment.text()).isEqualTo(commentText);
        assertThat(comment.author()).isEqualTo(author);
    }

    private void when(ArticleBuilder articleBuilder, Function<Article, Either<Error, Article>> act) {
        result = act.apply(
                articleBuilder.build()
        );
    }

    private void when(Function<Article, Either<Error, Article>> act) {
        when(anArticle(), act);
    }

    private void when(Function<ArticleBuilder, ArticleBuilder> options, Function<Article, Either<Error, Article>> act) {
        when(options.apply(anArticle()), act);
    }

    private void then(Consumer<Either<Error, Article>> act) {
        act.accept(result);
    }
}