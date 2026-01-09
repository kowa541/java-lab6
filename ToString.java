/**
 * <p>Аннотация, управляющая включением полей или всего типа в строковое представление объекта.</p>
 *
 * <p>Может применяться как к классам ({@code ElementType.TYPE}), так и к отдельным полям
 * ({@code ElementType.FIELD}). Предназначена для использования в утилитах, генерирующих
 * строковое представление объектов (например, кастомных реализациях {@code toString()}
 * или сериализаторах).</p>
 *
 * <p>Аннотация содержит необязательное свойство {@code value}, определяющее,
 * должно ли аннотированное поле (или все поля класса) включаться в вывод.
 * По умолчанию используется {@link Mode#YES}.</p>
 *
 * <h3>Примеры использования:</h3>
 * <pre>{@code
 * // Включение всех полей класса по умолчанию
 * @ToString
 * public class User {
 *     public String name;
 *     public int age;
 * }
 *
 * // Исключение конкретного поля
 * public class User {
 *     public String name;
 *
 *     @ToString(Mode.NO)
 *     public String password;
 * }
 *
 * // Явное включение (избыточно, но допустимо)
 * @ToString(Mode.YES)
 * public class Config { ... }
 * }</pre>
 *
 * <p>Если аннотация применена к классу, она может задавать глобальное поведение по умолчанию
 * для всех его полей, если они не переопределяют его своей собственной аннотацией.</p>
 *
 * @see Mode
 * @see java.lang.annotation.Target
 * @see java.lang.annotation.Retention
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ToString {
    /**
     * Определяет, должно ли аннотированное поле (или тип) участвовать в формировании
     * строкового представления объекта.
     *
     * @return режим отображения; по умолчанию {@link Mode#YES}
     */
    Mode value() default Mode.YES;
}
