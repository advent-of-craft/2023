using Day23.Exception;
using Day23.Users;
using LanguageExt;
using static LanguageExt.Prelude;
using Seq = LanguageExt.Seq;

namespace Day23.Trips
{
    public class TripService(ITripRepository repository)
    {
        private static readonly Seq<Trip> NoTrips = Seq.empty<Trip>();

        public Try<Seq<Trip>> RetrieveFriendTrips(User user, User? loggedUser)
            => CheckUser(loggedUser, () => RetrieveFriendsSafely(user, loggedUser!));

        private static Try<Seq<Trip>> CheckUser(
            User? loggedUser,
            Func<Seq<Trip>> continueWith)
            => loggedUser != null
                ? Try(continueWith())
                : Try<Seq<Trip>>(new UserNotLoggedInException());

        private Seq<Trip> RetrieveFriendsSafely(User user, User loggedUser)
            => user.IsFriendWith(loggedUser)
                ? FindTripsByUser(user)
                : NoTrips;

        private Seq<Trip> FindTripsByUser(User user) => repository.FindTripsByUser(user);
    }
}