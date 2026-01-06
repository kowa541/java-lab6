
/**
 * Класс для тестирования аннотации @Invoke.
 * Метод с @Invoke изменяет внутреннее состояние.
 */
public class InvokeTestSubject {
    private boolean wasInvoked = false;

    @Invoke
    public void doSomething() {
        this.wasInvoked = true;
    }

    public boolean wasInvoked() {
        return this.wasInvoked;
    }

    // Метод без аннотации — не должен вызываться
    public void doNothing() {
        this.wasInvoked = false;
    }
}
