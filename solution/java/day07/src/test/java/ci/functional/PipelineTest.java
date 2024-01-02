package ci.functional;

import ci.CapturingLogger;
import ci.dependencies.Config;
import ci.dependencies.Emailer;
import ci.dependencies.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static ci.dependencies.Project.ProjectBuilder;
import static ci.dependencies.Project.builder;
import static ci.dependencies.TestStatus.*;
import static java.util.Arrays.stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PipelineTest {
    private final Config config = mock(Config.class);
    private final CapturingLogger log = new CapturingLogger();
    private final Emailer emailer = mock(Emailer.class);

    private ci.functional.Pipeline pipeline;

    @BeforeEach
    void setUp() {
        pipeline = new Pipeline(config, emailer, log);
    }

    @Test
    void project_with_tests_that_deploys_successfully_with_email_notification() {
        pipeline.run(project(p -> p.setTestStatus(PASSING_TESTS)
                .setDeploysSuccessfully(true), true));

        assertLog("INFO: Tests passed",
                "INFO: Deployment successful",
                "INFO: Sending email");

        verify(emailer).send("Deployment completed successfully");
    }

    @Test
    void project_with_tests_that_deploys_successfully_without_email_notification() {
        pipeline.run(project(p -> p.setTestStatus(PASSING_TESTS)
                .setDeploysSuccessfully(true), false));

        assertLog("INFO: Tests passed",
                "INFO: Deployment successful",
                "INFO: Email disabled");

        verify(emailer, never()).send(any());
    }

    @Test
    void project_without_tests_that_deploys_successfully_with_email_notification() {
        pipeline.run(project(p -> p.setTestStatus(NO_TESTS)
                .setDeploysSuccessfully(true), true));

        assertLog("INFO: No tests",
                "INFO: Deployment successful",
                "INFO: Sending email");

        verify(emailer).send("Deployment completed successfully");
    }

    @Test
    void project_without_tests_that_deploys_successfully_without_email_notification() {
        pipeline.run(project(p -> p.setTestStatus(NO_TESTS)
                .setDeploysSuccessfully(true), false));

        assertLog("INFO: No tests",
                "INFO: Deployment successful",
                "INFO: Email disabled");

        verify(emailer, never()).send(any());
    }

    @Test
    void project_with_tests_that_fail_with_email_notification() {
        pipeline.run(project(p -> p.setTestStatus(FAILING_TESTS), true));

        assertLog("ERROR: Tests failed",
                "INFO: Sending email");

        verify(emailer).send("Tests failed");
    }

    @Test
    void project_with_tests_that_fail_without_email_notification() {
        pipeline.run(project(p -> p.setTestStatus(FAILING_TESTS), false));

        assertLog("ERROR: Tests failed",
                "INFO: Email disabled");

        verify(emailer, never()).send(any());
    }

    @Test
    void project_with_tests_and_failing_build_with_email_notification() {
        pipeline.run(project(p -> p.setTestStatus(PASSING_TESTS)
                .setDeploysSuccessfully(false), true));

        assertLog("INFO: Tests passed",
                "ERROR: Deployment failed",
                "INFO: Sending email");

        verify(emailer).send("Deployment failed");
    }

    @Test
    void project_with_tests_and_failing_build_without_email_notification() {
        pipeline.run(project(p -> p.setTestStatus(PASSING_TESTS)
                .setDeploysSuccessfully(false), false));

        assertLog("INFO: Tests passed",
                "ERROR: Deployment failed",
                "INFO: Email disabled");

        verify(emailer, never()).send(any());
    }

    @Test
    void project_without_tests_and_failing_build_with_email_notification() {
        pipeline.run(project(p -> p.setTestStatus(NO_TESTS)
                .setDeploysSuccessfully(false), true));

        assertLog("INFO: No tests",
                "ERROR: Deployment failed",
                "INFO: Sending email");

        verify(emailer).send("Deployment failed");
    }

    @Test
    void project_without_tests_and_failing_build_without_email_notification() {
        pipeline.run(project(p -> p.setTestStatus(NO_TESTS)
                .setDeploysSuccessfully(false), false));

        assertLog("INFO: No tests",
                "ERROR: Deployment failed",
                "INFO: Email disabled");

        verify(emailer, never()).send(any());
    }

    private Project project(
            Function<ProjectBuilder, ProjectBuilder> project,
            boolean shouldSendEmail) {
        when(config.sendEmailSummary()).thenReturn(shouldSendEmail);
        return project.apply(builder()).build();
    }

    private void assertLog(String... expectedLines) {
        assertThat(log.getLoggedLines())
                .isEqualTo(stream(expectedLines).toList());
    }
}