package riskval.prepare;

public class myInterfacesAndAbstractClasses {

    private interface IDance { // what the object can do
        boolean canDance = false;
        void dance();
    }

    private class Person {
        public String toString() {
            return "I'm a person.";
        }
        public void eat() {
        }
        public void speak() {
        }
    }

    /**
     * A Promise to provide the state or functionality.
     * No Info to share
     * What can object do
     */
    private interface NonPerson {
        void something();
    }

    /**
     * Have shared state or functionality
     * Share its functionality or state to all descendants
     * What an object is
     */
    private abstract class Animal {
        public String toString() {
            return "This is an animal.";
        }
        void eat(){
            System.out.println("Animal Eating.");
        }
        abstract void hunt();

        abstract void something();
    }

    private class Dancer extends Person implements IDance {

        @Override
        public void dance() {
            System.out.println("Dancing.");
        }
    }

    private abstract class Snake extends Animal implements IDance {
        public void Zeez() {
            System.out.println("Here is Snake");
        }
    }

    /**
     * GlassSnake is a Snake, it can do IDance and NonPerson moves.
     */
    private class GlassSnake extends Snake implements IDance, NonPerson {

        @Override
        public void hunt() {
            System.out.println("Implement Hunting.");
        }

        @Override
        public void something() {
            System.out.println("GlassSnake Doing Something");
        }

        @Override
        public void dance() {
            System.out.println("GlassSnake Dancing");
        }
    }

    public static void main(String[] args) {
        myInterfacesAndAbstractClasses nr = new myInterfacesAndAbstractClasses();

        IDance dancer = nr.new Dancer();
        System.out.println(dancer.canDance);
        dancer.dance();
        System.out.println(dancer.toString());

        System.out.println();

        Animal snake = nr.new GlassSnake();
        snake.eat();
        snake.hunt();
        snake.something();
        System.out.println(snake.toString());

        System.out.println();

        GlassSnake gs = nr.new GlassSnake();
        gs.dance();
        gs.Zeez();
        gs.something();
        System.out.println(gs.toString());
    }
}