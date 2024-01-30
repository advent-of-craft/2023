import {AuditManager, FileContent, FileUpdate} from '../src/audit';

describe('audit should add new visitor to', () => {
    const NO_FILES: FileContent[] = [];
    const TIME_OF_VISIT = new Date('2019-04-06T18:00:00Z');
    const NEW_CONTENT = 'Alice;2019-04-06T18:00:00.000Z';
    const VISITOR = 'Alice';
    const NO_CONTENT: string[] = [];

    let auditManager: AuditManager;

    beforeEach(() => auditManager = new AuditManager(3));

    test('a new file because no file today', () =>
        expect(addRecord(NO_FILES)).toEqual({
            fileName: 'audit_1.txt',
            newContent: NEW_CONTENT
        }));

    test('an existing file', () => {
        const files = [
            {fileName: 'audit_1.txt', lines: NO_CONTENT},
            {fileName: 'audit_2.txt', lines: ['Peter;2019-04-06 16:30:00']}
        ];

        expect(addRecord(files))
            .toEqual({fileName: 'audit_2.txt', newContent: 'Peter;2019-04-06 16:30:00\n' + NEW_CONTENT});
    });

    test('a new file when end of last file is reached', () => {
        const files = [
            {fileName: 'audit_1.txt', lines: NO_CONTENT},
            {
                fileName: 'audit_2.txt', lines: [
                    'Peter;2019-04-06 16:30:00',
                    'Jane;2019-04-06 16:40:00',
                    'Jack;2019-04-06 17:00:00',
                ]
            }
        ];

        expect(addRecord(files))
            .toEqual({fileName: 'audit_3.txt', newContent: NEW_CONTENT});
    });

    const addRecord = (files: FileContent[]): FileUpdate =>
        auditManager.addRecord(files, VISITOR, TIME_OF_VISIT);
});
