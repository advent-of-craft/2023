using FluentAssertions;
using Xunit;

namespace Day04.Tests
{
    public class ArticleTests
    {
        private const string Author = "Pablo Escobar";
        private const string CommentText = "Amazing article !!!";

        private readonly Article _article = new(
            "Lorem Ipsum",
            "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        );

        [Fact]
        public void Should_Add_Comment_In_An_Article()
        {
            _article.AddComment(CommentText, Author);

            _article.Comments
                .Should().HaveCount(1)
                .And.ContainSingle(comment => comment.Text == CommentText && comment.Author == Author);
        }

        [Fact]
        public void Should_Add_Comment_In_An_Article_Containing_Already_A_Comment()
        {
            const string newComment = "Finibus Bonorum et Malorum";
            const string newAuthor = "Al Capone";

            _article.AddComment(CommentText, Author);
            _article.AddComment(newComment, newAuthor);

            _article.Comments.Should().HaveCount(2);

            var lastComment = _article.Comments.Last();
            lastComment.Text.Should().Be(newComment);
            lastComment.Author.Should().Be(newAuthor);
        }

        public class Fail : ArticleTests
        {
            [Fact]
            public void When_Adding_An_Existing_Comment()
            {
                _article.AddComment(CommentText, Author);

                var act = () => _article.AddComment(CommentText, Author);
                act.Should().Throw<CommentAlreadyExistException>();
            }
        }
    }
}