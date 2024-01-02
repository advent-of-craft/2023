using FluentAssertions;
using Xunit;
using static Day19.Tests.ArticleBuilder;

namespace Day19.Tests
{
    public class ArticleTests
    {
        private Article _article;
        private readonly Bogus.Randomizer _random = new();

        [Fact]
        public void Should_Add_Comment_In_An_Article()
        {
            Given(AnArticle());
            When(article => article.AddComment(CommentText, Author));
            Then(article =>
            {
                article.Comments.Should().HaveCount(1);
                AssertComment(article.Comments[0], CommentText, Author);
            });
        }

        [Fact]
        public void Should_Add_Comment_In_An_Article_Containing_Already_A_Comment()
        {
            var newComment = _random.String(10);
            var newAuthor = _random.String(3);

            Given(AnArticle().Commented());
            When(article => article.AddComment(newComment, newAuthor));
            Then(article =>
            {
                article.Comments.Should().HaveCount(2);
                AssertComment(article.Comments[1], newComment, newAuthor);
            });
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
                var article = AnArticle().Build()
                    .AddComment(CommentText, Author);

                var act = () => article.AddComment(CommentText, Author);
                act.Should().Throw<CommentAlreadyExistException>();
            }
        }

        private void Given(ArticleBuilder articleBuilder) => _article = articleBuilder.Build();
        private void When(Func<Article, Article> act) => _article = act(_article);
        private void Then(Action<Article> act) => act(_article);
    }
}