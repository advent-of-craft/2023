package blog

import java.time.LocalDate

class Article(private val name: String, private val content: String) {
    private val comments: MutableList<Comment> = mutableListOf()

    private fun addComment(
        text: String,
        author: String,
        creationDate: LocalDate
    ) {
        val comment = Comment(text, author, creationDate)
        if (comments.contains(comment)) {
            throw CommentAlreadyExistException()
        } else comments.add(comment)
    }

    fun addComment(text: String, author: String) = addComment(text, author, LocalDate.now())
    fun getComments(): List<Comment> = comments
}


data class Comment(val text: String, val author: String, val creationDate: LocalDate)
class CommentAlreadyExistException : Exception()