using Day23.Exception;

namespace Day23.User
{
    public class UserSession
    {
        private static readonly UserSession userSession = new();

        public static UserSession GetInstance() => userSession;

        public bool IsUserLoggedIn(User user) =>
            throw new CollaboratorCallException(
                "UserSession.IsUserLoggedIn() should not be called in an unit test");

        public User GetLoggedUser() =>
            throw new CollaboratorCallException("UserSession.GetLoggedUser() should not be called in an unit test");
    }
}