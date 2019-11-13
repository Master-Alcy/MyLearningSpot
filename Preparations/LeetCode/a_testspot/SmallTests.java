package a_testspot;

import java.util.Arrays;
import java.util.Comparator;

@SuppressWarnings("ununsed")
public class SmallTests {

    public static void main(String[] args) {
        // test1();
        // test2();
        // test3();
        // test4();
        test5();
    }

    private static void test5() {
        Integer[] arr = {2, 3, 5, 6, 1, 2, 3, 4, 5, 9, 87};
        Arrays.sort(arr, new Comparator<Integer>(){
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1; // descending
                // return o1 - o2 // ascending
            }
        });
        System.out.println(Arrays.toString(arr));
    }

    private static void test4() {
        int[] arr = {1};
        int end = 1;
        // i < arr.length makes sure no out of bound happen
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] != arr[i - 1]) {
                System.out.println("???");
            }
        }
        System.out.println("222");
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

    private static void test3() {
        System.out.println(2147483647);
        System.out.println(Integer.MAX_VALUE);
    }

}
