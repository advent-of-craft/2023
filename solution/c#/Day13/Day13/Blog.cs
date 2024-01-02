namespace Day13
{
    public class Article
    {
        private readonly string _name;
        private readonly string _content;
        public List<Comment> Comments { get; }

        public Article(string name, string content)
        {
            _name = name;
            _content = content;
            Comments = new List<Comment>();
        }

        private void AddComment(
            string text,
            string author,
            DateOnly creationDate)
        {
            var comment = new Comment(text, author, creationDate);
            if (Comments.Contains(comment))
            {
                throw new CommentAlreadyExistException();
            }
            else Comments.Add(comment);
        }

        public void AddComment(string text, string author)
            => AddComment(text, author, DateOnly.FromDateTime(DateTime.Now));
    }

    public record Comment(string Text, string Author, DateOnly CreationDate);

    public class CommentAlreadyExistException : ArgumentException;
}