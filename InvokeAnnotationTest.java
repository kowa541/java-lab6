
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Method;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тест для проверки корректности вызова методов, отмеченных аннотацией @Invoke.
 */
public class InvokeAnnotationTest {

    private InvokeExample subject;

    @BeforeEach
    void setUp() {
        subject = new InvokeExample();
    }

    @Test
    void testInvokeAnnotatedMethodIsCalled() throws Exception {
        assertFalse(subject.wasCalled, "Изначально wasCalled должно быть false");

        Class<?> clazz = subject.getClass();
        boolean foundAndInvoked = false;

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Invoke.class)) {
                foundAndInvoked = true;
                method.setAccessible(true);
                method.invoke(subject); // ← ИЗМЕНЕНО: вызываем метод
            }
        }

        assertTrue(foundAndInvoked, "Должен существовать метод с @Invoke");
        assertTrue(subject.wasCalled, "Метод должен был установить wasCalled = true");
    }
}