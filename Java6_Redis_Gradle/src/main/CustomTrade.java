import java.io.Serializable;

public class CustomTrade implements Serializable {
    private static final long serialVersionUID = 1234L;
    private String name;
    private int age;

    CustomTrade(String aName, int anAge) {
        this.name = aName;
        this.age = anAge;
    }

    public void something() {
        System.out.println("This is doing something crazy");
    }

    @Override
    public String toString() {
        return "CustomTrade: Name is " + name + ", age is " + age;
    }
}
