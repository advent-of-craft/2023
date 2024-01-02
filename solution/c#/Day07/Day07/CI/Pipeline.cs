using Day07.CI.Dependencies;

namespace Day07.CI
{
    public class Pipeline(IConfig config, IEmailer emailer, ILogger log)
    {
        private const string Success = "success";

        public void Run(Project project)
        {
            if (RunTestsFailed(project))
            {
                SendEmail("Tests failed");
                return;
            }

            if (DunDeploymentFailed(project))
            {
                SendEmail("Deployment failed");
                return;
            }

            SendEmail("Deployment completed successfully");
        }

        private bool RunTestsFailed(Project project)
        {
            if (!project.HasTests())
            {
                log.Info("No tests");
                return false;
            }

            if (project.RunTests() != Success)
            {
                log.Error("Tests failed");
                return true;
            }

            log.Info("Tests passed");
            return false;
        }

        private bool DunDeploymentFailed(Project project)
        {
            if (project.Deploy() == Success)
            {
                log.Info("Deployment successful");
                return false;
            }

            log.Error("Deployment failed");
            return true;
        }

        private void SendEmail(string text)
        {
            if (!config.SendEmailSummary())
            {
                log.Info("Email disabled");
                return;
            }

            log.Info("Sending email");
            emailer.Send(text);
        }
    }
}