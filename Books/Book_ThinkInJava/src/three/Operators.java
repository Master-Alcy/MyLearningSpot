package three;

public class Operators {

    public static void main(String[] args) {
        test1();
    }

    private static void test1() {
        final boolean zero = false;
        final boolean one = true;
        System.out.println("1: " + (zero & one));
        System.out.println("2: " + (zero | one));
        System.out.println("3: " + (zero ^ one ^ zero));
        final byte by = 60; // 0011 1100
        System.out.println("4: " + ~by); // 1100 0011
        System.out.println("5: " + (by << 2)); // 1111 0000
        System.out.println("6: " + (by >> 2)); // 1111
        System.out.println("7: " + (by >>> 2)); // 0000 1111
        System.out.println("8: " + (true ? "111" : "222")); // 0000 1111
    }
}