
/**
 * Пример класса с @ToString на уровне класса и поля.
 */
@ToString
public class ToStringExample {
    @ToString(Mode.YES)
    public String name = "Alice";

    @ToString(Mode.NO)
    public int secret = 42;

    @ToString(Mode.YES)
    public double score = 95.5;
}