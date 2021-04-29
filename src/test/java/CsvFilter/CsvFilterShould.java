package CsvFilter;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CsvFilterShould {
    final String headerLine = "Num_factura, Fecha, Bruto, Neto, IVA, IGIC, Concepto, CIF_cliente, NIF_cliente";
    @Test
    public void allow_for_correct_lines_only() {
        final String invoiceLine = "1,02/05/2019,100,810,19,,ACER Laptop,B76430134,";

        List<String> result = new CsvFilter().filter(List.of(headerLine, invoiceLine));

        assertThat(result).isEqualTo(List.of(headerLine, invoiceLine));
    }
    @Test
    public void exclude_lines_with_both_tax_fields_populated_as_they_are_exclusive() {
        final String invoiceLine = "1,02/05/2019,100,810,19,8,ACER Laptop,B76430134,";

        List<String> result = new CsvFilter().filter(List.of(headerLine, invoiceLine));

        assertThat(result).isEqualTo(List.of(headerLine));
    }
    @Test
    public void exclude_lines_with_both_tax_fields_empty_as_one_is_required() {
        final String invoiceLine = "1,02/05/2019,1000,810,,,ACER Laptop,B76430134,";

        List<String> result = new CsvFilter().filter(List.of(headerLine, invoiceLine));

        assertThat(result).isEqualTo(List.of(headerLine));
    }

    @Test
    public void exclude_lines_with_non_decimal_tax_fields() {
        final String invoiceLine = "1,02/05/2019,1000,810,XYZ,,ACER Laptop,B76430134,";

        List<String> result = new CsvFilter().filter(List.of(headerLine, invoiceLine));

        assertThat(result).isEqualTo(List.of(headerLine));
    }

    @Test
    public void exclude_lines_with_both_tax_fields_populated_even_if_non_decimal() {
        final String invoiceLine = "1,02/05/2019,1000,810,XYZ,12,ACER Laptop,B76430134,";

        List<String> result = new CsvFilter().filter(List.of(headerLine, invoiceLine));

        assertThat(result).isEqualTo(List.of(headerLine));
    }
}