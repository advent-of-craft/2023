package document;

import static document.RecordType.ALL;

public enum DocumentTemplateType {
    DEERPP("DEER", RecordType.INDIVIDUAL_PROSPECT),
    DEERPM("DEER", RecordType.LEGAL_PROSPECT),
    AUTP("AUTP", RecordType.INDIVIDUAL_PROSPECT),
    AUTM("AUTM", RecordType.LEGAL_PROSPECT),
    SPEC("SPEC", RecordType.ALL),
    GLPP("GLPP", RecordType.INDIVIDUAL_PROSPECT),
    GLPM("GLPM", RecordType.LEGAL_PROSPECT);

    private final String documentType;
    private final RecordType recordType;

    DocumentTemplateType(String documentType, RecordType recordType) {
        this.documentType = documentType;
        this.recordType = recordType;
    }

    public static DocumentTemplateType fromDocumentTypeAndRecordType(String documentType, String recordType) {
        for (DocumentTemplateType dtt : DocumentTemplateType.values()) {
            if (dtt.getDocumentType().equalsIgnoreCase(documentType)
                    && dtt.getRecordType().equals(RecordType.valueOf(recordType))) {
                return dtt;
            } else if (dtt.getDocumentType().equalsIgnoreCase(documentType)
                    && dtt.getRecordType().equals(ALL)) {
                return dtt;
            }
        }
        throw new IllegalArgumentException("Invalid Document template type or record type");
    }

    private RecordType getRecordType() {
        return recordType;
    }

    private String getDocumentType() {
        return documentType;
    }
}