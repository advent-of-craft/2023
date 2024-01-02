using Day07.CI;
using Day07.CI.Dependencies;
using Day07.Tests.Doubles;
using FluentAssertions;
using NSubstitute;
using Xunit;
using static Day07.CI.Dependencies.TestStatus;
using static NSubstitute.Arg;

namespace Day07.Tests
{
    public class PipelineTests
    {
        private readonly IConfig _config = Substitute.For<IConfig>();
        private readonly CapturingLogger _log = new();
        private readonly IEmailer _emailer = Substitute.For<IEmailer>();
        private readonly Pipeline _pipeline;

        public PipelineTests() => _pipeline = new Pipeline(_config, _emailer, _log);

        [Fact]
        public void Project_With_Tests_That_Deploys_Successfully_With_Email_Notification()
        {
            _config.SendEmailSummary().Returns(true);

            var project = Project.Builder()
                .With(PassingTests)
                .Deployed(true)
                .Build();

            _pipeline.Run(project);

            _log.LoggedLines
                .Should()
                .BeEquivalentTo("INFO: Tests passed",
                    "INFO: Deployment successful",
                    "INFO: Sending email");

            _emailer.Received(1).Send("Deployment completed successfully");
        }

        [Fact]
        public void Project_Without_Tests_That_Deploys_Successfully_With_Email_Notification()
        {
            _config.SendEmailSummary().Returns(true);

            var project = Project.Builder()
                .With(NoTests)
                .Deployed(true)
                .Build();

            _pipeline.Run(project);

            _log.LoggedLines
                .Should()
                .BeEquivalentTo("INFO: No tests",
                    "INFO: Deployment successful",
                    "INFO: Sending email");

            _emailer.Received(1).Send("Deployment completed successfully");
        }

        [Fact]
        public void Project_Without_Tests_That_Deploys_Successfully_Without_Email_Notification()
        {
            _config.SendEmailSummary().Returns(false);

            var project = Project.Builder()
                .With(NoTests)
                .Deployed(true)
                .Build();

            _pipeline.Run(project);

            _log.LoggedLines
                .Should()
                .BeEquivalentTo("INFO: No tests",
                    "INFO: Deployment successful",
                    "INFO: Email disabled");

            _emailer.DidNotReceive().Send(Any<string>());
        }

        [Fact]
        public void Project_With_Tests_That_Fail_With_Email_Notification()
        {
            _config.SendEmailSummary().Returns(true);

            var project = Project.Builder()
                .With(FailingTests)
                .Deployed(true)
                .Build();

            _pipeline.Run(project);

            _log.LoggedLines
                .Should()
                .BeEquivalentTo("ERROR: Tests failed",
                    "INFO: Sending email");

            _emailer.Received(1).Send("Tests failed");
        }

        [Fact]
        public void Project_With_Tests_That_Fail_Without_Email_Notification()
        {
            _config.SendEmailSummary().Returns(false);

            var project = Project.Builder()
                .With(FailingTests)
                .Build();

            _pipeline.Run(project);

            _log.LoggedLines
                .Should()
                .BeEquivalentTo("ERROR: Tests failed",
                    "INFO: Email disabled");

            _emailer.DidNotReceive().Send(Any<string>());
        }

        [Fact]
        public void Project_With_Tests_And_Failing_Build_With_Email_Notification()
        {
            _config.SendEmailSummary().Returns(true);

            var project = Project.Builder()
                .With(PassingTests)
                .Deployed(false)
                .Build();

            _pipeline.Run(project);

            _log.LoggedLines
                .Should()
                .BeEquivalentTo("INFO: Tests passed",
                    "ERROR: Deployment failed",
                    "INFO: Sending email");

            _emailer.Received(1).Send("Deployment failed");
        }

        [Fact]
        public void Project_With_Tests_And_Failing_Build_Without_Email_Notification()
        {
            _config.SendEmailSummary().Returns(false);

            var project = Project.Builder()
                .With(PassingTests)
                .Deployed(false)
                .Build();

            _pipeline.Run(project);

            _log.LoggedLines
                .Should()
                .BeEquivalentTo("INFO: Tests passed",
                    "ERROR: Deployment failed",
                    "INFO: Email disabled");

            _emailer.DidNotReceive().Send(Any<string>());
        }

        [Fact]
        public void Project_Without_Tests_And_Failing_Build_With_Email_Notification()
        {
            _config.SendEmailSummary().Returns(true);

            var project = Project.Builder()
                .With(NoTests)
                .Deployed(false)
                .Build();

            _pipeline.Run(project);

            _log.LoggedLines
                .Should()
                .BeEquivalentTo("INFO: No tests",
                    "ERROR: Deployment failed",
                    "INFO: Sending email");

            _emailer.Received(1).Send("Deployment failed");
        }

        [Fact]
        public void Project_Without_Tests_And_Failing_Build_Without_Email_Notification()
        {
            _config.SendEmailSummary().Returns(false);

            var project = Project.Builder()
                .With(NoTests)
                .Deployed(false)
                .Build();

            _pipeline.Run(project);

            _log.LoggedLines
                .Should()
                .BeEquivalentTo("INFO: No tests",
                    "ERROR: Deployment failed",
                    "INFO: Email disabled");

            _emailer.DidNotReceive().Send(Any<string>());
        }
    }
}