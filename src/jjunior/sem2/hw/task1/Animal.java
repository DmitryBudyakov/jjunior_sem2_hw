package jjunior.sem2.hw.task1;

abstract class Animal {

    private String name;

    private int age;

    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }


    public abstract void makeSound();
}
