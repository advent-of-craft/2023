using Day23.Users;
using LanguageExt;

namespace Day23.Trips
{
    public interface ITripRepository
    {
        public Seq<Trip> FindTripsByUser(User user) => TripDAO.FindTripsByUser(user);
    }
}