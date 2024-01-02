import blog.Article

class ArticleBuilder {
    private val comments: MutableMap<String, String> = mutableMapOf()

    fun commented(): ArticleBuilder = apply { comments += (COMMENT_TEXT to AUTHOR) }

    fun build(): Article {
        val article = Article("Lorem Ipsum", "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore")
        comments.forEach { (text, author) -> article.addComment(text, author) }
        return article
    }

    companion object {
        const val AUTHOR = "Pablo Escobar"
        const val COMMENT_TEXT = "Amazing article !!!"
        fun anArticle(): ArticleBuilder = ArticleBuilder()
    }
}