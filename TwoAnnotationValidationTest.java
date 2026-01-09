/**
 * <p>Тестовый класс, проверяющий валидацию аннотации {@link Two} через метод
 * {@link Main#validateTwoAnnotation(Class)}.</p>
 *
 * <p>Аннотация {@code @Two} требует два обязательных параметра: строку ({@code first})
 * и целое число ({@code second}). Этот тест проверяет, что при недопустимых значениях
 * (например, пустая строка или отрицательное число) система корректно выбрасывает
 * {@link IllegalArgumentException}.</p>
 *
 * <p>Также включён позитивный тест, подтверждающий, что корректно аннотированный класс
 * проходит валидацию без исключений.</p>
 *
 * @see Two
 * @see Main#validateTwoAnnotation(Class)
 * @see TwoExample
 * @see TwoInvalidExample
 */
public class TwoAnnotationValidationTest {

    /**
     * Проверяет, что при наличии некорректных значений в аннотации {@link Two}
     * (пустая строка в {@code first} и отрицательное число в {@code second})
     * метод валидации выбрасывает {@link IllegalArgumentException}.
     *
     * <p>Ожидается, что сообщение исключения укажет на проблему с одним
     * или обоими параметрами аннотации.</p>
     */
    @Test
    void testTwoAnnotationWithInvalidValuesThrowsException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Main.validateTwoAnnotation(TwoInvalidExample.class),
                "Должен быть выброшен IllegalArgumentException при некорректных значениях @Two"
        );

        String message = exception.getMessage();
        assertTrue(
                message.contains("first") || message.contains("second"),
                "Сообщение об ошибке должно указывать на проблему с first или second"
        );
    }

    /**
     * Проверяет, что класс с корректной аннотацией {@link Two}
     * (например, {@code first = "Laboratory"}, {@code second = 6})
     * проходит валидацию без выброса исключений.
     *
     * <p>Этот тест служит позитивным сценарием и подтверждает,
     * что валидные данные принимаются системой.</p>
     */
    @Test
    void testTwoAnnotationWithValidValuesDoesNotThrow() {
        assertDoesNotThrow(
                () -> Main.validateTwoAnnotation(TwoExample.class),
                "Корректная аннотация @Two не должна вызывать исключение"
        );
    }
}
