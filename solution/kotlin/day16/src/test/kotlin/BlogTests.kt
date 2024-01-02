import blog.CommentAlreadyExistException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.inspectors.forOne
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

private const val comment_text = "Amazing article !!!"
private const val author = "Pablo Escobar"

class BlogTests : BehaviorSpec({
    given("an article") {
        `when`("we add comment") {
            then("it adds it to the article") {
                ArticleBuilder
                    .anArticle()
                    .build()
                    .addComment(comment_text, author)
                    .let { article ->
                        article.comments shouldHaveSize 1
                        article.comments.forOne { comment ->
                            comment.text shouldBe comment_text
                            comment.author shouldBe author
                        }
                    }
            }
        }

        `when`("we add an existing comment") {
            then("it should fail") {
                ArticleBuilder
                    .anArticle()
                    .build()
                    .addComment(comment_text, author)
                    .let { article ->
                        shouldThrow<CommentAlreadyExistException> {
                            article.addComment(comment_text, author)
                        }
                    }
            }
        }
    }

    given("an article containing already a comment") {
        `when`("we add comment") {
            then("it adds it to the article") {
                val newComment = "Finibus Bonorum et Malorum"
                val newAuthor = "Al Capone"

                ArticleBuilder.anArticle()
                    .commented()
                    .build()
                    .addComment(newComment, newAuthor)
                    .let { article ->
                        article.comments shouldHaveSize 2
                        article.comments.forOne { comment ->
                            comment.text shouldBe newComment
                            comment.author shouldBe newAuthor
                        }
                    }


            }
        }
    }
})