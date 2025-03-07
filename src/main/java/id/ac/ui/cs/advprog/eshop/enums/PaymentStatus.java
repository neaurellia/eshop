package id.ac.ui.cs.advprog.eshop.enums;

public enum PaymentStatus {
    PENDING,
    SUCCESS,
    REJECTED;

    public static boolean contains(String param) {
        for (PaymentStatus status : PaymentStatus.values()) {
            if (status.name().equalsIgnoreCase(param)) {
                return true;
            }
        }
        return false;
    }
}
