import blog.Article
import blog.CommentAlreadyExistException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.inspectors.forExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

class BlogTests : FunSpec({
    test("it should add valid comment") {
        val article = Article(
            "Lorem Ipsum",
            "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        )
        article.addComment("Amazing article !!!", "Pablo Escobar")
    }

    test("it should add a comment with the given text") {
        val article = Article(
            "Lorem Ipsum",
            "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        )
        val text = "Amazing article !!!"

        article.addComment(text, "Pablo Escobar")

        article.getComments() shouldHaveSize 1
        article.getComments().forExactly(1) {
            it.text shouldBe text
        }
    }

    test("it should add a comment with the given author") {
        val article = Article(
            "Lorem Ipsum",
            "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        )
        val author = "Pablo Escobar"

        article.addComment("Amazing article !!!", author)

        article.getComments() shouldHaveSize 1
        article.getComments().forExactly(1) {
            it.author shouldBe author
        }
    }

    test("it should add a comment with the date of the day") {
        val article = Article(
            "Lorem Ipsum",
            "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        )
        article.addComment("Amazing article !!!", "Pablo Escobar")
    }

    test("it should throw an exception when adding existing comment") {
        val article = Article(
            "Lorem Ipsum",
            "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        )

        article.addComment("Amazing article !!!", "Pablo Escobar")

        shouldThrow<CommentAlreadyExistException> { article.addComment("Amazing article !!!", "Pablo Escobar") }
    }
})