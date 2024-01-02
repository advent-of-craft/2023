package ci.functional;

import ci.dependencies.Config;
import ci.dependencies.Emailer;
import ci.dependencies.Logger;
import ci.dependencies.Project;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;

import static io.vavr.control.Option.some;

@AllArgsConstructor
public class Pipeline {
    private final Config config;
    private final Emailer emailer;
    private final Logger log;
    private static final String SUCCESS = "success";

    public void run(Project project) {
        createProjectContext(project)
                .flatMap(this::runTests)
                .flatMap(this::deploy);
    }

    private Option<PipelineContext> createProjectContext(Project project) {
        return some(new PipelineContext(project))
                .peek(context -> {
                    if (!context.isHasTests()) log.info("No tests");
                });
    }

    private Option<PipelineContext> runTests(PipelineContext context) {
        return context.hasTests
                ? runTestsSafely(context)
                : some(context);
    }

    private Option<PipelineContext> runTestsSafely(PipelineContext context) {
        return some(context.getProject().runTests())
                .map(testsResult -> context.withTestsRanSuccessfully(testsResult.equals(SUCCESS)))
                .peek(this::logTestResult);
    }

    private void logTestResult(PipelineContext context) {
        if (!context.isTestsRanSuccessfully()) {
            log.error("Tests failed");
            sendEmail("Tests failed");
            return;
        }
        log.info("Tests passed");
    }

    private Option<PipelineContext> deploy(PipelineContext context) {
        return context.mustRunDeployment()
                ? deploySafely(context)
                : some(context);
    }

    private Option<PipelineContext> deploySafely(PipelineContext context) {
        return some(context.getProject().deploy())
                .map(deploymentResult -> context.withDeployedSuccessfully(deploymentResult.equals(SUCCESS)))
                .peek(this::logDeploymentResult);
    }

    private void logDeploymentResult(PipelineContext context) {
        if (!context.isDeployedSuccessfully()) {
            log.error("Deployment failed");
            sendEmail("Deployment failed");
            return;
        }
        log.info("Deployment successful");
        sendEmail("Deployment completed successfully");
    }

    private void sendEmail(String text) {
        if (!config.sendEmailSummary()) {
            log.info("Email disabled");
            return;
        }
        log.info("Sending email");
        emailer.send(text);
    }

    @Getter
    @AllArgsConstructor
    @With
    private static class PipelineContext {
        private final boolean hasTests;
        private final Project project;
        private boolean testsRanSuccessfully;
        private boolean deployedSuccessfully;

        public PipelineContext(Project project) {
            this.project = project;
            this.hasTests = project.hasTests();
        }

        public boolean mustRunDeployment() {
            return testsRanSuccessfully || !hasTests;
        }
    }
}