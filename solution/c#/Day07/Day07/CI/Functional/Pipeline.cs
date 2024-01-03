using Day07.CI.Dependencies;
using LanguageExt;

namespace Day07.CI.Functional
{
    public class Pipeline(IConfig config, IEmailer emailer, ILogger log)
    {
        private const string Success = "success";

        public Option<PipelineContext> Run(Project project)
            => ContextFor(project)
                .Bind(RunTests)
                .Bind(Deploy);

        private Option<PipelineContext> ContextFor(Project project)
            => Option<PipelineContext>.Some(new PipelineContext(project))
                .Do(context =>
                {
                    if (!context.HasTests)
                        log.Info("No tests");
                });

        private Option<PipelineContext> RunTests(PipelineContext context)
            => context.HasTests
                ? RunTestsSafely(context)
                : context;

        private Option<PipelineContext> RunTestsSafely(PipelineContext context)
            => Option<string>.Some(context.Project.RunTests())
                .Map(testsResult => context.TestsRan(testsResult == Success))
                .Do(LogTestResult);

        private void LogTestResult(PipelineContext context)
        {
            if (!context.TestsRanSuccessfully)
            {
                log.Error("Tests failed");
                SendEmail("Tests failed");
                return;
            }

            log.Info("Tests passed");
        }

        private Option<PipelineContext> Deploy(PipelineContext context)
            => context.MustRunDeployment()
                ? DeploySafely(context)
                : context;

        private Option<PipelineContext> DeploySafely(PipelineContext context)
            => Option<string>.Some(context.Project.Deploy())
                .Map(deploymentResult => context.Deployed(deploymentResult == Success))
                .Do(LogDeploymentResult);

        private void LogDeploymentResult(PipelineContext context)
        {
            if (!context.DeployedSuccessfully)
            {
                log.Error("Deployment failed");
                SendEmail("Deployment failed");
                return;
            }

            log.Info("Deployment successful");
            SendEmail("Deployment completed successfully");
        }

        private void SendEmail(String text)
        {
            if (!config.SendEmailSummary())
            {
                log.Info("Email disabled");
                return;
            }

            log.Info("Sending email");
            emailer.Send(text);
        }

        public sealed class PipelineContext(Project project)
        {
            public bool MustRunDeployment() => TestsRanSuccessfully || !HasTests;

            public bool HasTests => project.HasTests();
            public Project Project => project;
            public bool TestsRanSuccessfully { get; private set; }
            public bool DeployedSuccessfully { get; private set; }

            public PipelineContext TestsRan(bool success)
            {
                TestsRanSuccessfully = success;
                return this;
            }

            public PipelineContext Deployed(bool success)
            {
                DeployedSuccessfully = success;
                return this;
            }
        }
    }
}