import {Config, Emailer, Logger} from "./ci";
import {Project} from "./project";
import * as O from 'fp-ts/Option';
import {Option, some} from 'fp-ts/Option';
import {pipe} from "fp-ts/function";

const success = 'success';

export class Pipeline {
    constructor(private config: Config, private emailSender: Emailer, private log: Logger) {
    }

    run(project: Project): void {
        pipe(
            this.createProjectContext(project),
            O.chain(context => this.runTests(context)),
            O.chain(context => this.deploy(context))
        );
    }

    private createProjectContext(project: Project): Option<PipelineContext> {
        if (!project.hasTests()) {
            this.log.info('No tests');
        }
        return some(new PipelineContext(project));
    }

    private runTests = (context: PipelineContext): Option<PipelineContext> =>
        context.hasTests
            ? this.runTestsSafely(context)
            : some(context);

    private runTestsSafely = (context: PipelineContext): Option<PipelineContext> => some(
        this.logTestResult(
            context.withTestsResults(context.project.runTests() === success)
        )
    );

    private logTestResult(context: PipelineContext): PipelineContext {
        if (!context.testsRanSuccessfully) {
            this.log.error('Tests failed');
            this.sendEmail('Tests failed');
        } else {
            this.log.info('Tests passed');
        }
        return context;
    }

    private deploy = (context: PipelineContext): Option<PipelineContext> =>
        context.mustRunDeployment()
            ? this.deploySafely(context)
            : some(context);

    private deploySafely = (context: PipelineContext): Option<PipelineContext> => some(
        this.logDeploymentResult(
            context.withDeploymentStatus(context.project.deploy() === success)
        )
    );

    private logDeploymentResult(context: PipelineContext): PipelineContext {
        if (!context.deployedSuccessfully) {
            this.log.error('Deployment failed');
            this.sendEmail('Deployment failed');
        } else {
            this.log.info('Deployment successful');
            this.sendEmail('Deployment completed successfully');
        }
        return context;
    }

    private sendEmail(text: string): void {
        if (!this.config.sendEmailSummary()) {
            this.log.info('Email disabled');
            return;
        }
        this.log.info('Sending email');
        this.emailSender.send(text);
    }
}

class PipelineContext {
    public hasTests = this.project.hasTests();
    public testsRanSuccessfully = false;
    public deployedSuccessfully = false;

    constructor(public readonly project: Project) {
    }

    mustRunDeployment(): boolean {
        return this.testsRanSuccessfully || !this.hasTests;
    }

    withTestsResults(testsResult: boolean): PipelineContext {
        this.testsRanSuccessfully = testsResult
        return this
    }

    withDeploymentStatus(deploymentStatus: boolean): PipelineContext {
        this.deployedSuccessfully = deploymentStatus
        return this
    }
}