using Day23.Users;
using FluentAssertions;
using Xunit;
using static Day23.Tests.Users.UserBuilder;

namespace Day23.Tests.Users
{
    public class UserShould
    {
        private readonly User _rick = AUser().Build();
        private readonly User _morty = AUser().Build();

        [Fact]
        public void Return_False_When_2_Users_Are_Not_Friends()
            => AUser()
                .FriendsWith(_rick).Build()
                .IsFriendWith(_morty)
                .Should()
                .BeFalse();

        [Fact]
        public void Return_True_When_2_Users_Are_Friends()
            => AUser()
                .FriendsWith(_rick, _morty).Build()
                .IsFriendWith(_morty)
                .Should()
                .BeTrue();
    }
}