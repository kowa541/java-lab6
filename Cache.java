
import java.lang.annotation.*;

/**
 * Аннотация для указания кешируемых областей.
 * Применяется только к типам.
 * Необязательное свойство value — массив строк (по умолчанию пуст).
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {
    String[] value() default {};
}
