/**
 * <p>Аннотация, помечающая метод для автоматического вызова в определённых сценариях.</p>
 *
 * <p>Применяется исключительно к методам ({@code ElementType.METHOD}) и сохраняется
 * во время выполнения программы ({@code RetentionPolicy.RUNTIME}), что позволяет
 * использовать её через рефлексию.</p>
 *
 * <p>Методы, помеченные этой аннотацией, могут быть автоматически обнаружены и вызваны
 * фреймворком, например при инициализации компонента, обработке событий
 * или выполнении пользовательских расширений.</p>
 *
 * <h3>Пример использования:</h3>
 * <pre>{@code
 * public class EventHandler {
 *     @Invoke
 *     public void onStartup() {
 *         System.out.println("Система запущена!");
 *     }
 * }
 * }</pre>
 *
 * <p>Аннотация не содержит параметров и служит исключительно как маркер.</p>
 *
 * @see java.lang.annotation.Target
 * @see java.lang.annotation.Retention
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Invoke {
    // Маркерная аннотация без параметров
}
