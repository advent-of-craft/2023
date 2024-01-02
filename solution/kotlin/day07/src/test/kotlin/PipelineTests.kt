import ci.Config
import ci.Emailer
import ci.Pipeline
import ci.Project.ProjectBuilder
import ci.TestStatus.*
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class PipelineTests : StringSpec({
    lateinit var config: Config
    lateinit var emailer: Emailer
    lateinit var log: CapturingLogger

    beforeEach {
        config = mockk()
        emailer = mockk()
        log = CapturingLogger()

        every { emailer(any()) } returns Unit
    }

    "project with tests that deploys successfully with email notification" {
        every { config.sendEmailSummary() } returns true

        ProjectBuilder()
            .setTestStatus(PassingTests)
            .setDeploysSuccessfully(true)
            .build()
            .let {
                Pipeline(config, emailer, log).run(it)
            }

        log.lines shouldBe listOf(
            "INFO: Tests passed",
            "INFO: Deployment successful",
            "INFO: Sending email"
        )
        verify { emailer("Deployment completed successfully") }
    }

    "project with tests that deploys successfully without email notification" {
        every { config.sendEmailSummary() } returns false

        ProjectBuilder()
            .setTestStatus(PassingTests)
            .setDeploysSuccessfully(true)
            .build()
            .let {
                Pipeline(config, emailer, log).run(it)
            }

        log.lines shouldBe listOf(
            "INFO: Tests passed",
            "INFO: Deployment successful",
            "INFO: Email disabled"
        )
        verify { emailer wasNot Called }
    }

    "project without tests that deploys successfully with email notification" {
        every { config.sendEmailSummary() } returns true

        ProjectBuilder()
            .setTestStatus(NoTests)
            .setDeploysSuccessfully(true)
            .build()
            .let {
                Pipeline(config, emailer, log).run(it)
            }

        log.lines shouldBe listOf(
            "INFO: No tests",
            "INFO: Deployment successful",
            "INFO: Sending email"
        )
        verify { emailer("Deployment completed successfully") }
    }

    "project without tests that deploys successfully without email notification" {
        every { config.sendEmailSummary() } returns false

        ProjectBuilder()
            .setTestStatus(NoTests)
            .setDeploysSuccessfully(true)
            .build()
            .let {
                Pipeline(config, emailer, log).run(it)
            }

        log.lines shouldBe listOf(
            "INFO: No tests",
            "INFO: Deployment successful",
            "INFO: Email disabled"
        )
        verify { emailer wasNot Called }
    }

    "project with tests that fail with email notification" {
        every { config.sendEmailSummary() } returns true

        ProjectBuilder()
            .setTestStatus(FailingTests)
            .build()
            .let {
                Pipeline(config, emailer, log).run(it)
            }

        log.lines shouldBe listOf(
            "ERROR: Tests failed",
            "INFO: Sending email"
        )
        verify { emailer("Tests failed") }
    }

    "project with tests that fail without email notification" {
        every { config.sendEmailSummary() } returns false

        ProjectBuilder()
            .setTestStatus(FailingTests)
            .build()
            .let {
                Pipeline(config, emailer, log).run(it)
            }

        log.lines shouldBe listOf(
            "ERROR: Tests failed",
            "INFO: Email disabled"
        )
        verify { emailer wasNot Called }
    }

    "project with tests and failing build with email notification" {
        every { config.sendEmailSummary() } returns true

        ProjectBuilder()
            .setTestStatus(PassingTests)
            .setDeploysSuccessfully(false)
            .build()
            .let {
                Pipeline(config, emailer, log).run(it)
            }

        log.lines shouldBe listOf(
            "INFO: Tests passed",
            "ERROR: Deployment failed",
            "INFO: Sending email"
        )
        verify { emailer("Deployment failed") }
    }

    "project with tests and failing build without email notification" {
        every { config.sendEmailSummary() } returns false

        ProjectBuilder()
            .setTestStatus(PassingTests)
            .setDeploysSuccessfully(false)
            .build()
            .let {
                Pipeline(config, emailer, log).run(it)
            }

        log.lines shouldBe listOf(
            "INFO: Tests passed",
            "ERROR: Deployment failed",
            "INFO: Email disabled"
        )
        verify { emailer wasNot Called }
    }

    "project without tests and failing build with email notification" {
        every { config.sendEmailSummary() } returns true

        ProjectBuilder()
            .setTestStatus(NoTests)
            .setDeploysSuccessfully(false)
            .build()
            .let {
                Pipeline(config, emailer, log).run(it)
            }

        log.lines shouldBe listOf(
            "INFO: No tests",
            "ERROR: Deployment failed",
            "INFO: Sending email"
        )
        verify { emailer("Deployment failed") }
    }

    "project without tests and failing build without email notification" {
        every { config.sendEmailSummary() } returns false

        ProjectBuilder()
            .setTestStatus(NoTests)
            .setDeploysSuccessfully(false)
            .build()
            .let {
                Pipeline(config, emailer, log).run(it)
            }

        log.lines shouldBe listOf(
            "INFO: No tests",
            "ERROR: Deployment failed",
            "INFO: Email disabled"
        )
        verify { emailer wasNot Called }
    }
})