package com.tdd.leanmind.test;

import csvfilter.CsvFilter;
import csvfilter.FileWithOneInvoiceLineBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CsvFilterShould {
    private final String headerLine = "Num_factura, Fecha, Bruto, Neto, IVA, IGIC, Concepto, CIF_cliente, NIF_cliente";
    CsvFilter filter;
    FileWithOneInvoiceLineBuilder builder;
    private final List<String> emptyDataFile = List.of(headerLine);
    private final String emptyField = "";

    @BeforeEach
    public void Setup() {
        filter = new CsvFilter();
        builder = new FileWithOneInvoiceLineBuilder();
    }

    @Test
    public void allow_for_correct_lines_only() {
        List<String> lines = builder
                .withHeader(headerLine)
                .withConcept("a correct line with irrelevant data")
                .generateLines();

        List<String> result = filter.apply(lines);
        assertThat(result).isEqualTo(lines);
    }

    @Test
    public void exclude_lines_with_both_tax_fields_populated_as_they_are_exclusive() {
        List<String> result = filter.apply(builder
                .withHeader(headerLine)
                .withIvaTax("19")
                .withIgicTax("8")
                .generateLines()
        );

        assertThat(result).isEqualTo(emptyDataFile);
    }

    @Test
    public void exclude_lines_with_both_tax_fields_empty_as_one_is_required() {
        List<String> result = filter.apply(builder
                .withHeader(headerLine)
                .withIvaTax(emptyField)
                .withIgicTax(emptyField)
                .generateLines()
        );

        assertThat(result).isEqualTo(emptyDataFile);
    }

    @Test
    public void exclude_lines_with_non_decimal_tax_fields() {
        List<String> result = filter.apply(builder
                .withHeader(headerLine)
                .withIvaTax("XYZ")
                .withIgicTax(emptyField)
                .generateLines()
        );

        assertThat(result).isEqualTo(emptyDataFile);
    }

    @Test
    public void exclude_lines_with_both_tax_fields_populated_even_if_non_decimal() {

        List<String> result = filter.apply(builder
                .withHeader(headerLine)
                .withIvaTax("XYZ")
                .withIgicTax("12")
                .generateLines()
        );

        assertThat(result).isEqualTo(emptyDataFile);
    }
}