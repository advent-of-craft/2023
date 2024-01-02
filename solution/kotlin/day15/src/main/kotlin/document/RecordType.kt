package document

enum class RecordType(val value: String) {
    INDIVIDUAL_PROSPECT("IndividualPersonProspect"),
    LEGAL_PROSPECT("LegalEntityProspect"),
    ALL("All")
}