package binary_search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class FindKClosestElements {

    public static void main(String[] args) {
        FindKClosestElements fkce = new FindKClosestElements();
        int[] arr = {0, 0, 1, 2, 3, 3, 4, 7, 7, 8};
        List<Integer> list = fkce.findClosestElements(arr, 3, 5);
        // 3, 3, 4
        System.out.println(Arrays.toString(list.toArray()));
    }

    /**
     * Smart way. I don't think I can remember this without longtime debugging
     */
    public List<Integer> findClosestElements2(int[] arr, int k, int target) {
        int start = 0, end = arr.length - k; // smart way

        while (start < end) {
            int mid = start + (end - start) / 2;
            int curr = arr[mid], kcurr = arr[mid + k];

            if (2 * target > kcurr + curr) { // mhm
                start = mid + 1;
            } else {
                end = mid;
            }
        }

        List<Integer> results = new ArrayList<Integer>();
        for (int i = start; i < start + k; i++) {
            results.add(arr[i]);
        }

        return results;
    }

    /**
     * Slow first try
     */
    public List<Integer> findClosestElements(int[] arr, int k, int target) {
        List<Integer> result = new ArrayList<>();
        // k > 0 && k < arr.length
        if (arr == null || arr.length == 0) {
            return result;
        }
        if (arr.length == 1) {
            result.add(arr[0]);
            return result;
        }
        int start = 0, end = arr.length - 1, pivot = 0;
        // no target return -1, Arrays.binarySearch(arr, start, end, target);

        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            int curr = arr[mid];

            if (curr < target) {
                start = mid;
            } else { // curr >= target
                pivot = mid;
                end = mid;
            }
        }

        if (arr[pivot] != target) {
            if (Math.abs(arr[start] - target) <= Math.abs(arr[end] - target)) {
                pivot = start; // prefer smaller value
            } else {
                pivot = end;
            }
        } // pivot done

        // two pointer to find closest elements
        // length here at least 2
        if (pivot + 1 == arr.length) {
            end = pivot;
            start = pivot - 1;
        } else {
            start = pivot;
            end = pivot + 1;
        }

        int count = 0,
                leftDiff = Math.abs(arr[start] - target),
                rightDiff = Math.abs(arr[end] - target);

        while (count < k) {
            if (leftDiff <= rightDiff) {
                result.add(arr[start]); // add current start to result

                if (start - 1 >= 0) {
                    start--; // move to next start
                    leftDiff = Math.abs(arr[start] - target); // re-calc
                } else { // run out of left, start == 0
                    leftDiff = Integer.MAX_VALUE; // not gona be selected again
                }
            } else {
                result.add(arr[end]);

                if (end + 1 >= arr.length) {
                    rightDiff = Integer.MAX_VALUE; // Note, they won't be max_value at the same time
                } else {
                    end++;
                    rightDiff = Math.abs(arr[end] - target);
                }
            }
            count++;
        }

        result.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });

        return result;
    }
}
