package ru.netology;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class CardOrderTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpForAll(){
        System.setProperty("webdriver.chrome.driver", ".\\driver\\chromedriver.exe");
    }

    @BeforeEach
    void setUp(){
        driver = new ChromeDriver();
    }

    @AfterEach
    void tearDown(){
        driver.close();
        driver = null;
    }

    @Test
    void shouldSendOrder(){
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[type=text]")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[type=tel]")).sendKeys("+79000000000");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String actual = driver.findElement(By.className("paragraph")).getText().strip();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        Assertions.assertEquals(expected, actual);
    }
}
