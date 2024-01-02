using FluentAssertions;
using Xunit;

namespace Day04.Tests
{
    public class ArticleTests
    {
        [Fact]
        public void It_Should_Add_Valid_Comment()
        {
            var article = new Article(
                "Lorem Ipsum",
                "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
            );

            article.AddComment("Amazing article !!!", "Pablo Escobar");
        }

        [Fact]
        public void It_Should_Add_A_Comment_With_The_Given_Text()
        {
            var article = new Article(
                "Lorem Ipsum",
                "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
            );

            const string text = "Amazing article !!!";
            article.AddComment(text, "Pablo Escobar");

            article.Comments
                .Should().HaveCount(1)
                .And.ContainSingle(comment => comment.Text == text);
        }

        [Fact]
        public void It_Should_Add_A_Comment_With_The_Given_Author()
        {
            var article = new Article(
                "Lorem Ipsum",
                "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
            );

            const string author = "Pablo Escobar";
            article.AddComment("Amazing article !!!", author);

            article.Comments
                .Should().HaveCount(1)
                .And.ContainSingle(comment => comment.Author == author);
        }

        [Fact]
        public void It_Should_Add_A_Comment_With_The_Date_Of_The_Day()
        {
            var article = new Article(
                "Lorem Ipsum",
                "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
            );

            article.AddComment("Amazing article !!!", "Pablo Escobar");

            var today = DateOnly.FromDateTime(DateTime.Today);
            article.Comments
                .Should().ContainSingle(comment => comment.CreationDate == today);
        }

        [Fact]
        public void It_Should_Throw_An_Exception_When_Adding_Existing_Comment()
        {
            var article = new Article(
                "Lorem Ipsum",
                "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
            );
            article.AddComment("Amazing article !!!", "Pablo Escobar");

            var act = () => article.AddComment("Amazing article !!!", "Pablo Escobar");
            act.Should().Throw<CommentAlreadyExistException>();
        }
    }
}