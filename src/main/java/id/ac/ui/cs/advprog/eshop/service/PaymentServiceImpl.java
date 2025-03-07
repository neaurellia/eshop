package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        String paymentId = UUID.randomUUID().toString();
        String status = determinePaymentStatus(method, paymentData);

        Payment payment = new Payment(paymentId, method, status, paymentData);
        paymentRepository.save(payment);
        return payment;
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        if (!PaymentStatus.isValid(status)) {
            throw new IllegalArgumentException("Invalid payment status.");
        }
        payment.setStatus(status);

        // Update related order status
        if (status.equals(PaymentStatus.SUCCESS.getValue())) {
            payment.getOrder().setStatus("SUCCESS");
        } else if (status.equals(PaymentStatus.REJECTED.getValue())) {
            payment.getOrder().setStatus("FAILED");
        }

        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPayment(String paymentId) {
        Payment payment = paymentRepository.findById(paymentId);
        if (payment == null) {
            throw new NoSuchElementException("Payment not found.");
        }
        return payment;
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    private String determinePaymentStatus(String method, Map<String, String> paymentData) {
        if ("VOUCHER".equalsIgnoreCase(method)) {
            return validateVoucher(paymentData.get("voucherCode")) ?
                    PaymentStatus.SUCCESS.getValue() :
                    PaymentStatus.REJECTED.getValue();
        } else if ("COD".equalsIgnoreCase(method)) {
            return validateCOD(paymentData) ?
                    PaymentStatus.SUCCESS.getValue() :
                    PaymentStatus.REJECTED.getValue();
        }
        return PaymentStatus.REJECTED.getValue();
    }

    private boolean validateVoucher(String voucherCode) {
        if (voucherCode == null || voucherCode.length() != 16) return false;
        if (!voucherCode.startsWith("ESHOP")) return false;

        String numbers = voucherCode.substring(5);
        long digitCount = numbers.chars().filter(Character::isDigit).count();
        return digitCount == 8;
    }

    private boolean validateCOD(Map<String, String> paymentData) {
        if (paymentData == null) return false;
        String address = paymentData.get("address");
        String deliveryFee = paymentData.get("deliveryFee");
        return address != null && !address.isBlank() &&
                deliveryFee != null && !deliveryFee.isBlank();
    }

    @Override
    public Payment createPayment(Payment payment) {
        return null;
    }

    @Override
    public Payment updateStatus(String paymentId, String status) {
        return null;
    }

    @Override
    public Payment findById(String paymentId) {
        return null;
    }

    @Override
    public List<Payment> findAllByOrderId(String orderId) {
        return List.of();
    }
}
