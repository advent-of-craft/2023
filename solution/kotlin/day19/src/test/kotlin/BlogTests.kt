import arrow.core.flatMap
import blog.Error
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
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
                    .let { result ->
                        val article = result.shouldBeRight()
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
                    .flatMap { a -> a.addComment(comment_text, author) }
                    .shouldBeLeft(Error("This comment already exists in this article"))
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
                    .let { result ->
                        val article = result.shouldBeRight()
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