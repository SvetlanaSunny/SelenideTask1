package ru.netology;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.conditions.Value;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.*;

public class SenenideTask2Test {

    @Test
    void shouldCheckCorrectFormFirst() {
        open("http://localhost:9999");
        $("[data-test-id='city'] [placeholder='Город']").val("Ма");
        $$(".menu-item .menu-item__control").find(exactText("Магадан")).click();
        LocalDate lDateNow = LocalDate.now();
        String dateNowMM = lDateNow.format(DateTimeFormatter.ofPattern("MM"));
        LocalDate lDateAdd = LocalDate.now().plusDays(20);
        String dateAddMM = lDateAdd.format(DateTimeFormatter.ofPattern("MM"));
        $(".input_type_tel .input__icon").click();
        int dateAddDD = lDateAdd.getDayOfMonth();
        if (dateNowMM != dateAddMM) {
            $$(".calendar__arrow").find(attribute("data-step","1")).click();
        }
        $$(".calendar__day").find(text(String.valueOf(dateAddDD))).click();
        $("[data-test-id='name'] [name='name']").sendKeys("Иван Иванов");
        $("[data-test-id='phone'] [name='phone']").sendKeys("+79012345678");
        $("[data-test-id=agreement]").click();
        $("[type='button'] .button__text").click();
        $(".notification__title").shouldBe(visible, Duration.ofSeconds(20));
        $(".notification__title").find(String.valueOf(exactText("Успешно!")));
        String dateRes = lDateAdd.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=notification] .notification__content").should(exactText("Встреча успешно забронирована на " + dateRes));

    }
}
