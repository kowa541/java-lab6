/**
 * <p>Аннотация, указывающая список классов, подлежащих проверке (валидации).</p>
 *
 * <p>Может применяться как к объявлениям типов ({@code ElementType.TYPE}),
 * так и к другим аннотациям ({@code ElementType.ANNOTATION_TYPE}),
 * что позволяет создавать иерархии валидации или повторно использовать
 * правила проверки через мета-аннотации.</p>
 *
 * <p>Содержит обязательное свойство {@code value}, представляющее массив
 * классов ({@code Class<?>[]}), каждый из которых должен пройти определённую
 * процедуру валидации (например, проверку структуры, аннотаций или состояния).</p>
 *
 * <h3>Примеры использования:</h3>
 * <pre>{@code
 * // Применение к классу
 * @Validate({User.class, Role.class})
 * public class SecurityConfig { ... }
 *
 * // Применение к кастомной аннотации (мета-аннотация)
 * @Validate({String.class, Integer.class})
 * @Target(ElementType.TYPE)
 * @Retention(RetentionPolicy.RUNTIME)
 * public @interface ValidData { }
 * }</pre>
 *
 * <p>Обработка аннотации осуществляется во время выполнения через рефлексию,
 * что требует сохранения её в байт-коде ({@code RetentionPolicy.RUNTIME}).</p>
 *
 * @see java.lang.annotation.Target
 * @see java.lang.annotation.Retention
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Validate {

    /**
     * Возвращает массив классов, которые должны быть проверены.
     * Это обязательное свойство аннотации.
     *
     * @return массив ссылок на классы, подлежащих валидации
     */
    Class<?>[] value();
}
