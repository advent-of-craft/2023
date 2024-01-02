package document

import document.RecordType.*

enum class DocumentTemplateType(val documentType: String, val recordType: RecordType) {
    DEERPP("DEER", INDIVIDUAL_PROSPECT),
    DEERPM("DEER", LEGAL_PROSPECT),
    AUTP("AUTP", INDIVIDUAL_PROSPECT),
    AUTM("AUTM", LEGAL_PROSPECT),
    SPEC("SPEC", ALL),
    GLPP("GLPP", INDIVIDUAL_PROSPECT),
    GLPM("GLPM", LEGAL_PROSPECT);

    companion object {
        fun fromDocumentTypeAndRecordType(documentType: String, recordType: String): DocumentTemplateType {
            for (dtt in entries) {
                if (dtt.documentType.equals(documentType, ignoreCase = true)
                    && dtt.recordType == RecordType.valueOf(recordType)
                ) {
                    return dtt
                } else if (dtt.documentType.equals(
                        documentType,
                        ignoreCase = true
                    ) && dtt.recordType == ALL
                ) {
                    return dtt
                }
            }
            throw IllegalArgumentException("Invalid Document template type or record type")
        }
    }
}