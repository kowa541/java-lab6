/**
 * <p>Аннотация, указывающая класс, который должен использоваться как тип по умолчанию
 * при обработке поля или типа.</p>
 *
 * <p>Может применяться как к объявлениям классов ({@code ElementType.TYPE}),
 * так и к полям ({@code ElementType.FIELD}).</p>
 *
 * <p>Аннотация требует обязательного указания параметра {@code value},
 * который должен быть ссылкой на класс ({@code Class<?>}).</p>
 *
 * <h3>Пример использования:</h3>
 * <pre>{@code
 * @Default(User.class)
 * public class UserProfile { ... }
 *
 * public class ServiceConfig {
 *     @Default(HashMap.class)
 *     private Map<String, Object> settings;
 * }
 * }</pre>
 *
 * @see java.lang.annotation.Target
 * @see java.lang.annotation.Retention
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Default {
    /**
     * Указывает класс, который служит типом по умолчанию.
     * Это обязательное свойство аннотации.
     *
     * @return класс, используемый по умолчанию
     */
    Class<?> value();
}
