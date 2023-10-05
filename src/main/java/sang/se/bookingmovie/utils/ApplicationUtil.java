package sang.se.bookingmovie.utils;

import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.regex.Pattern;

@Service
public class ApplicationUtil {

    public String toSlug(String input) {
        if (input == null) {
            return "";
        }
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String slug = pattern.matcher(normalized).replaceAll("");
        slug = slug.toLowerCase().replaceAll(" ", "-");
        slug = slug.replaceAll("[^a-z0-9\\-]", "");

        return slug;
    }

    public String addZeros(long number, int desiredLength) {
        return String.format("%0" + desiredLength + "d", number);
    }
}
