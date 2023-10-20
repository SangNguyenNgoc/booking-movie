package sang.se.bookingmovie.utils;

import org.springframework.stereotype.Service;
import sang.se.bookingmovie.exception.CreateUUIDException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class ApplicationUtil {

    public LocalDateTime getDateNow() {
        return LocalDateTime.now();
    }

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

    public String createUUID(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            UUID uuid = UUID.nameUUIDFromBytes(hash);

            return uuid.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new CreateUUIDException("Server error", List.of("Create ID failure"));
        }
    }

    public String generateVerificationCode(int length) {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10);
            code.append(digit);
        }
        return code.toString();
    }
}
