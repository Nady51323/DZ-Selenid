import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import javax.net.ssl.KeyManagerFactorySpi;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;


public class CardDeliveryOrderTest {

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void successfulCardOrder() {
        Configuration.holdBrowserOpen = true;

        open("http://localhost:9999/");
        String planningDate = generateDate(4);
        $x("//input[@placeholder='Город']").setValue("Омск").pressEscape();
        $x("//input[@placeholder='Дата встречи']").doubleClick().pressEscape().sendKeys(planningDate);
        $x("//input[@name='name']").setValue("Иванов Иван");
        $x("//input[@name='phone']").setValue("+79118000000");
        $x("//label[@data-test-id='agreement']").click();
        $x("//span[contains(text(),'Забронировать')]").click();
        $x("//div[@class='notification__content']").shouldBe(visible, Duration.ofSeconds(20));
        $x("//div[contains(text(),'Встреча успешно забронирована на ')]").shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(20)).shouldBe(visible);

    }


}

