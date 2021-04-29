package CsvFilter;

import java.util.ArrayList;
import java.util.List;


public class CsvFilter {
    public List<String> filter(List<String> lines) {
        ArrayList<String> result = new ArrayList<>();
        result.add(lines.get(0));
        final String invoice = lines.get(1);
        final List<String> fields = List.of(invoice.split(","));
        if ((fields.get(4).isBlank() || fields.get(5).isBlank()) &&
                (!(fields.get(4).isBlank() && fields.get(5).isBlank()))) {
            result.add(lines.get(1));
        }
        return result;
    }
}