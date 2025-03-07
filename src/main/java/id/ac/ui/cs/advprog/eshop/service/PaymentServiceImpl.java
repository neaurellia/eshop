package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment createPayment(Payment payment) {
        if (paymentRepository.findById(payment.getId()) != null) {
            throw new IllegalArgumentException("Payment with this ID already exists.");
        }
        return paymentRepository.save(payment);
    }

    @Override
    public Payment updateStatus(String paymentId, String status) {
        Payment payment = paymentRepository.findById(paymentId);
        if (payment == null) {
            throw new NoSuchElementException("Payment not found.");
        }
        if (!PaymentStatus.isValid(status)) {
            throw new IllegalArgumentException("Invalid payment status.");
        }
        payment.setStatus(status);
        return paymentRepository.save(payment);
    }

    @Override
    public Payment findById(String paymentId) {
        Payment payment = paymentRepository.findById(paymentId);
        if (payment == null) {
            throw new NoSuchElementException("Payment not found.");
        }
        return payment;
    }

    @Override
    public List<Payment> findAllByOrderId(String orderId) {
        return paymentRepository.findAllByOrderId(orderId);
    }
}
