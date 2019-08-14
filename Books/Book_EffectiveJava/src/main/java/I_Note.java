
public class I_Note {

    private I_Note() {
        System.out.println(this.getClass().getSimpleName());
    }
    // 1
    private static final I_Note INSTANCE1 = new I_Note();
    public static I_Note getInstance1() {
        return INSTANCE1;
    }
    // 2
    private volatile static I_Note instance2;
    public static I_Note getInstance2() {
        if (instance2 == null) {
            synchronized (I_Note.class) {
                if (instance2 == null) {
                    instance2 = new I_Note();
                }
            }
        }
        return instance2;
    }
    // 3 rec
    private static class InstanceHolder {
        private static final I_Note INSTANCE3 = new I_Note();
    }
    public static final I_Note getInstance3() {
        return InstanceHolder.INSTANCE3;
    }
    // 4 Effective Java Rec
    public enum EnumSingleton {
        INSTANCE;
        private EnumSingleton() {
            System.out.println(this.getClass().getSimpleName());
        }
    }

    public static void main(String[] args) {
        I_Note ins1 = I_Note.getInstance1();
        I_Note ins2 = I_Note.getInstance2();
        I_Note ins3 = I_Note.getInstance3();
        EnumSingleton something = EnumSingleton.INSTANCE;
    }
}
