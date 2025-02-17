package id.ac.ui.cs.advprog.eshop.functional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import org.springframework.boot.test.web.server.LocalServerPort;

import io.github.bonigarcia.seljup.SeleniumJupiter;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    private String baseUrl;

    @BeforeEach
    void initialize() {
        baseUrl = "http://localhost:" + serverPort;
    }

    @Test
    void shouldCreateProductSuccessfully(WebDriver driver) throws Exception {
        driver.get(baseUrl + "/product/create");

        WebElement productNameField = driver.findElement(By.id("nameInput"));
        productNameField.sendKeys("Bacon Pancakes");

        WebElement quantityField = driver.findElement(By.id("quantityInput"));
        quantityField.sendKeys("123");

        WebElement submitBtn = driver.findElement(By.cssSelector("button[type='submit']"));
        submitBtn.click();

        assertEquals(baseUrl + "/product/list", driver.getCurrentUrl());
        WebElement productTable = driver.findElement(By.tagName("table"));
        String tableContent = productTable.getText();
        assertTrue(tableContent.contains("Bacon Pancakes"));
        assertTrue(tableContent.contains("123"));
    }


    @Test
    void shouldFailWhenQuantityIsNegative(WebDriver driver) throws Exception {
        driver.get(baseUrl + "/product/create");

        WebElement productNameField = driver.findElement(By.id("nameInput"));
        productNameField.sendKeys("Test Product");

        WebElement quantityField = driver.findElement(By.id("quantityInput"));
        quantityField.sendKeys("-789");

        WebElement submitBtn = driver.findElement(By.cssSelector("button[type='submit']"));
        submitBtn.click();

        assertTrue(driver.getCurrentUrl().contains("/product/create"));
    }
}
