using Day23.Trips;
using Day23.Users;
using LanguageExt;
using static LanguageExt.Seq;

namespace Day23.Tests.Users
{
    public class UserBuilder
    {
        private Seq<User> _friends = empty<User>();
        private Seq<Trip> _trips = empty<Trip>();

        public static UserBuilder AUser() => new();

        public UserBuilder FriendsWith(params User[] friends)
            => this.Let(builder => builder._friends = friends.ToSeq());

        public UserBuilder TravelledTo(params Trip[] trips)
            => this.Let(builder => builder._trips = trips.ToSeq());

        public User Build() => new User(_trips, _friends);
    }
}