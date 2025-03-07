package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceImplTest {
    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Payment payment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        payment = new Payment("pymt-123", "order-456", 50000, "John Doe", PaymentStatus.PENDING.getValue());
    }

    @Test
    void testCreatePayment_Success() {
        when(paymentRepository.save(payment)).thenReturn(payment);

        Payment result = paymentService.createPayment(payment);

        assertNotNull(result);
        assertEquals(payment.getId(), result.getId());
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    void testCreatePayment_AlreadyExists() {
        when(paymentRepository.findById(payment.getId())).thenReturn(payment);

        Executable action = () -> paymentService.createPayment(payment);

        assertThrows(IllegalArgumentException.class, action);
        verify(paymentRepository, never()).save(any());
    }

    @Test
    void testUpdateStatus_Success() {
        when(paymentRepository.findById(payment.getId())).thenReturn(payment);

        Payment updatedPayment = paymentService.updateStatus(payment.getId(), PaymentStatus.SUCCESS.getValue());

        assertNotNull(updatedPayment);
        assertEquals(PaymentStatus.SUCCESS.getValue(), updatedPayment.getStatus());
        verify(paymentRepository, times(1)).save(any());
    }

    @Test
    void testUpdateStatus_InvalidStatus() {
        when(paymentRepository.findById(payment.getId())).thenReturn(payment);

        Executable action = () -> paymentService.updateStatus(payment.getId(), "INVALID_STATUS");

        assertThrows(IllegalArgumentException.class, action);
        verify(paymentRepository, never()).save(any());
    }

    @Test
    void testUpdateStatus_PaymentNotFound() {
        when(paymentRepository.findById(payment.getId())).thenReturn(null);

        Executable action = () -> paymentService.updateStatus(payment.getId(), PaymentStatus.SUCCESS.getValue());

        assertThrows(NoSuchElementException.class, action);
        verify(paymentRepository, never()).save(any());
    }

    @Test
    void testFindById_Success() {
        when(paymentRepository.findById(payment.getId())).thenReturn(payment);

        Payment result = paymentService.findById(payment.getId());

        assertNotNull(result);
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testFindById_NotFound() {
        when(paymentRepository.findById(payment.getId())).thenReturn(null);

        Executable action = () -> paymentService.findById(payment.getId());

        assertThrows(NoSuchElementException.class, action);
    }

    @Test
    void testFindAllByOrderId_Success() {
        List<Payment> payments = new ArrayList<>();
        payments.add(payment);

        when(paymentRepository.findAllByOrderId(payment.getOrderId())).thenReturn(payments);

        List<Payment> result = paymentService.findAllByOrderId(payment.getOrderId());

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testFindAllByOrderId_NotFound() {
        when(paymentRepository.findAllByOrderId(payment.getOrderId())).thenReturn(new ArrayList<>());

        List<Payment> result = paymentService.findAllByOrderId(payment.getOrderId());

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
