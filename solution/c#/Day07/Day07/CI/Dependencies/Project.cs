namespace Day07.CI.Dependencies
{
    public class Project
    {
        private readonly bool _buildsSuccessfully;
        private readonly TestStatus _testStatus;

        private Project(bool buildsSuccessfully, TestStatus testStatus)
        {
            _buildsSuccessfully = buildsSuccessfully;
            _testStatus = testStatus;
        }

        public static ProjectBuilder Builder() => new();

        public bool HasTests() => _testStatus != TestStatus.NoTests;
        public string RunTests() => _testStatus == TestStatus.PassingTests ? "success" : "failure";
        public string Deploy() => _buildsSuccessfully ? "success" : "failure";

        public class ProjectBuilder
        {
            private bool _buildsSuccessfully;
            private TestStatus _testStatus;

            public ProjectBuilder With(TestStatus testStatus)
            {
                _testStatus = testStatus;
                return this;
            }

            public ProjectBuilder Deployed(bool buildsSuccessfully)
            {
                _buildsSuccessfully = buildsSuccessfully;
                return this;
            }

            public Project Build() => new(_buildsSuccessfully, _testStatus);
        }
    }
}