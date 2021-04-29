package CsvFilter;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CsvFilterShould {
    @Test
    public void allow_for_correct_lines_only() {
        final String headerLine = "Num_factura, Fecha, Bruto, Neto, IVA, IGIC, Concepto, CIF_cliente, NIF_cliente";
        final String invoiceLine = "1,02/05/2019,100,810,19,,ACER Laptop,B76430134,";

        List<String> result = new CsvFilter().filter(List.of(headerLine, invoiceLine));

        assertThat(result).isEqualTo(List.of(headerLine, invoiceLine));
    }
    @Test
    public void exclude_lines_with_both_tax_fields_populated_as_they_are_exclusive() {
        final String headerLine = "Num_factura, Fecha, Bruto, Neto, IVA, IGIC, Concepto, CIF_cliente, NIF_cliente";
        final String invoiceLine = "1,02/05/2019,100,810,19,8,ACER Laptop,B76430134,";

        List<String> result = new CsvFilter().filter(List.of(headerLine, invoiceLine));

        assertThat(result).isEqualTo(List.of(headerLine));
    }
}