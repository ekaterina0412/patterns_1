package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        // TODO: добавить логику теста в рамках которого будет выполнено планирование и перепланирование встречи.
        // Для заполнения полей формы можно использовать пользователя validUser и строки с датами в переменных
        // firstMeetingDate и secondMeetingDate. Можно также вызывать методы generateCity(locale),
        // generateName(locale), generatePhone(locale) для генерации и получения в тесте соответственно города,
        // имени и номера телефона без создания пользователя в методе generateUser(String locale) в датагенераторе
        $("[data-test-id=city] .input__control").setValue(validUser.getCity());
        $("[data-test-id=date] .input__control").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(firstMeetingDate);
        $("[data-test-id=name] .input__control").setValue(validUser.getName());
        $("[data-test-id=phone] .input__control").setValue(validUser.getPhone());
        $("[data-test-id=agreement] .checkbox__box").click();
        $$(".button").find(Condition.exactText("Запланировать")).click();
        $(Selectors.withText("Успешно")).shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(Selectors.withText("Встреча успешно запланирована на")).shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(Selectors.withText(firstMeetingDate)).shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id=success-notification] .icon-button__text").click();
        $("[data-test-id=date] .input__control").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(secondMeetingDate);
        $$(".button").find(Condition.exactText("Запланировать")).click();
        $(Selectors.withText("Необходимо подтверждение")).shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id=replan-notification] .button__content").click();
        $(Selectors.withText("Успешно")).shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(Selectors.withText("Встреча успешно запланирована на")).shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(Selectors.withText(secondMeetingDate)).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }
}