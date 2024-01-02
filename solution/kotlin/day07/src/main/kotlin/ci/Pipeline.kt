package ci

import arrow.core.Option
import arrow.core.Some
import ci.TestStatus.NoTests
import ci.TestStatus.PassingTests

class Pipeline(
    private val config: Config,
    private val emailSender: Emailer,
    private val log: Logger
) {
    private val success = "success"

    fun run(project: Project) {
        createProjectContext(project)
            .flatMap { runTests(it) }
            .flatMap { deploy(it) }
    }

    private fun createProjectContext(project: Project): Option<PipelineContext> =
        Some(PipelineContext(project))
            .onSome { context -> if (!context.hasTests) log.info("No tests") }

    private fun runTests(context: PipelineContext): Option<PipelineContext> =
        if (context.hasTests) runTestsSafely(context)
        else Some(context)

    private fun runTestsSafely(context: PipelineContext): Option<PipelineContext> =
        Some(context.project.runTests())
            .map { testsResult -> context.withTestsResults(testsResult == success) }
            .onSome { logTestResult(it) }

    private fun logTestResult(context: PipelineContext) {
        if (!context.testsRanSuccessfully) {
            log.error("Tests failed")
            sendEmail("Tests failed")
            return
        }
        log.info("Tests passed")
    }

    private fun deploy(context: PipelineContext): Option<PipelineContext> =
        if (context.mustRunDeployment()) deploySafely(context) else Some(context)

    private fun deploySafely(context: PipelineContext): Option<PipelineContext> =
        Some(context.project.deploy())
            .map { deploymentResult -> context.withDeploymentStatus(deploymentResult == success) }
            .onSome { logDeploymentResult(it) }

    private fun logDeploymentResult(context: PipelineContext) {
        if (!context.deployedSuccessfully) {
            log.error("Deployment failed")
            sendEmail("Deployment failed")
            return
        }
        log.info("Deployment successful")
        sendEmail("Deployment completed successfully")
    }

    private fun sendEmail(text: String) {
        if (!config.sendEmailSummary()) {
            log.info("Email disabled")
            return
        }
        log.info("Sending email");
        emailSender(text);
    }
}

fun interface Config {
    fun sendEmailSummary(): Boolean
}

typealias Emailer = (message: String) -> Unit

interface Logger {
    fun info(message: String)
    fun error(message: String)
}

data class PipelineContext(val project: Project) {
    val hasTests: Boolean = project.hasTests()
    var testsRanSuccessfully = false
    var deployedSuccessfully = false
    fun mustRunDeployment(): Boolean {
        return testsRanSuccessfully || !hasTests
    }

    fun withTestsResults(testsResult: Boolean): PipelineContext {
        testsRanSuccessfully = testsResult
        return this
    }

    fun withDeploymentStatus(deploymentStatus: Boolean): PipelineContext {
        deployedSuccessfully = deploymentStatus
        return this
    }
}

class Project private constructor(private val buildsSuccessfully: Boolean, private val testStatus: TestStatus) {
    fun hasTests(): Boolean = testStatus != NoTests
    fun runTests(): String = if (testStatus == PassingTests) "success" else "failure"
    fun deploy(): String = if (buildsSuccessfully) "success" else "failure"

    class ProjectBuilder {
        private var buildsSuccessfully = false
        private var testStatus: TestStatus? = null
        fun setTestStatus(testStatus: TestStatus?): ProjectBuilder {
            this.testStatus = testStatus
            return this
        }

        fun setDeploysSuccessfully(buildsSuccessfully: Boolean): ProjectBuilder {
            this.buildsSuccessfully = buildsSuccessfully
            return this
        }

        fun build(): Project = Project(buildsSuccessfully, testStatus!!)
    }
}

enum class TestStatus {
    NoTests,
    PassingTests,
    FailingTests
}