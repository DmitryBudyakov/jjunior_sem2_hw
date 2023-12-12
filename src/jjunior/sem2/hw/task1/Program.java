package jjunior.sem2.hw.task1;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
    Задача 1:
    Создайте абстрактный класс "Animal" с полями "name" и "age".
    Реализуйте два класса-наследника от "Animal" (например, "Dog" и "Cat") с уникальными полями и методами.
    Создайте массив объектов типа "Animal" и с использованием Reflection API выполните следующие действия:
    - Выведите на экран информацию о каждом объекте.
    - Вызовите метод "makeSound()" у каждого объекта, если такой метод присутствует.
 */
public class Program {
    public static void main(String[] args) throws IllegalAccessException {
        Animal dog1 = new Dog("Шарик", 5, 'm');
        Animal dog2 = new Dog("Джульбарс", 2, 'm', "Овчарка");
        Animal cat1 = new Cat("Мурзик", 1, 'm');
        Animal cat2 = new Cat("Муся", 3, 'f', "Сибирская");

        List<Animal> animals = new ArrayList<>();
        animals.add(dog1);
        animals.add(dog2);
        animals.add(cat1);
        animals.add(cat2);

        int objectCount = 1;
        for (Animal animal : animals) {
            System.out.printf("\n----- Object #%s Info -----\n", objectCount++);
            classDisplayInfo(animal);

            System.out.println("Make Sound:");
            if (isMethodExist(animal, "makeSound")) {
                animal.makeSound();
            } else {
                System.out.println("No sound");
            }
        }
    }

    /**
     * Показывает общую информацию об объекте
     *
     * @param obj
     * @throws IllegalAccessException
     */
    static void classDisplayInfo(Object obj) throws IllegalAccessException {

        Class<?> objectClass = obj.getClass();  // Instance class
        Class<?> objectSuperClass = obj.getClass().getSuperclass(); // Superclass

        StringBuilder classInfo = new StringBuilder();
        classInfo.append("Simple Class name: ").append(objectClass.getSimpleName()).append("\n");
        classInfo.append("Full Class name: ").append(objectClass.getName()).append("\n");

        // Superclass info
        if (objectSuperClass != null) {
            classInfo.append("Superclass name: ").append(objectSuperClass.getName()).append("\n");
            classInfo.append(displayClassFieldsInfo(obj, true));

            // Constructors info:
            classInfo.append(" Constructors info:").append("\n");
            classInfo.append(displayClassConstructorsInfo(objectSuperClass));

            // Methods info:
            classInfo.append(" Methods info:").append("\n");
            classInfo.append(displayClassMethodsInfo(objectSuperClass));

        } else {
            classInfo.append("Superclass name: ").append("None").append("\n");
        }


        // Instance class info:
        classInfo.append("Instance class name: ").append(objectClass.getName()).append("\n");
        classInfo.append(displayClassFieldsInfo(obj, false));
        classInfo.append(" Constructors info:").append("\n");
        classInfo.append(displayClassConstructorsInfo(objectClass));
        classInfo.append(" Methods info:").append("\n");
        classInfo.append(displayClassMethodsInfo(objectClass));

        System.out.println(classInfo);

    }

    /**
     * Метод возвращает информацию о полях объекта в зависимости от того объект Superclass или нет
     *
     * @param obj
     * @param isSuperclass
     * @return
     * @throws IllegalAccessException
     */
    static String displayClassFieldsInfo(Object obj, boolean isSuperclass) throws IllegalAccessException {
        StringBuilder fieldsInfo = new StringBuilder();
        fieldsInfo.append(" Fields info: ").append("\n");
        Class<?> objectClass;
        if (isSuperclass) {
            objectClass = obj.getClass().getSuperclass();
        } else {
            objectClass = obj.getClass();
        }
        for (Field field : objectClass.getDeclaredFields()) {
            field.setAccessible(true);
            fieldsInfo.append("  Field name: ").append(field.getName())
                    .append(", Value: ").append(field.get(obj))
                    .append(", Type: ").append(field.getType().getName())
                    .append(", Modifier: ")
                    .append(field.getModifiers() != 0 ?
                            Modifier.toString(field.getModifiers()) : "None").append("\n");
        }

        return fieldsInfo.toString();

    }

    /**
     * Метод возвращает информацию о конструкторах объекта
     *
     * @param clazz
     * @return
     */
    static String displayClassConstructorsInfo(Class<?> clazz) {
        StringBuilder constructorsInfo = new StringBuilder();
        int count = 1;
        for (Constructor constructor : clazz.getConstructors()) {
            constructorsInfo.append("  Contructor #").append(count++).append(": ")
                    .append(" Number of parameters: ").append(constructor.getParameterCount())
                    .append(", Types: ").append(Arrays.toString(constructor.getParameterTypes()))
                    .append("\n");
        }
        return constructorsInfo.toString();

    }

    /**
     * Метод возвращает информацию о методах объекта
     *
     * @param clazz
     * @return
     */
    static String displayClassMethodsInfo(Class<?> clazz) {
        StringBuilder methodsInfo = new StringBuilder();
        for (Method method : clazz.getDeclaredMethods()) {
            methodsInfo.append("  Method name: ").append(method.getName())
                    .append(", Returned type: ").append(method.getReturnType())
                    .append(", Parameter types: ").append(Arrays.toString(method.getParameterTypes()))
                    .append("\n");
        }
        return methodsInfo.toString();
    }

    /**
     * Метод проверяет есть ли объекта указанный метод
     *
     * @param obj
     * @param methodName
     * @return
     */
    static boolean isMethodExist(Object obj, String methodName) {
        // makeSound check:
        Class<?> objClass = obj.getClass();
        for (Method method : objClass.getDeclaredMethods()) {
            if (method.getName().equals(methodName)) {
                return true;
            }
        }
        return false;
    }

}
