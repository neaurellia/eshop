package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {
    PaymentRepository paymentRepository;
    List<Payment> payments;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP123456789012");

        payments = new ArrayList<>();
        Payment payment1 = new Payment("pay123", "order123", "VOUCHER", PaymentStatus.PENDING.getValue(), paymentData);
        payments.add(payment1);
        Payment payment2 = new Payment("pay456", "order123", "CREDIT_CARD", PaymentStatus.SUCCESS.getValue(), paymentData);
        payments.add(payment2);
        Payment payment3 = new Payment("pay789", "order456", "BANK_TRANSFER", PaymentStatus.FAILED.getValue(), paymentData);
        payments.add(payment3);
    }

    @Test
    void testSaveCreate() {
        Payment payment = payments.get(1);
        Payment result = paymentRepository.save(payment);
        Payment findResult = paymentRepository.findById(payments.get(1).getId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), findResult.getId());
        assertEquals(payment.getOrderId(), findResult.getOrderId());
        assertEquals(payment.getPaymentMethod(), findResult.getPaymentMethod());
        assertEquals(payment.getStatus(), findResult.getStatus());
    }

    @Test
    void testSaveUpdate() {
        Payment payment = payments.get(1);
        paymentRepository.save(payment);
        Payment updatedPayment = new Payment(payment.getId(), payment.getOrderId(), payment.getPaymentMethod(), PaymentStatus.SUCCESS.getValue(), payment.getPaymentData());
        Payment result = paymentRepository.save(updatedPayment);

        Payment findResult = paymentRepository.findById(payments.get(1).getId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), findResult.getId());
        assertEquals(payment.getOrderId(), findResult.getOrderId());
        assertEquals(payment.getPaymentMethod(), findResult.getPaymentMethod());
        assertEquals(PaymentStatus.SUCCESS.getValue(), findResult.getStatus());
    }

    @Test
    void testFindByIdIfIdFound() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }
        Payment findResult = paymentRepository.findById(payments.get(1).getId());
        assertEquals(payments.get(1).getId(), findResult.getId());
        assertEquals(payments.get(1).getOrderId(), findResult.getOrderId());
        assertEquals(payments.get(1).getPaymentMethod(), findResult.getPaymentMethod());
        assertEquals(payments.get(1).getStatus(), findResult.getStatus());
    }

    @Test
    void testFindByIdIfIdNotFound() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }
        Payment findResult = paymentRepository.findById("nonexistent");
        assertNull(findResult);
    }

    @Test
    void testFindAllByOrderIdIfOrderExists() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }
        List<Payment> paymentList = paymentRepository.findAllByOrderId(payments.get(1).getOrderId());
        assertEquals(2, paymentList.size());
    }
}
