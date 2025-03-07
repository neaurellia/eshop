// [RED] Add PaymentServiceTest with test structure
package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    List<Payment> payments;
    Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        payments = new ArrayList<>();
        paymentData = new HashMap<>();
    }

    @Test
    void testCreatePaymentWithEmptyData() {
        paymentData.put("voucherCode", "");
        assertThrows(IllegalArgumentException.class, () ->
                paymentService.addPayment("order123", "VOUCHER", paymentData)
        );
    }

    @Test
    void testCreatePaymentWithoutStatus() {
        paymentData.put("voucherCode", "ESHOP123456789012");
        Payment payment = paymentService.addPayment("order123", "VOUCHER", paymentData);
        assertNotNull(payment);
        assertEquals("PENDING", payment.getStatus());
    }

    @Test
    void testCreatePaymentSuccessfully() {
        paymentData.put("voucherCode", "ESHOP123456789012");
        Payment payment = new Payment("pay123", "VOUCHER", "SUCCESS", paymentData);
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);
        Payment result = paymentService.addPayment("order123", "VOUCHER", paymentData);
        assertEquals("SUCCESS", result.getStatus());
    }

    @Test
    void testCreatePaymentWithInvalidData() {
        paymentData.put("voucherCode", "INVALID123");
        assertThrows(IllegalArgumentException.class, () ->
                paymentService.addPayment("order123", "VOUCHER", paymentData)
        );
    }

    @Test
    void testUpdatePaymentWithValidStatus() {
        Payment payment = new Payment("pay123", "VOUCHER", "PENDING", paymentData);
        when(paymentRepository.findById("pay123")).thenReturn(Optional.of(payment));
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        Payment updated = paymentService.setStatus(payment, "SUCCESS");
        assertEquals("SUCCESS", updated.getStatus());
    }

    @Test
    void testUpdatePaymentWithInvalidStatus() {
        Payment payment = new Payment("pay123", "VOUCHER", "PENDING", paymentData);
        when(paymentRepository.findById("pay123")).thenReturn(Optional.of(payment));

        assertThrows(IllegalArgumentException.class, () ->
                paymentService.setStatus(payment, "INVALID_STATUS")
        );
    }

    @Test
    void testGetPaymentById() {
        Payment payment = new Payment("pay123", "VOUCHER", "SUCCESS", paymentData);
        when(paymentRepository.findById("pay123")).thenReturn(Optional.of(payment));
        Payment result = paymentService.getPayment("pay123");
        assertEquals("pay123", result.getId());
    }

    @Test
    void testGetAllPayments() {
        when(paymentRepository.findAll()).thenReturn(payments);
        List<Payment> results = paymentService.getAllPayments();
        assertEquals(payments.size(), results.size());
    }
}
