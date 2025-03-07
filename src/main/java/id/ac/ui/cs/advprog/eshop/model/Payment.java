package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Builder
public class Payment {
    private String id;
    private String method;
    private Map<String, String> paymentData;

    @Setter
    private PaymentStatus status;

    public Payment(String id, String method, Map<String, String> paymentData) {
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
        this.status = PaymentStatus.PENDING; // Default status
    }

    public void setStatus(String status) {
        if (PaymentStatus.contains(status)) {
            this.status = PaymentStatus.valueOf(status.toUpperCase());
        } else {
            throw new IllegalArgumentException("Invalid payment status");
        }
    }
}
