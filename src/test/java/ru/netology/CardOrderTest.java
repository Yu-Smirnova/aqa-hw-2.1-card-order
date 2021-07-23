package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class CardOrderTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpForAll(){
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
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

    @Test
    void shouldNotSendOrderWithoutData(){
        driver.get("http://localhost:9999/");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText().strip();
        String expected = "Поле обязательно для заполнения";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldNotSendOrderWithNameInEnglish(){
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[type=text]")).sendKeys("Ivan Ivanov");
        driver.findElement(By.cssSelector("[type=tel]")).sendKeys("+79000000000");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText().strip();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldNotSendOrderWithoutPhoneNumber(){
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[type=text]")).sendKeys("Иванов Иван");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText().strip();
        String expected = "Поле обязательно для заполнения";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldNotSendOrderWithoutName() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[type=tel]")).sendKeys("+79000000000");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText().strip();
        String expected = "Поле обязательно для заполнения";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldNotSendOrderWithoutCheckBox(){
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[type=text]")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[type=tel]")).sendKeys("+79000000000");
        driver.findElement(By.className("button")).click();
        String actual = driver.findElement(By.className("checkbox__text")).getCssValue("color");
        String expected = "rgba(255, 92, 92, 1)";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldNotSendOrderWithShoterPhoneNumber(){
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[type=text]")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[type=tel]")).sendKeys("+7900000000");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText().strip();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        Assertions.assertEquals(expected, actual);
    }
}
