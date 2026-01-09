/**
 * <p>Главный класс лабораторной работы №6, демонстрирующий применение и обработку
 * пользовательских аннотаций через рефлексию.</p>
 *
 * <p>Класс содержит статические методы для обработки следующих аннотаций:
 * <ul>
 *   <li>{@link Invoke} — автоматический вызов методов;</li>
 *   <li>{@link Default} — получение класса по умолчанию;</li>
 *   <li>{@link ToString} — формирование строкового представления с учётом режима отображения полей;</li>
 *   <li>{@link Validate} — вывод списка классов, подлежащих проверке;</li>
 *   <li>{@link Two} — чтение и валидация двух обязательных параметров;</li>
 *   <li>{@link Cache} — получение списка кешируемых областей.</li>
 * </ul>
 * </p>
 *
 * <p>Точка входа {@link #main(String[])} последовательно демонстрирует работу
 * всех аннотаций на примере тестовых классов, таких как {@link InvokeExample},
 * {@link DefaultExample}, {@link ToStringExample} и других.</p>
 *
 * <p>Класс предназначен исключительно для учебных целей и демонстрации возможностей
 * метапрограммирования в Java с использованием аннотаций и рефлексии.</p>
 *
 * @see Invoke
 * @see Default
 * @see ToString
 * @see Validate
 * @see Two
 * @see Cache
 */
public class Main {

    /**
     * Вызывает все методы объекта, помеченные аннотацией {@link Invoke}.
     *
     * <p>Метод использует рефлексию для поиска и вызова аннотированных методов.
     * Все методы делаются доступными через {@link java.lang.reflect.AccessibleObject#setAccessible(boolean)}.
     * Ошибки при вызове перехватываются и выводятся в {@code System.err}.</p>
     *
     * @param obj объект, методы которого необходимо проверить и вызвать
     * @throws IllegalArgumentException если передан {@code null}
     */
    public static void invokeAnnotatedMethods(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Объект для обработки @Invoke не может быть null");
        }
        Class<?> clazz = obj.getClass();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Invoke.class)) {
                try {
                    method.setAccessible(true);
                    method.invoke(obj);
                } catch (Exception e) {
                    System.err.println("Ошибка при вызове метода " + method.getName() + ": " + e.getMessage());
                }
            }
        }
    }

    /**
     * Выводит простое имя класса, указанного в аннотации {@link Default} на уровне типа.
     *
     * <p>Если аннотация отсутствует, выводится соответствующее сообщение.
     * Метод безопасен к передаче {@code null}.</p>
     *
     * @param clazz класс, аннотированный {@link Default}
     */
    public static void processDefaultAnnotation(Class<?> clazz) {
        if (clazz == null) {
            System.out.println("Передан null вместо класса для @Default.");
            return;
        }
        if (clazz.isAnnotationPresent(Default.class)) {
            Default annotation = clazz.getAnnotation(Default.class);
            System.out.println("Класс по умолчанию: " + annotation.value().getSimpleName());
        } else {
            System.out.println("Класс " + clazz.getSimpleName() + " не аннотирован @Default.");
        }
    }

    /**
     * Формирует строковое представление объекта, включая только поля,
     * разрешённые аннотацией {@link ToString}.
     *
     * <p>Поведение определяется следующим образом:
     * <ul>
     *   <li>Если поле имеет собственную аннотацию {@code @ToString}, используется её {@code value};</li>
     *   <li>Если аннотации на поле нет, но она присутствует на классе — используется значение уровня класса;</li>
     *   <li>Если аннотация отсутствует везде, поле включается по умолчанию ({@link Mode#YES}).</li>
     * </ul>
     * </p>
     *
     * @param obj объект для преобразования
     * @return строковое представление в формате {@code ClassName { field1=value1, field2=value2 }}
     */
    public static String buildToString(Object obj) {
        if (obj == null) return "null";
        Class<?> clazz = obj.getClass();
        StringBuilder sb = new StringBuilder();
        sb.append(clazz.getSimpleName()).append(" { ");

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            ToString fieldAnnotation = field.getAnnotation(ToString.class);
            Mode mode = Mode.YES;

            if (fieldAnnotation != null) {
                mode = fieldAnnotation.value();
            } else if (clazz.isAnnotationPresent(ToString.class)) {
                mode = clazz.getAnnotation(ToString.class).value();
            }

            if (mode == Mode.YES) {
                try {
                    sb.append(field.getName()).append("=").append(field.get(obj)).append(", ");
                } catch (IllegalAccessException e) {
                    sb.append(field.getName()).append("=<ошибка доступа>, ");
                }
            }
        }

        if (sb.length() > 2) {
            sb.setLength(sb.length() - 2);
        }
        sb.append(" }");
        return sb.toString();
    }

    /**
     * Выводит список классов, указанных в аннотации {@link Validate}.
     *
     * <p>Если аннотация отсутствует или массив пуст, выводятся соответствующие сообщения.
     * Метод безопасен к передаче {@code null}.</p>
     *
     * @param clazz класс с аннотацией {@link Validate}
     */
    public static void processValidateAnnotation(Class<?> clazz) {
        if (clazz == null) {
            System.out.println("Передан null вместо класса для @Validate.");
            return;
        }
        if (clazz.isAnnotationPresent(Validate.class)) {
            Validate annotation = clazz.getAnnotation(Validate.class);
            Class<?>[] classes = annotation.value();
            if (classes.length == 0) {
                System.out.println("Список классов в @Validate пуст.");
            } else {
                String names = Arrays.stream(classes)
                        .map(Class::getSimpleName)
                        .reduce((a, b) -> a + ", " + b)
                        .orElse("");
                System.out.println("Классы для валидации: [" + names + "]");
            }
        } else {
            System.out.println("Класс " + clazz.getSimpleName() + " не аннотирован @Validate.");
        }
    }

    /**
     * Выводит значения параметров аннотации {@link Two}.
     *
     * <p>Если аннотация отсутствует, выводится соответствующее сообщение.
     * Метод безопасен к передаче {@code null}.</p>
     *
     * @param clazz класс с аннотацией {@link Two}
     */
    public static void processTwoAnnotation(Class<?> clazz) {
        if (clazz == null) {
            System.out.println("Передан null вместо класса для @Two.");
            return;
        }
        if (clazz.isAnnotationPresent(Two.class)) {
            Two annotation = clazz.getAnnotation(Two.class);
            System.out.println("first = '" + annotation.first() + "', second = " + annotation.second());
        } else {
            System.out.println("Класс " + clazz.getSimpleName() + " не аннотирован @Two.");
        }
    }

    /**
     * Выводит список кешируемых областей из аннотации {@link Cache}.
     *
     * <p>Если аннотация отсутствует или массив пуст, выводятся соответствующие сообщения.
     * Метод безопасен к передаче {@code null}.</p>
     *
     * @param clazz класс с аннотацией {@link Cache}
     */
    public static void processCacheAnnotation(Class<?> clazz) {
        if (clazz == null) {
            System.out.println("Передан null вместо класса для @Cache.");
            return;
        }
        if (clazz.isAnnotationPresent(Cache.class)) {
            Cache annotation = clazz.getAnnotation(Cache.class);
            String[] areas = annotation.value();
            if (areas.length == 0) {
                System.out.println("Кешируемые области: список пуст.");
            } else {
                System.out.println("Кешируемые области: " + Arrays.toString(areas));
            }
        } else {
            System.out.println("Класс " + clazz.getSimpleName() + " не аннотирован @Cache.");
        }
    }

    /**
     * Проверяет корректность значений аннотации {@link Two}.
     *
     * <p>Требования к значениям:
     * <ul>
     *   <li>{@code first} не должен быть {@code null} или пустой строкой;</li>
     *   <li>{@code second} должен быть больше или равен нулю.</li>
     * </ul>
     * </p>
     *
     * @param clazz класс, аннотированный {@link Two}
     * @throws IllegalArgumentException если аннотация отсутствует или значения нарушают требования
     */
    public static void validateTwoAnnotation(Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Класс не может быть null");
        }
        if (!clazz.isAnnotationPresent(Two.class)) {
            throw new IllegalArgumentException("Класс не аннотирован @Two");
        }

        Two annotation = clazz.getAnnotation(Two.class);
        String first = annotation.first();
        int second = annotation.second();

        if (first == null || first.isEmpty()) {
            throw new IllegalArgumentException("Свойство 'first' не должно быть пустым");
        }
        if (second < 0) {
            throw new IllegalArgumentException("Свойство 'second' не должно быть отрицательным");
        }
    }

    /**
     * Точка входа в программу.
     *
     * <p>Демонстрирует работу всех разработанных аннотаций:
     * <ul>
     *   <li>автоматический вызов метода с {@code @Invoke};</li>
     *   <li>чтение класса по умолчанию из {@code @Default};</li>
     *   <li>формирование строкового представления с {@code @ToString};</li>
     *   <li>вывод списка классов из {@code @Validate};</li>
     *   <li>чтение и валидация параметров {@code @Two};</li>
     *   <li>вывод кешируемых областей из {@code @Cache}.</li>
     * </ul>
     * </p>
     *
     * <p>Все операции выводятся в консоль для наглядности.</p>
     *
     * @param args аргументы командной строки (игнорируются)
     */
    public static void main(String[] args) {
        System.out.println(" ЛАБОРАТОРНАЯ РАБОТА №6: АННОТАЦИИ \n");

        // задание 2.2 @Invoke ===
        System.out.println("Задание 2.2 @Invoke");
        InvokeExample demo = new InvokeExample();
        System.out.println("Состояние ДО: wasCalled = " + demo.wasCalled);
        invokeAnnotatedMethods(demo);
        System.out.println("Состояние ПОСЛЕ: wasCalled = " + demo.wasCalled);
        if (demo.wasCalled) {
            System.out.println("метод с @Invoke вызван автоматически!");
        } else {
            System.out.println("метод не был вызван.");
        }
        System.out.println();

        // === Задание 1.1: @Invoke ===
        System.out.println("1.1 @Invoke:");
        InvokeExample invokeObj = new InvokeExample();
        invokeAnnotatedMethods(invokeObj);
        System.out.println();

        // === Задание 1.2: @Default ===
        System.out.println("1.2 @Default:");
        processDefaultAnnotation(DefaultExample.class);
        System.out.println();

        // === Задание 1.3: @ToString ===
        System.out.println("1.3 @ToString:");
        ToStringExample toStringObj = new ToStringExample();
        System.out.println("Строковое представление: " + buildToString(toStringObj));
        System.out.println();

        // === Задание 1.4: @Validate ===
        System.out.println("1.4 @Validate:");
        processValidateAnnotation(ValidateExample.class);
        System.out.println();

        // === Задание 1.5: @Two ===
        System.out.println("1.5 @Two:");
        processTwoAnnotation(TwoExample.class);
        // Демонстрация валидации (без исключения)
        try {
            validateTwoAnnotation(TwoExample.class);
            System.out.println(" @Two в TwoExample корректен.");
        } catch (Exception e) {
            System.out.println(" Ошибка в @Two: " + e.getMessage());
        }
        System.out.println();

        // === Задание 1.6: @Cache ===
        System.out.println("1.6 @Cache:");
        processCacheAnnotation(CacheExample.class);
        System.out.println();
    }
}
