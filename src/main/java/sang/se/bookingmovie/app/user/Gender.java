package sang.se.bookingmovie.app.user;

public enum Gender {
    MALE("Nam"),
    FEMALE("Nữ"),
    UNKNOWN("Không xác định");

    private final String value;

    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
