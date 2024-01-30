export interface Config {
    sendEmailSummary(): boolean;
}

export interface Emailer {
    send(message: string): void;
}

export interface Logger {
    info(message: string): void;

    error(message: string): void;
}

export enum TestStatus {
    NoTests,
    PassingTests,
    FailingTests,
}