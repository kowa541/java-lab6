
import java.lang.annotation.*;

/**
 * Аннотация для указания списка классов, подлежащих проверке.
 * Может применяться к типам и другим аннотациям.
 * Обязательное свойство: value (массив Class).
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Validate {
    Class<?>[] value();
}
