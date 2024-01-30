import {RecordType} from "./recordType";

export class DocumentTemplateType {
    constructor(readonly documentType: string, readonly recordType: RecordType) {
        this.documentType = documentType;
        this.recordType = recordType;
    }
}

export class DocumentTemplates {
    public static SPEC: DocumentTemplateType = new DocumentTemplateType('SPEC', RecordType.ALL);
    public static values: Map<string, DocumentTemplateType> = new Map([
        ['DEERPP', new DocumentTemplateType('DEER', RecordType.INDIVIDUAL_PROSPECT)],
        ['DEERPM', new DocumentTemplateType('DEER', RecordType.LEGAL_PROSPECT)],
        ['AUTP', new DocumentTemplateType('AUTP', RecordType.INDIVIDUAL_PROSPECT)],
        ['AUTM', new DocumentTemplateType('AUTM', RecordType.LEGAL_PROSPECT)],
        ['SPEC', DocumentTemplates.SPEC],
        ['GLPP', new DocumentTemplateType('GLPP', RecordType.INDIVIDUAL_PROSPECT)],
        ['GLPM', new DocumentTemplateType('GLPM', RecordType.LEGAL_PROSPECT)]
    ]);

    public static fromDocumentTypeAndRecordType(documentType: string, recordType: string): DocumentTemplateType {
        let recordName = RecordType[recordType as keyof RecordType];
        let result = this.mapping()[this.formatKey(documentType, recordName)];

        if (!result) throw new Error("Invalid Document template type or record type");

        return result;
    }

    private static mapping() {
        let mapping: Map<string, DocumentTemplateType> = new Map();

        DocumentTemplates.values.forEach((dtt) => mapping[this.formatKey(dtt.documentType, dtt.recordType)] = dtt);
        Object.values(RecordType).filter((item) => isNaN(Number(item)))
            .forEach(recordType =>
                mapping[this.formatKey('SPEC', recordType)] = DocumentTemplates.SPEC
            );

        return mapping;
    }

    private static formatKey = (documentType: string, recordType: string): string =>
        `${documentType.toUpperCase()}-${recordType.toUpperCase()}`;
}