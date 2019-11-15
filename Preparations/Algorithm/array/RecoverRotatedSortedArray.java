package array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description
 * Given a rotated sorted array, recover it to sorted array in-place.
 * <p>
 * For example, the original array is [1,2,3,4], The rotated array
 * of it can be [1,2,3,4], [2,3,4,1], [3,4,1,2], [4,1,2,3]
 * <p>
 * Example1:
 * [4, 5, 1, 2, 3] -> [1, 2, 3, 4, 5]
 * <p>
 * Example2:
 * [6,8,9,1,2] -> [1,2,6,8,9]
 * <p>
 * Challenge
 * In-place, O(1) extra space and O(n) time.
 */
public class RecoverRotatedSortedArray {

    /**
     * Failed at 1,1,1,1,1,1,1,-1,1,1,1,1,1 with binary search
     *
     * @param nums: An integer array
     * @return: nothing
     */
    public void recoverRotatedSortedArray(List<Integer> nums) {
        if (nums == null || nums.size() == 0) {
            return;
        }

        int start = 0, end = nums.size() - 1, base = nums.get(end);
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            int curr = nums.get(mid);
            // ...curr...base
            if (curr < base) {
                end = mid;
            } else if (curr > base) {
                start = mid;
            } else {

            }
        }
        int pivot;
        if (nums.get(start) <= base) {
            pivot = start;
        } else if (nums.get(end) <= base) {
            pivot = end;
        } else {
            pivot = 0;
        }
        System.out.println("Pivot: " + pivot);

        List<Integer> result = new ArrayList<>();
        result.addAll(nums);
        nums.clear();
        nums.addAll(result.subList(pivot, result.size()));
        nums.addAll(result.subList(0, pivot));
    }

    /**
     * @param nums: The rotated sorted array
     * @return: The recovered sorted array
     */
    private void reverse(List<Integer> nums, int start, int end) {
        for (int i = start, j = end; i < j; i++, j--) {
            int temp = nums.get(i);
            nums.set(i, nums.get(j));
            nums.set(j, temp);
        }
    }

    /**
     * 3 step to reverse, isn't this brute force?
     */
    public void recoverRotatedSortedArray2(List<Integer> nums) {
        if (nums == null || nums.size() == 0) {
            return;
        }

        for (int index = 0; index < nums.size() - 1; index++) {
            // find pivot => 4, 5, 1, 2, 3 => pivot: 5, => flip 4, 5 => 5, 4
            // flip 1, 2, 3 => 3, 2, 1 => flip 5, 4, 3, 2, 1 => 1, 2, 3, 4, 5
            if (nums.get(index) > nums.get(index + 1)) {
                reverse(nums, 0, index);
                reverse(nums, index + 1, nums.size() - 1);
                reverse(nums, 0, nums.size() - 1);
                return;
            }
        }
    }

    public static void main(String[] args) {
        RecoverRotatedSortedArray rrsa = new RecoverRotatedSortedArray();
        int[] arr = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        List<Integer> list = Arrays.stream(arr).boxed().collect(Collectors.toList());
        rrsa.recoverRotatedSortedArray2(list);
        System.out.println(Arrays.toString(list.toArray()));
    }
}
