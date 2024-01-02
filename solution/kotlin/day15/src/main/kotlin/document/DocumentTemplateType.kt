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
        private val mapping: Map<String, DocumentTemplateType> = entries
            .associateBy { entry -> formatKey(entry.documentType, entry.recordType.name) }
            .plus(RecordType.entries.map { entry -> formatKey(SPEC.name, entry.name) to SPEC })

        private fun formatKey(documentType: String, recordType: String) =
            "${documentType.uppercase()}-${recordType.uppercase()}"

        fun fromDocumentTypeAndRecordType(documentType: String, recordType: String): DocumentTemplateType =
            mapping.getOrElse(
                formatKey(documentType, recordType)
            ) { throw IllegalArgumentException("Invalid Document template type or record type") }
    }
}