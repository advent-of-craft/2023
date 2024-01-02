using System.Collections.Immutable;
using LanguageExt;

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

        public Either<Error, Article> AddComment(string text, string author)
        {
            var comment = new Comment(text, author, Now());

            return Comments.Contains(comment)
                ? new Error("This comment already exists in this article")
                : new Article(_name, _content, Comments.Append(comment));
        }

        private static DateOnly Now() => DateOnly.FromDateTime(DateTime.Now);
    }

    public record Error(string Message);

    public record Comment(string Text, string Author, DateOnly CreationDate);
}