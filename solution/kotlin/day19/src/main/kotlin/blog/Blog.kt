package blog

import arrow.core.Either
import arrow.core.Either.Left
import arrow.core.Either.Right
import java.time.LocalDate

class Article(
    private val name: String,
    private val content: String,
    val comments: List<Comment> = mutableListOf()
) {
    private fun addComment(
        text: String,
        author: String,
        creationDate: LocalDate
    ): Either<Error, Article> = Comment(text, author, creationDate)
        .let { comment ->
            return when (comments.contains(comment)) {
                true -> Left(Error("This comment already exists in this article"))
                else -> Right(Article(name, content, comments + comment))
            }
        }

    fun addComment(text: String, author: String): Either<Error, Article> = addComment(text, author, LocalDate.now())
}

data class Comment(val text: String, val author: String, val creationDate: LocalDate)
data class Error(val message: String)