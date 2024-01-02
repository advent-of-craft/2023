using Day23.Exception;
using Day23.Trips;
using Day23.Users;
using FluentAssertions;
using FluentAssertions.LanguageExt;
using NSubstitute;
using Xunit;
using static Day23.Tests.Users.UserBuilder;

namespace Day23.Tests.Trips
{
    public class GetTripsByUserShould
    {
        private static readonly User RegisteredUser = AUser().Build();

        private readonly ITripRepository _repository = Substitute.For<ITripRepository>();
        private readonly TripService _tripService;

        private GetTripsByUserShould() => _tripService = new(_repository);

        public class Return : GetTripsByUserShould
        {
            private static readonly Trip Lisbon = new();
            private static readonly Trip Springfield = new();
            private static readonly User AnotherUser = AUser().Build();

            [Fact]
            public void No_Trips_When_Logged_User_Is_Not_A_Friend_Of_The_Target_User()
            {
                var aUserWithTrips = AUser()
                    .FriendsWith(AnotherUser)
                    .TravelledTo(Lisbon)
                    .Build();

                _tripService.RetrieveFriendTrips(
                        aUserWithTrips,
                        RegisteredUser)
                    .Should()
                    .BeSuccess(trips => trips.Should().BeEmpty());
            }

            [Fact]
            public void All_The_Target_User_Trips_When_Logged_User_Is_A_Friend_Of_The_Target_User()
                // Alternative syntax using Functional Extensions for our tests
                => AUser()
                    .FriendsWith(AnotherUser, RegisteredUser)
                    .TravelledTo(Lisbon, Springfield).Build()
                    .Let(aUserWithTrips =>
                    {
                        _repository.FindTripsByUser(aUserWithTrips)
                            .Returns(aUserWithTrips.Trips());

                        _tripService.RetrieveFriendTrips(
                                aUserWithTrips,
                                RegisteredUser)
                            .Should()
                            .BeSuccess(trips => trips.Should().ContainInOrder(Lisbon, Springfield));
                    });
        }

        public class ReturnAnError : GetTripsByUserShould
        {
            private readonly User? _guest = null;

            [Fact]
            public void When_User_Is_Not_LoggedIn()
                => _tripService
                    .RetrieveFriendTrips(RegisteredUser, _guest).Should()
                    .BeFail()
                    .Which.Should()
                    .BeOfType<UserNotLoggedInException>();
        }
    }
}