package sang.se.bookingmovie.app.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;

public enum CommentStatus {
    APPROVED("Duyệt"),
    PENDING("Chưa duyệt"),
    DELETED("Đã xóa");

    private final String value;

    CommentStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
