using Day23.Exception;
using Day23.Users;
using LanguageExt;

namespace Day23.Trips
{
    public static class TripDAO
    {
        public static Seq<Trip> FindTripsByUser(User user)
        {
            throw new CollaboratorCallException("TripDAO should not be invoked on an unit test.");
        }
    }
}