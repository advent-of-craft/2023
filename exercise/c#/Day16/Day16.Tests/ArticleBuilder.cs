namespace Day16.Tests
{
    public class ArticleBuilder
    {
        public const string Author = "Pablo Escobar";
        public const string CommentText = "Amazing article !!!";
        private readonly Dictionary<string, string> _comments = new();

        public static ArticleBuilder AnArticle() => new();

        public ArticleBuilder Commented()
        {
            _comments.Add(CommentText, Author);
            return this;
        }

        public Article Build()
        {
            var article = new Article(
                "Lorem Ipsum",
                "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
            );

            _comments
                .ToList()
                .ForEach(comment => article.AddComment(comment.Key, comment.Value));

            return article;
        }
    }
}