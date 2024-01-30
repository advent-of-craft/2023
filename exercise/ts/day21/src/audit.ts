import * as path from 'path';

export interface FileSystem {
    getFiles(directoryName: string): Promise<string[]>;

    writeAllText(filePath: string, content: string): Promise<void>;

    readAllLines(filePath: string): Promise<string[]>;
}

export class AuditManager {
    constructor(private readonly maxEntriesPerFile: number,
                private readonly directoryName: string,
                private readonly fileSystem: FileSystem) {
    }

    async addRecord(visitorName: string, timeOfVisit: Date): Promise<void> {
        const filePaths = await this.fileSystem.getFiles(this.directoryName);
        const sorted = filePaths.sort(); // Assuming filePaths are sortable as in the Java example
        const newRecord = `${visitorName};${timeOfVisit.toISOString()}`;

        if (sorted.length === 0) {
            const newFile = path.join(this.directoryName, 'audit_1.txt');
            await this.fileSystem.writeAllText(newFile, newRecord);
            return;
        }

        const currentFileIndex = sorted.length - 1;
        const currentFilePath = sorted[currentFileIndex];
        const lines = await this.fileSystem.readAllLines(currentFilePath);

        if (lines.length < this.maxEntriesPerFile) {
            lines.push(newRecord);
            const newContent = lines.join('\n');
            await this.fileSystem.writeAllText(currentFilePath, newContent);
        } else {
            const newName = `audit_${currentFileIndex + 2}.txt`;
            const newFile = path.join(this.directoryName, newName);
            await this.fileSystem.writeAllText(newFile, newRecord);
        }
    }
}