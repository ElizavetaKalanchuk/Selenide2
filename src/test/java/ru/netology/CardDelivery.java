package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;


public class CardDelivery {
    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldCardDelivery() {
        open("http://localhost:9999");

        $("[data-test-id=city] input").setValue("Санкт-Петербург");
        $("[data-test-id=date] input").doubleClick();
        $("[data-test-id=date] input").sendKeys(generateDate(6, "dd.MM.yyyy"));
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79851111111");
        $("[data-test-id=agreement]").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(Condition.hidden, Duration.ofSeconds(100));
        $(withText("Встреча успешно забронирована")).shouldBe(Condition.hidden, Duration.ofSeconds(100));
        $("[data-test-id=notification]").shouldBe(Condition.visible, Duration.ofSeconds(100));
        $("[data-test-id=notification]").shouldHave(Condition.text("Успешно!\n" +
                "Встреча успешно забронирована на " + generateDate(6, "dd.MM.yyyy"))).shouldBe(Condition.visible);
    }
}