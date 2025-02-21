package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)  // 🔹 Replaces @MockBean functionality
class ProductControllerIT {

    @Mock
    private ProductService service;  // 🔹 Replaces @MockBean

    @Mock
    private Model model;

    @InjectMocks
    private ProductController controller; // Inject mocks into controller

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Ensures mock initialization
    }

    @Test
    void testProductListPage() {
        when(service.findAll()).thenReturn(Collections.emptyList());

        String view = controller.productListPage(model);

        assertEquals("productList", view);
        verify(service).findAll();
    }
}
