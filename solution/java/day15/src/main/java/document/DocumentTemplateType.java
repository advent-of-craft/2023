package document;

import io.vavr.collection.List;
import io.vavr.collection.Map;

public enum DocumentTemplateType {
    DEERPP("DEER", RecordType.INDIVIDUAL_PROSPECT),
    DEERPM("DEER", RecordType.LEGAL_PROSPECT),
    AUTP("AUTP", RecordType.INDIVIDUAL_PROSPECT),
    AUTM("AUTM", RecordType.LEGAL_PROSPECT),
    SPEC("SPEC", RecordType.ALL),
    GLPP("GLPP", RecordType.INDIVIDUAL_PROSPECT),
    GLPM("GLPM", RecordType.LEGAL_PROSPECT);

    private static final Map<String, DocumentTemplateType> mapping =
            List.of(DocumentTemplateType.values())
                    .toMap(v -> formatKey(v.getDocumentType(), v.getRecordType().name()), v -> v)
                    .merge(List.of(RecordType.values()).toMap(v -> formatKey(SPEC.name(), v.name()), v -> SPEC));

    private final String documentType;
    private final RecordType recordType;

    DocumentTemplateType(String documentType, RecordType recordType) {
        this.documentType = documentType;
        this.recordType = recordType;
    }

    private static String formatKey(String documentType, String recordType) {
        return documentType.toUpperCase() + "-" + recordType.toUpperCase();
    }

    public static DocumentTemplateType fromDocumentTypeAndRecordType(String documentType, String recordType) {
        return mapping.get(formatKey(documentType, recordType))
                .getOrElseThrow(() -> new IllegalArgumentException("Invalid Document template type or record type"));
    }

    private RecordType getRecordType() {
        return recordType;
    }

    private String getDocumentType() {
        return documentType;
    }
}