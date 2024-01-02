namespace Day13.Tests
{
    public class ArticleBuilder
    {
        public const string Author = "Pablo Escobar";
        public const string CommentText = "Amazing article !!!";

        private readonly Dictionary<string, string> _comments = new();
        private readonly Bogus.Randomizer _random = new();

        public static ArticleBuilder AnArticle() => new();

        public ArticleBuilder Commented()
        {
            _comments.Add(CommentText, Author);
            return this;
        }

        public Article Build()
        {
            var article = new Article(_random.String(), _random.String());

            _comments
                .ToList()
                .ForEach(comment => article.AddComment(comment.Key, comment.Value));

            return article;
        }
    }
}