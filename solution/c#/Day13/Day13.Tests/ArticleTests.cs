using FluentAssertions;
using Xunit;
using static Day13.Tests.ArticleBuilder;

namespace Day13.Tests
{
    public class ArticleTests
    {
        [Fact]
        public void Should_Add_Comment_In_An_Article()
        {
            var article = AnArticle().Build();

            article.AddComment(CommentText, Author);

            article.Comments.Should().HaveCount(1);
            AssertComment(article.Comments[0], CommentText, Author);
        }

        [Fact]
        public void Should_Add_Comment_In_An_Article_Containing_Already_A_Comment()
        {
            const string newComment = "Finibus Bonorum et Malorum";
            const string newAuthor = "Al Capone";

            var article = AnArticle()
                .Commented()
                .Build();

            article.AddComment(newComment, newAuthor);

            article.Comments.Should().HaveCount(2);
            AssertComment(article.Comments[1], newComment, newAuthor);
        }

        private static void AssertComment(Comment comment, string expectedComment, string expectedAuthor)
        {
            comment.Text.Should().Be(expectedComment);
            comment.Author.Should().Be(expectedAuthor);
            comment.CreationDate.Should().Be(DateOnly.FromDateTime(DateTime.Now));
        }

        public class Fail
        {
            [Fact]
            public void When_Adding_An_Existing_Comment()
            {
                var article = AnArticle().Build();
                article.AddComment(CommentText, Author);

                var act = () => article.AddComment(CommentText, Author);
                act.Should().Throw<CommentAlreadyExistException>();
            }
        }
    }
}