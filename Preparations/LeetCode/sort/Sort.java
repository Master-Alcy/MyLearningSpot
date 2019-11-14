package sort;

import java.util.Arrays;

public class Sort {

    public static void main(String[] args) {
        int[] arr1 = new int[]{ 6,3,2,4,5,0,2,1 };
        int[] res = quickSort(arr1);
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

    private static int[] quickSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return arr;
        }

        quickSort(arr, 0, arr.length - 1);

        return arr;
    }

    /**
     * @param head begin of this sub array
     * @param tail end of this sub array
     */
    private static void quickSort(int[] arr, int head, int tail) {
        if (head >= tail) {
            return;
        }
        int pivotIndex = findPivotIndex(head, tail);
        int index = partition(arr, head, tail, pivotIndex);
        quickSort(arr, head, index - 1);
        quickSort(arr, index + 1, tail);
    }

    private static int partition(int[] arr, int head, int tail, int pivotIndex) {
        int i = head, j = tail - 1;
        int pivotNum = arr[pivotIndex];
        arr[pivotIndex] = arr[tail];
        arr[tail] = pivotNum;
        // ready the head and tail pointer
        while (i <= j) {
            if (arr[i] <= pivotNum) {
                i++;
            } else if (arr[j] > pivotNum) {
                j--;
            } else { // arr[i] > pivotNum, arr[j] <= pivotNum
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }
        } // i > j
        int temp = arr[i];
        arr[i] = pivotNum;
        arr[tail] = temp;
        return i;
    }

    private static int findPivotIndex(int head, int tail) {
        return (int)(Math.random() * (tail - head )) + head;
    }
}
