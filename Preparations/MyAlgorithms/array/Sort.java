package array;

import java.util.Arrays;

public class Sort {

    public static void main(String[] args) {
        int[] arr1 = new int[]{ 6,3,2,4,5,0,2,1 };
        int[] res = mergeSort(arr1);
        System.out.println(Arrays.toString(res));
    }

    private static int[] insertionSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return arr;
        }

        for (int i = 1; i < arr.length; i++) {
            int index = i;
            while(index > 0 && arr[index - 1] > arr[index]) {
                int prev = arr[index - 1];
                arr[index - 1] = arr[index];
                arr[index] = prev;
                index--;
            }
        }

        return arr;
    }

    private static int[] mergeSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return arr;
        }

        divide(arr, 0, arr.length - 1);
        return arr;
    }

    private static void divide(int[] arr, int start, int end) {
        if (start >= end) {
            return;
        }

        int mid = start + (end - start) / 2;

        divide(arr, start, mid);
        divide(arr, mid + 1, end);
        merge(arr, start, mid, mid + 1, end);
    }

    private static void merge(int[] arr, int leftHead, int leftTail, int rightHead, int rightTail) {
        int[] result = new int[rightTail - leftHead + 1];
        int index = 0;
        int head = leftHead;
        while(leftHead <= leftTail || rightHead <= rightTail) {

            if (rightHead <= rightTail && (leftHead > leftTail || arr[leftHead] > arr[rightHead])) {
                result[index] = arr[rightHead];
                index++;
                rightHead++;
            }

            if (leftHead <= leftTail && (rightHead > rightTail || arr[leftHead] <= arr[rightHead])) {
                result[index] = arr[leftHead];
                index++;
                leftHead++;
            }
        }

        index = 0;
        for (int i = head; i <= rightTail; i++) {
            arr[i] = result[index];
            index++;
        }
    }
}
