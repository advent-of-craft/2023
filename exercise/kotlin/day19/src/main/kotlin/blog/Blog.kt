package blog

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
    ): Article {
        val comment = Comment(text, author, creationDate)
        if (comments.contains(comment)) throw CommentAlreadyExistException()
        else return Article(name, content, comments + comment)
    }

    fun addComment(text: String, author: String): Article = addComment(text, author, LocalDate.now())
}


data class Comment(val text: String, val author: String, val creationDate: LocalDate)
class CommentAlreadyExistException : Exception()