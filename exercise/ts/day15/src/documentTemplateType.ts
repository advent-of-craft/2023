import {RecordType} from "./recordType";

export class DocumentTemplateType {
    private static readonly values: Map<string, DocumentTemplateType> = new Map([
        ['DEERPP', new DocumentTemplateType('DEER', RecordType.INDIVIDUAL_PROSPECT)],
        ['DEERPM', new DocumentTemplateType('DEER', RecordType.LEGAL_PROSPECT)],
        ['AUTP', new DocumentTemplateType('AUTP', RecordType.INDIVIDUAL_PROSPECT)],
        ['AUTM', new DocumentTemplateType('AUTM', RecordType.LEGAL_PROSPECT)],
        ['SPEC', new DocumentTemplateType('SPEC', RecordType.ALL)],
        ['GLPP', new DocumentTemplateType('GLPP', RecordType.INDIVIDUAL_PROSPECT)],
        ['GLPM', new DocumentTemplateType('GLPM', RecordType.LEGAL_PROSPECT)]
    ]);

    private readonly documentType: string;
    private readonly recordType: RecordType;

    private constructor(documentType: string, recordType: RecordType) {
        this.documentType = documentType;
        this.recordType = recordType;
    }

    public static fromDocumentTypeAndRecordType(documentType: string, recordType: string): DocumentTemplateType {
        for (const [_, dtt] of DocumentTemplateType.values) {
            if (dtt.documentType.toLowerCase() === documentType.toLowerCase() &&
                dtt.recordType === RecordType[recordType as keyof RecordType]) {
                return dtt;
            } else if (dtt.documentType.toLowerCase() === documentType.toLowerCase() &&
                dtt.recordType === RecordType.ALL) {
                return dtt;
            }
        }
        throw new Error("Invalid Document template type or record type");
    }

    static getValues = () => DocumentTemplateType.values;
    getRecordType = (): RecordType => this.recordType;
    getDocumentType = (): string => this.documentType;
}