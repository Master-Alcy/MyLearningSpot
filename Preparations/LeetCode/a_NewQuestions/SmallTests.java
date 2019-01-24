package a_NewQuestions;

public class SmallTests {

    public static void main(String[] args) {
        // test1();
        test2();
    }

    private static void test2() {
        for (int i = 0; i < 5; i++)
            System.out.println(i);

        System.out.println();

        for (int i = 0; i < 5; ++i)
            System.out.println(i);
    }

    private static void test1() {
        char[][] grid1 = {
                {'1', '1', '1', '1', '0'},
                {'1', '1', '0', '1', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '0', '0', '0'}
        };
        System.out.println(grid1.length);
        System.out.println(grid1[0].length);
        int i = 6;
        if (i < 3 && grid1[i][0] == '0') {
            System.out.println(i);
        }
    }

}
