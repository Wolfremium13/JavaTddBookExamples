package CsvFilter;

import java.util.ArrayList;
import java.util.List;


public class CsvFilter {
    public List<String> filter(List<String> lines) {
        ArrayList<String> result = new ArrayList<>();
        result.add(lines.get(0));
        final String invoice = lines.get(1);
        final List<String> fields = List.of(invoice.split(","));
        final int ivaFieldIndex = 4;
        final int igicFieldIndex = 5;
        final boolean taxFieldsAreMutuallyExclusive =
                (fields.get(ivaFieldIndex).isBlank() ||
                        fields.get(igicFieldIndex).isBlank()) &&
                        (!(fields.get(ivaFieldIndex).isBlank() && fields.get(igicFieldIndex).isBlank()));
        if (taxFieldsAreMutuallyExclusive) {
            result.add(lines.get(1));
        }
        return result;
    }
}