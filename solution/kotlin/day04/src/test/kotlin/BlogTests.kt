import blog.Article
import blog.CommentAlreadyExistException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.inspectors.forOne
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

private const val comment_text = "Amazing article !!!"
private const val author = "Pablo Escobar"

class BlogTests : BehaviorSpec({
    lateinit var article: Article

    beforeTest {
        article = Article(
            "Lorem Ipsum",
            "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        )
    }

    given("an article") {
        `when`("we add comment") {
            then("it adds it to the article") {
                article.addComment(comment_text, author)

                article.comments shouldHaveSize 1
                article.comments.forOne {
                    it.text shouldBe comment_text
                    it.author shouldBe author
                }
            }
        }

        `when`("we add an existing comment") {
            then("it should fail") {
                article.addComment(comment_text, author)
                shouldThrow<CommentAlreadyExistException> {
                    article.addComment(comment_text, author)
                }

            }
        }
    }

    given("an article containing already a comment") {
        `when`("we add comment") {
            then("it adds it to the article") {
                val newComment = "Finibus Bonorum et Malorum"
                val newAuthor = "Al Capone"

                article.addComment(comment_text, author)
                article.addComment(newComment, newAuthor)

                article.comments shouldHaveSize 2
                article.comments.forOne {
                    it.text shouldBe newComment
                    it.author shouldBe newAuthor
                }
            }
        }
    }
})