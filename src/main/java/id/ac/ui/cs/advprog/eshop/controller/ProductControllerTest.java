package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void testCreateProductPage() throws Exception {
        mockMvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("createProduct"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    public void testCreateProduct() throws Exception {
        mockMvc.perform(post("/product/create")
                        .param("name", "Test Product")
                        .param("price", "1000"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));
    }

    @Test
    public void testEditProductPage() throws Exception {
        Product mockProduct = new Product("1", "Test Product", 1000);
        when(productService.findById("1")).thenReturn(mockProduct);

        mockMvc.perform(get("/product/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("editProduct"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        mockMvc.perform(post("/product/edit")
                        .param("id", "1")
                        .param("name", "Updated Product")
                        .param("price", "1500"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        mockMvc.perform(post("/product/delete")
                        .param("productId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));
    }

    @Test
    public void testEditProductPage_InvalidId() throws Exception {
        when(productService.findById("999")).thenReturn(null);

        mockMvc.perform(get("/product/edit/999"))
                .andExpect(status().isOk())
                .andExpect(view().name("errorPage"));
    }
}
