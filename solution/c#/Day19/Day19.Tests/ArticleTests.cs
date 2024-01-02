using FluentAssertions;
using FluentAssertions.LanguageExt;
using LanguageExt;
using LanguageExt.UnsafeValueAccess;
using Xunit;
using static Day19.Tests.ArticleBuilder;

namespace Day19.Tests
{
    public class ArticleTests
    {
        private Either<Error, Article> _result;
        private readonly Bogus.Randomizer _random = new();

        [Fact]
        public void Should_Add_Comment_In_An_Article()
        {
            Given(AnArticle());
            When(article => article.AddComment(CommentText, Author));
            Then(result
                => result.Should()
                    .BeRight(article =>
                        {
                            article.Comments.Should().HaveCount(1);
                            AssertComment(article.Comments[0], CommentText, Author);
                        }
                    ));
        }

        [Fact]
        public void Should_Add_Comment_In_An_Article_Containing_Already_A_Comment()
        {
            var newComment = _random.String(10);
            var newAuthor = _random.String(3);

            Given(AnArticle().Commented());
            When(article => article.AddComment(newComment, newAuthor));
            Then(result
                => result.Should()
                    .BeRight(article =>
                    {
                        article.Comments.Should().HaveCount(2);
                        AssertComment(article.Comments[1], newComment, newAuthor);
                    })
            );
        }

        private static void AssertComment(Comment comment, string expectedComment, string expectedAuthor)
        {
            comment.Text.Should().Be(expectedComment);
            comment.Author.Should().Be(expectedAuthor);
            comment.CreationDate.Should().Be(DateOnly.FromDateTime(DateTime.Now));
        }

        public class Fail : ArticleTests
        {
            [Fact]
            public void When_Adding_An_Existing_Comment()
            {
                Given(AnArticle().Commented());
                When(article => article.AddComment(CommentText, Author));
                Then(result
                    => result.Should()
                        .BeLeft(error =>
                            error.Should()
                                .Be(new Error("This comment already exists in this article"))
                        ));
            }
        }

        private void Given(ArticleBuilder articleBuilder) => _result = articleBuilder.Build();
        private void When(Func<Article, Either<Error, Article>> act) => _result = act(_result.ValueUnsafe());
        private void Then(Action<Either<Error, Article>> act) => act(_result);
    }
}