package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class SelenideTest {

    @BeforeEach
    void openForm() {
        open("http://localhost:9999");
    }

    @Test
    void shouldCheckCorrectForm() {
        $("[data-test-id='city'] [placeholder='Город']").sendKeys("Омск");
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] [placeholder='Дата встречи']").sendKeys(date);
        $("[data-test-id='name'] [name='name']").sendKeys("Иван Иванов");
        $("[data-test-id='phone'] [name='phone']").sendKeys("+79012345678");
        $("[data-test-id=agreement]").click();
        $("[type='button'] .button__text").click();
        $(".notification__title").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__title").find(String.valueOf(exactText("Успешно!")));
        $("[data-test-id=notification] .notification__content").should(exactText("Встреча успешно забронирована на " + date));
    }

    @Test
    void shouldUnCheckCorrectCity() {
        $("[data-test-id='city'] [placeholder='Город']").sendKeys("Караганда");
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] [placeholder='Дата встречи']").sendKeys(date);
        $("[data-test-id='name'] [name='name']").sendKeys("Иван Иванов");
        $("[data-test-id='phone'] [name='phone']").sendKeys("+79012345678");
        $("[data-test-id=agreement]").click();
        $("[type='button'] .button__text").click();
        $("[data-test-id='city'] .input__sub").should(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldUnCheckCorrectDate() {
        $("[data-test-id='city'] [placeholder='Город']").sendKeys("Москва");
        $(".input_type_tel [type='tel']").doubleClick().sendKeys("Delete");
        String date = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] [placeholder='Дата встречи']").sendKeys(date);
        $("[data-test-id='name'] [name='name']").sendKeys("Иван Иванов");
        $("[data-test-id='phone'] [name='phone']").sendKeys("+79012345678");
        $("[data-test-id=agreement]").click();
        $("[type='button'] .button__text").click();
        $("[data-test-id='date'] .input__sub").should(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldUnCheckCorrectName() {
        $("[data-test-id='city'] [placeholder='Город']").sendKeys("Москва");
        $(".input_type_tel [type='tel']").doubleClick().sendKeys("Delete");
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] [placeholder='Дата встречи']").sendKeys(date);
        $("[data-test-id='name'] [name='name']").sendKeys("Ivan Иванов");
        $("[data-test-id='phone'] [name='phone']").sendKeys("+79012345678");
        $("[data-test-id=agreement]").click();
        $("[type='button'] .button__text").click();
        $(".input_invalid[data-test-id='name'] .input__sub").should(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }


    @Test
    void shouldUnCheckCorrectPhone() {
        $("[data-test-id='city'] [placeholder='Город']").sendKeys("Москва");
        $(".input_type_tel [type='tel']").doubleClick().sendKeys("Delete");
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] [placeholder='Дата встречи']").sendKeys(date);
        $("[data-test-id='name'] [name='name']").sendKeys("Иван Иванов");
        $("[data-test-id='phone'] [name='phone']").sendKeys("55566");
        $("[data-test-id=agreement]").click();
        $("[type='button'] .button__text").click();
        $(".input_invalid[data-test-id='phone'] .input__sub").should(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldUnCheckCorrectCheck() {
        $("[data-test-id='city'] [placeholder='Город']").sendKeys("Москва");
        $(".input_type_tel [type='tel']").doubleClick().sendKeys("Delete");
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] [placeholder='Дата встречи']").sendKeys(date);
        $("[data-test-id='name'] [name='name']").sendKeys("Иван Иванов");
        $("[data-test-id='phone'] [name='phone']").sendKeys("+79012345678");
        $("[type='button'] .button__text").click();
        $("[data-test-id=agreement] .checkbox__text").should(exactText("Я соглашаюсь с условиями обработки и использования  моих персональных данных"));
    }

    @Test
    void shouldUnCheckCorrectNameEmpty() {
        $("[data-test-id='city'] [placeholder='Город']").sendKeys("Москва");
        $(".input_type_tel [type='tel']").doubleClick().sendKeys("Delete");
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] [placeholder='Дата встречи']").sendKeys(date);
        $("[data-test-id='phone'] [name='phone']").sendKeys("+79012345678");
        $("[data-test-id=agreement]").click();
        $("[type='button'] .button__text").click();
        $("[data-test-id='name'] .input__sub").should(exactText("Поле обязательно для заполнения"));
    }

}
