using System.Collections.Immutable;

namespace Day19
{
    public class Article
    {
        private readonly string _name;
        private readonly string _content;
        public ImmutableArray<Comment> Comments { get; }

        private Article(string name, string content, IEnumerable<Comment> comments)
        {
            _name = name;
            _content = content;
            Comments = comments.ToImmutableArray();
        }

        public Article(string name, string content)
            : this(name, content, Array.Empty<Comment>())
        {
        }

        private Article AddComment(
            string text,
            string author,
            DateOnly creationDate)
        {
            var comment = new Comment(text, author, creationDate);

            return Comments.Contains(comment)
                ? throw new CommentAlreadyExistException()
                : new Article(_name, _content, Comments.Append(comment));
        }

        public Article AddComment(string text, string author)
            => AddComment(text, author, DateOnly.FromDateTime(DateTime.Now));
    }

    public record Comment(string Text, string Author, DateOnly CreationDate);

    public class CommentAlreadyExistException : ArgumentException;
}