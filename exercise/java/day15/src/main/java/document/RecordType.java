package document;

public enum RecordType {
    INDIVIDUAL_PROSPECT("IndividualPersonProspect"),
    LEGAL_PROSPECT("LegalEntityProspect"),
    ALL("All");

    private final String value;

    RecordType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}