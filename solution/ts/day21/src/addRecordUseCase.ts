import {AuditManager} from './audit';
import {Persister} from "./persister";

export class AddRecordUseCase {
    constructor(private readonly directory: string,
                private readonly auditManager: AuditManager,
                private readonly persister: Persister) {
    }

    public async handle(visitor: string, time: Date): Promise<void> {
        const files = await this.persister.readDirectory(this.directory);
        const update = this.auditManager.addRecord(files, visitor, time);

        await this.persister.applyUpdate(this.directory, update);
    }
}
