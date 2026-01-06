
import java.lang.annotation.*;

/**
 * Аннотация для управления включением поля в строковое представление объекта.
 * Может применяться к типам и полям.
 * Необязательное свойство value с значением по умолчанию YES.
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ToString {
    Mode value() default Mode.YES;
}