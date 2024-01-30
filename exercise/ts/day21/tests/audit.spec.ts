import {AuditManager, FileSystem} from '../src/audit';

describe('audit manager', () => {
    it('adds new visitor to a new file when end of last file is reached', async () => {
        const fileSystem: FileSystem = {
            getFiles: jest.fn(),
            writeAllText: jest.fn(),
            readAllLines: jest.fn()
        };

        (fileSystem.getFiles as jest.Mock).mockResolvedValue([
            'audits/audit_2.txt',
            'audits/audit_1.txt'
        ]);

        (fileSystem.readAllLines as jest.Mock).mockResolvedValue([
            'Peter;2019-04-06 16:30:00',
            'Jane;2019-04-06 16:40:00',
            'Jack;2019-04-06 17:00:00'
        ]);

        const auditManager = new AuditManager(3, 'audits', fileSystem);

        await auditManager.addRecord('Alice', new Date('2019-04-06T18:00:00.000Z'));

        expect(fileSystem.writeAllText)
            .toHaveBeenCalledWith(
                'audits/audit_3.txt',
                'Alice;2019-04-06T18:00:00.000Z'
            );
    });
});
