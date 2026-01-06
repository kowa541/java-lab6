
import java.lang.annotation.*;

/**
 * Аннотация, помечающая метод для автоматического вызова.
 * Может применяться только к методам и доступна во время выполнения.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Invoke {
}