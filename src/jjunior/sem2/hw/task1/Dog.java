package jjunior.sem2.hw.task1;

public class Dog extends Animal{

    private char sex;       // Male (M) / Female (F)
    private String breed;   // порода

    public Dog(String name, int age, char sex) {
        super(name, age);
        this.sex = sex;
        this.breed = "Дворняжка";
    }

    public Dog(String name, int age, char sex, String breed) {
        super(name, age);
        this.sex = sex;
        this.breed = breed;
    }

    public void guard(){
        System.out.println(getName() + " охраняет.");
    }

    @Override
    public void makeSound() {
        System.out.println(getName() + " лает.");
    }
}
