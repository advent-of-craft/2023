using Day23.Exception;

namespace Day23.Trip
{
    public class TripDAO
    {
        public static List<Trip> FindTripsByUser(User.User user)
        {
            throw new CollaboratorCallException("TripDAO should not be invoked on an unit test.");
        }
    }
}