import {Logger} from "../src/ci";

export class CapturingLogger implements Logger {
    private lines: string[] = [];

    info(message: string): void {
        this.lines.push(`INFO: ${message}`)
    }

    error(message: string): void {
        this.lines.push(`ERROR: ${message}`)
    }

    loggedLines = () => this.lines;
}