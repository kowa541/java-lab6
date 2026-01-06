
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тест проверяет, что при некорректных значениях в @Two
 * выбрасывается IllegalArgumentException.
 */
public class TwoAnnotationValidationTest {

    /**
     * Тестирует случай, когда first = "" и second = -1.
     * Ожидается исключение.
     */
    @Test
    void testTwoAnnotationWithInvalidValuesThrowsException() {
        // Выполняем валидацию класса с некорректной аннотацией
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Main.validateTwoAnnotation(TwoInvalidExample.class),
                "Должен быть выброшен IllegalArgumentException при некорректных значениях @Two"
        );

        // Проверяем, что сообщение содержит информацию об ошибке
        String message = exception.getMessage();
        assertTrue(
                message.contains("first") || message.contains("second"),
                "Сообщение об ошибке должно указывать на проблему с first или second"
        );
    }

    /**
     * Дополнительно: убедимся, что КОРРЕКТНАЯ аннотация НЕ вызывает исключения.
     */
    @Test
    void testTwoAnnotationWithValidValuesDoesNotThrow() {
        // Используем существующий TwoExample (first="Laboratory", second=6)
        assertDoesNotThrow(
                () -> Main.validateTwoAnnotation(TwoExample.class),
                "Корректная аннотация @Two не должна вызывать исключение"
        );
    }
}