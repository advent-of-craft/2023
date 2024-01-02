package ci

class Pipeline(
    private val config: Config,
    private val emailSender: Emailer,
    private val log: Logger
) {
    fun run(project: Project) {
        var testsPassed: Boolean
        val deploySuccessful: Boolean

        testsPassed = if (project.hasTests()) {
            if ("success" == project.runTests()) {
                log.info("Tests passed")
                true
            } else {
                log.error("Tests failed")
                false
            }
        } else {
            log.info("No tests")
            true
        }
        deploySuccessful = if (testsPassed) {
            if ("success" == project.deploy()) {
                log.info("Deployment successful")
                true
            } else {
                log.error("Deployment failed")
                false
            }
        } else {
            false
        }
        if (config.sendEmailSummary()) {
            log.info("Sending email")
            if (testsPassed) {
                if (deploySuccessful) {
                    emailSender("Deployment completed successfully")
                } else {
                    emailSender("Deployment failed")
                }
            } else {
                emailSender("Tests failed")
            }
        } else {
            log.info("Email disabled")
        }
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

class Project private constructor(private val buildsSuccessfully: Boolean, private val testStatus: TestStatus) {
    fun hasTests(): Boolean = testStatus !== TestStatus.NO_TESTS
    fun runTests(): String = if (testStatus === TestStatus.PASSING_TESTS) "success" else "failure"
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
    NO_TESTS,
    PASSING_TESTS,
    FAILING_TESTS
}