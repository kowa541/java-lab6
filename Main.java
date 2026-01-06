import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Главный класс лабораторной работы №6.
 * Демонстрирует работу всех аннотаций и их обработчиков.
 */
public class Main {

    /**
     * Обрабатывает объект, вызывая все методы, помеченные аннотацией @Invoke.
     *
     * @param obj объект, методы которого необходимо проверить и вызвать
     * @throws IllegalArgumentException если объект равен null
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
     * Выводит имя класса, указанного в аннотации @Default.
     *
     * @param clazz класс, аннотированный @Default
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
     * Формирует строковое представление объекта, учитывая только поля,
     * помеченные @ToString со значением Mode.YES.
     *
     * @param obj объект для преобразования
     * @return строковое представление объекта
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
     * Выводит список классов, указанных в аннотации @Validate.
     *
     * @param clazz класс с аннотацией @Validate
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
     * Выводит значения свойств аннотации @Two.
     *
     * @param clazz класс с аннотацией @Two
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
     * Выводит список кешируемых областей из аннотации @Cache.
     *
     * @param clazz класс с аннотацией @Cache
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
     * Проверяет корректность значений аннотации @Two.
     * Требования:
     * - first не должен быть null или пустой строкой
     * - second должен быть >= 0
     *
     * @param clazz класс, аннотированный @Two
     * @throws IllegalArgumentException если значения не соответствуют требованиям
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
     * Демонстрирует работу всех аннотаций, включая доказательство работы @Invoke
     * и пример валидации @Two.
     *
     * @param args аргументы командной строки (не используются)
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