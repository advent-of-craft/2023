package blog;

import io.vavr.collection.Seq;
import io.vavr.control.Either;

import java.time.LocalDate;

import static io.vavr.collection.List.of;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

public class Article {
    private final String name;
    private final String content;
    private final Seq<Comment> comments;

    public Article(String name, String content) {
        this(name, content, of());
    }

    private Article(String name, String content, Seq<Comment> comments) {
        this.name = name;
        this.content = content;
        this.comments = comments;
    }

    public Either<Error, Article> addComment(String text, String author) {
        var comment = new Comment(text, author, LocalDate.now());

        return comments.contains(comment)
                ? left(new Error("This comment already exists in this article"))
                : right(new Article(name, content, comments.append(comment)));
    }

    public Seq<Comment> getComments() {
        return comments;
    }
}

