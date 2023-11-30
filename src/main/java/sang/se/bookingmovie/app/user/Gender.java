package sang.se.bookingmovie.app.user;

import lombok.Getter;

@Getter
public enum Gender {
    MALE("Nam"),
    FEMALE("Nữ"),
    UNKNOWN("Khác");

    private final String value;

    Gender(String value) {
        this.value = value;
    }

}
