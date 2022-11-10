package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class SenenideTask2Test {

    @Test
    void shouldCheckCorrectFormFirst() {
        open("http://localhost:9999");
        $("[data-test-id='city'] [placeholder='Город']").val("Ма");
        $$(".menu-item .menu-item__control").find(exactText("Магадан")).click();
        LocalDate lDate = LocalDate.now().plusDays(7);
        String date = lDate.format(DateTimeFormatter.ofPattern("dd"));
        $(".input_type_tel .input__icon").click();
        $$(".calendar__day").find(exactText(date)).click();
        $("[data-test-id='name'] [name='name']").sendKeys("Иван Иванов");
        $("[data-test-id='phone'] [name='phone']").sendKeys("+79012345678");
        $("[data-test-id=agreement]").click();
        $("[type='button'] .button__text").click();
        $(".notification__title").shouldBe(visible, Duration.ofSeconds(20));
        $(".notification__title").find(String.valueOf(exactText("Успешно!")));
        String dateRes = lDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=notification] .notification__content").should(exactText("Встреча успешно забронирована на " + dateRes));

    }
}
