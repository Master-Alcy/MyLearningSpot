package array;

import java.util.Arrays;

public class InsertionSort {

    public static void main(String[] args) {
        int[] arr1 = new int[]{ 1,3,2,4,5,0,2 };
        int[] res = sort1(arr1);
        System.out.println(Arrays.toString(res));
    }

    private static int[] sort1(int[] arr) {
        if (arr != null && arr.length >= 1) {
            for (int i = 1; i < arr.length; i++) {
                int curr = arr[i];

                while(arr[i - 1] >= arr[i]) {
                    arr[i] = arr[i-1];
                    arr[i-1] = curr;
                }
            }
            return arr;
        }
        return arr;
    }
}
