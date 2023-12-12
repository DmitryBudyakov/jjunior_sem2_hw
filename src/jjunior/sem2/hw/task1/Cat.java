package jjunior.sem2.hw.task1;

public class Cat extends Animal{

    private char sex;
    private String breed;

    public Cat(String name, int age, char sex) {
        super(name, age);
        this.sex = sex;
        this.breed = "Бездомная";
    }

    public Cat(String name, int age, char sex, String breed) {
        super(name, age);
        this.sex = sex;
        this.breed = breed;
    }

    public void play(){
        System.out.println(getName() + " играет.");
    }

    @Override
    public void makeSound() {
        System.out.println(getName() + " мяукает.");
    }
}
