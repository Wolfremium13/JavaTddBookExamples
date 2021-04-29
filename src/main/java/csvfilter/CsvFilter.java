package csvfilter;

import java.util.ArrayList;
import java.util.List;


public class CsvFilter {
    public List<String> apply(List<String> lines) {
        ArrayList<String> result = new ArrayList<>();
        result.add(lines.get(0));
        final String invoice = lines.get(1);
        final List<String> fields = List.of(invoice.split(","));
        final int ivaFieldIndex = 4;
        final int igicFieldIndex = 5;
        final String ivaField = fields.get(ivaFieldIndex);
        final String igicField = fields.get(igicFieldIndex);
        final String decimalRegex = "\\d+(\\.\\d+)?";
        final boolean taxFieldsAreMutuallyExclusive =
                ((ivaField.matches(decimalRegex) || igicField.matches(decimalRegex)) &&
                        (ivaField.isBlank() || igicField.isBlank()));
        if (taxFieldsAreMutuallyExclusive) {
            result.add(lines.get(1));
        }
        return result;
    }
}