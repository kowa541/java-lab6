
import java.lang.annotation.*;

/**
 * Аннотация, указывающая тип по умолчанию.
 * Может применяться к типам и полям.
 * Обязательное свойство: value (Class).
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Default {
    Class<?> value();
}
