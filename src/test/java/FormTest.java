import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;


import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.openqa.selenium.Keys.*;

public class FormTest {

    @BeforeEach
    void setup() {
        Selenide.open("http://localhost:9999");
    }

    public String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldRegisterDeliveryTest() {
        String deliveryDate = generateDate(3, "dd.MM.yyyy");

        $("[data-test-id='city'] input").setValue("Тула");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(deliveryDate);
        $("[data-test-id='name'] input").setValue("Сидоров Сергей");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(Condition.text("Забронировать")).click();
        $("[data-test-id='notification']").should(Condition.visible,
                Duration.ofSeconds(15)).should(Condition.text("Встреча успешно забронирована на " + deliveryDate));
    }

    @Test
    public void shouldRegisterDeliveryTest2() {
        Selenide.open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Ту");
        $$(".menu-item").findBy(Condition.exactText("Тула")).click();
        $("[data-test-id=date] input").click();
        $$(".calendar__day.calendar__day").findBy(Condition.exactText("13")).click();
        $("[data-test-id=name] input").setValue("Сидоров Сергей");
        $("[data-test-id=phone] input").setValue("+79012345678");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(".notification__content").shouldBe(Condition.visible, Duration.ofSeconds(15)).shouldHave(Condition.text("Встреча успешно забронирована на 13.02.2025"));
    }

}