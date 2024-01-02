using Day23.Exception;
using Day23.Tests.Users;
using Day23.Trips;
using FluentAssertions;
using Xunit;

namespace Day23.Tests.Trips
{
    public class TripRepositoryShould
    {
        private readonly ITripRepository _tripRepository = new TripRepository();

        [Fact]
        public void Retrieve_User_Trips_Throw_An_Exception()
        {
            var findTrips = () => _tripRepository.FindTripsByUser(UserBuilder.AUser().Build());
            findTrips.Should().Throw<CollaboratorCallException>();
        }

        private class TripRepository : ITripRepository;
    }
}