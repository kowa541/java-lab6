
/**
 * Пример класса с методом, помеченным аннотацией @Invoke.
 */
public class InvokeExample {
    /** Флаг, показывающий, был ли вызван метод с @Invoke */
    public boolean wasCalled = false; // ← ДОБАВЛЕНО

    @Invoke
    public void doSomething() {
        System.out.println("   [!] Метод с @Invoke был вызван!");
        this.wasCalled = true; // ← ДОБАВЛЕНО: изменение состояния
    }

    public void notAnnotated() {
        System.out.println("Этот метод НЕ аннотирован.");
    }
}