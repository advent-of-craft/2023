import {Config, Emailer, TestStatus} from "../src/ci";
import {Pipeline} from "../src/pipeline";
import {ProjectBuilder} from "../src/project";
import {CapturingLogger} from "./capturingLogger";

describe('Pipeline Tests', () => {
    let config: Config;
    let sendEmailSummary = jest.fn();
    let log: CapturingLogger;
    let emailer: Emailer;
    let pipeline: Pipeline;

    beforeEach(() => {
        config = {sendEmailSummary: sendEmailSummary};
        log = new CapturingLogger();
        emailer = {send: jest.fn()};
        pipeline = new Pipeline(config, emailer, log);
    });

    test('project with tests that deploys successfully with email notification', () => {
        sendEmailSummary.mockReturnValue(true);

        const project = new ProjectBuilder()
            .setTestStatus(TestStatus.PassingTests)
            .setDeploysSuccessfully(true)
            .build();

        pipeline.run(project);

        expect(log.loggedLines()).toEqual([
                'INFO: Tests passed',
                'INFO: Deployment successful',
                'INFO: Sending email'
            ]
        );
        expect(emailer.send).toHaveBeenCalledWith('Deployment completed successfully');
    });

    test('project with tests that deploys successfully without email notification', () => {
        sendEmailSummary.mockReturnValue(false);

        const project = new ProjectBuilder()
            .setTestStatus(TestStatus.PassingTests)
            .setDeploysSuccessfully(true)
            .build();

        pipeline.run(project);

        expect(log.loggedLines()).toEqual([
                'INFO: Tests passed',
                'INFO: Deployment successful',
                'INFO: Email disabled'
            ]
        );
        expect(emailer.send).toHaveBeenCalledTimes(0);
    });

    test('project without tests that deploys successfully with email notification', () => {
        sendEmailSummary.mockReturnValue(true);

        const project = new ProjectBuilder()
            .setTestStatus(TestStatus.NoTests)
            .setDeploysSuccessfully(true)
            .build();

        pipeline.run(project);

        expect(log.loggedLines()).toEqual([
                'INFO: No tests',
                'INFO: Deployment successful',
                'INFO: Sending email'
            ]
        );
        expect(emailer.send).toHaveBeenCalledWith('Deployment completed successfully');
    });

    test('project without tests that deploys successfully without email notification', () => {
        sendEmailSummary.mockReturnValue(false);

        const project = new ProjectBuilder()
            .setTestStatus(TestStatus.NoTests)
            .setDeploysSuccessfully(true)
            .build();

        pipeline.run(project);

        expect(log.loggedLines()).toEqual([
                'INFO: No tests',
                'INFO: Deployment successful',
                'INFO: Email disabled'
            ]
        );
        expect(emailer.send).toHaveBeenCalledTimes(0);
    });

    test('project with tests that fail with email notification', () => {
        sendEmailSummary.mockReturnValue(true);

        const project = new ProjectBuilder()
            .setTestStatus(TestStatus.FailingTests)
            .build();

        pipeline.run(project);

        expect(log.loggedLines()).toEqual([
                'ERROR: Tests failed',
                'INFO: Sending email'
            ]
        );
        expect(emailer.send).toHaveBeenCalledWith('Tests failed');
    });

    test('project with tests that fail without email notification', () => {
        sendEmailSummary.mockReturnValue(false);

        const project = new ProjectBuilder()
            .setTestStatus(TestStatus.FailingTests)
            .build();

        pipeline.run(project);

        expect(log.loggedLines()).toEqual([
                'ERROR: Tests failed',
                'INFO: Email disabled'
            ]
        );
        expect(emailer.send).toHaveBeenCalledTimes(0);
    });

    test('project with tests and failing build with email notification', () => {
        sendEmailSummary.mockReturnValue(true);

        const project = new ProjectBuilder()
            .setTestStatus(TestStatus.PassingTests)
            .setDeploysSuccessfully(false)
            .build();

        pipeline.run(project);

        expect(log.loggedLines()).toEqual([
                'INFO: Tests passed',
                'ERROR: Deployment failed',
                'INFO: Sending email'
            ]
        );
        expect(emailer.send).toHaveBeenCalledWith('Deployment failed');
    });

    test('project with tests and failing build without email notification', () => {
        sendEmailSummary.mockReturnValue(false);

        const project = new ProjectBuilder()
            .setTestStatus(TestStatus.PassingTests)
            .setDeploysSuccessfully(false)
            .build();

        pipeline.run(project);

        expect(log.loggedLines()).toEqual([
                'INFO: Tests passed',
                'ERROR: Deployment failed',
                'INFO: Email disabled'
            ]
        );
        expect(emailer.send).toHaveBeenCalledTimes(0);
    });

    test('project without tests and failing build with email notification', () => {
        sendEmailSummary.mockReturnValue(true);

        const project = new ProjectBuilder()
            .setTestStatus(TestStatus.NoTests)
            .setDeploysSuccessfully(false)
            .build();

        pipeline.run(project);

        expect(log.loggedLines()).toEqual([
                'INFO: No tests',
                'ERROR: Deployment failed',
                'INFO: Sending email'
            ]
        );
        expect(emailer.send).toHaveBeenCalledWith('Deployment failed');
    });

    test('project without tests and failing build without email notification', () => {
        sendEmailSummary.mockReturnValue(false);

        const project = new ProjectBuilder()
            .setTestStatus(TestStatus.NoTests)
            .setDeploysSuccessfully(false)
            .build();

        pipeline.run(project);

        expect(log.loggedLines()).toEqual([
                'INFO: No tests',
                'ERROR: Deployment failed',
                'INFO: Email disabled'
            ]
        );
        expect(emailer.send).toHaveBeenCalledTimes(0);
    });
});
