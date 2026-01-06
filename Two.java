
import java.lang.annotation.*;

/**
 * Аннотация с двумьмя обязательными свойствами: строка и число.
 * Применяется только к типам.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Two {
    String first();
    int second();
}