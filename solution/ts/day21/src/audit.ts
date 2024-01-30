export type FileContent = { fileName: string, lines: string[] };
export type FileUpdate = { fileName: string, newContent: string };

export class AuditManager {
    constructor(private readonly maxEntriesPerFile: number) {
    }

    public addRecord(files: FileContent[], visitorName: string, timeOfVisit: Date): FileUpdate {
        const sortedFiles = files.sort((a, b) => a.fileName.localeCompare(b.fileName));
        const newRecord = `${visitorName};${timeOfVisit.toISOString()}`;

        return sortedFiles.length === 0
            ? this.createANewFile(sortedFiles, newRecord)
            : this.createNewFileOrUpdate(sortedFiles, newRecord);
    }

    private createANewFile = (sortedFiles: FileContent[], newRecord: string): FileUpdate => ({
        fileName: `audit_${sortedFiles.length + 1}.txt`,
        newContent: newRecord
    });

    private createNewFileOrUpdate(sortedFiles: FileContent[], newRecord: string): FileUpdate {
        const currentFile = sortedFiles[sortedFiles.length - 1];

        return this.canAppendToExistingFile(currentFile)
            ? this.appendToExistingFile(newRecord, currentFile)
            : this.createANewFile(sortedFiles, newRecord);
    }

    private appendToExistingFile = (newRecord: string, currentFile: FileContent): FileUpdate => ({
        fileName: currentFile.fileName,
        newContent: [...currentFile.lines, newRecord].join('\n')
    });

    private canAppendToExistingFile = (currentFile: FileContent): boolean =>
        currentFile.lines.length < this.maxEntriesPerFile;
}