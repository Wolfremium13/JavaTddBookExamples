package CsvFilter;

import java.util.List;

class FileWithOneInvoiceLineBuilder {
    private final String emptyField = "";
    private String headerLine;
    private String ivaTax = "19";
    private String igicTax = emptyField;
    private String concept = "irrelevant";

    public FileWithOneInvoiceLineBuilder withHeader(String headerLine) {
        this.headerLine = headerLine;
        return this;
    }


    public FileWithOneInvoiceLineBuilder withIvaTax(String ivaTax) {
        this.ivaTax = ivaTax;
        return this;
    }

    public FileWithOneInvoiceLineBuilder withIgicTax(String igicTax) {
        this.igicTax = igicTax;
        return this;
    }

    public FileWithOneInvoiceLineBuilder withConcept(String concept) {
        this.concept = concept;
        return this;
    }

    public List<String> generateLines() {
        final String invoiceId = "1";
        final String invoiceDate = "02/05/2019";
        final String grossAmount = "1000";
        final String netAmount = "810";
        final String cif = "B76430134";
        final String nif = emptyField;
        var formattedLine = String.join(",",
                List.of(
                        invoiceId,
                        invoiceDate,
                        grossAmount,
                        netAmount,
                        this.ivaTax,
                        this.igicTax,
                        this.concept,
                        cif,
                        nif
                ));
        return List.of(headerLine, formattedLine);
    }
}
