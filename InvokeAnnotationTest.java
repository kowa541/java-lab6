/**
 * <p>Тестовый класс для проверки корректной обработки методов,
 * помеченных аннотацией {@link Invoke}.</p>
 *
 * <p>Этот тест использует рефлексию для поиска методов с аннотацией {@code @Invoke}
 * в экземпляре класса {@link InvokeExample}, вызывает их и проверяет,
 * что побочные эффекты таких вызовов действительно происходят.</p>
 *
 * <p>Основная цель теста — убедиться, что:
 * <ul>
 *   <li>Аннотация {@code @Invoke} обнаруживается во время выполнения;</li>
 *   <li>Помеченный метод может быть вызван через рефлексию;</li>
 *   <li>Вызов приводит к ожидаемому изменению состояния объекта.</li>
 * </ul>
 * </p>
 *
 * <p>Тест использует JUnit 5 и проверяет как наличие аннотированного метода,
 * так и его корректное исполнение.</p>
 *
 * @see Invoke
 * @see InvokeExample
 */
public class InvokeAnnotationTest {

    /**
     * Тестируемый экземпляр класса {@link InvokeExample},
     * содержащий метод, помеченный аннотацией {@link Invoke}.
     */
    private InvokeExample subject;

    /**
     * Инициализирует тестовый объект перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        subject = new InvokeExample();
    }

    /**
     * Проверяет, что метод, помеченный аннотацией {@link Invoke},
     * действительно вызывается и изменяет состояние объекта.
     *
     * <p>Шаги теста:
     * <ol>
     *   <li>Убеждается, что флаг {@code wasCalled} изначально равен {@code false};</li>
     *   <li>Использует рефлексию для поиска всех методов с аннотацией {@code @Invoke};</li>
     *   <li>Вызывает найденный метод;</li>
     *   <li>Проверяет, что метод был найден и вызван;</li>
     *   <li>Проверяет, что после вызова флаг {@code wasCalled} стал {@code true}.</li>
     * </ol>
     *
     * @throws Exception если возникает ошибка при вызове метода через рефлексию
     */
    @Test
    void testInvokeAnnotatedMethodIsCalled() throws Exception {
        assertFalse(subject.wasCalled, "Изначально wasCalled должно быть false");

        Class<?> clazz = subject.getClass();
        boolean foundAndInvoked = false;

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Invoke.class)) {
                foundAndInvoked = true;
                method.setAccessible(true);
                method.invoke(subject);
            }
        }

        assertTrue(foundAndInvoked, "Должен существовать метод с @Invoke");
        assertTrue(subject.wasCalled, "Метод должен был установить wasCalled = true");
    }
}
