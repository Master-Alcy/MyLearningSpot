package binarysearch;

public class SearchInsertPosition {

    public static void main(String[] args) {
        SearchInsertPosition sip = new SearchInsertPosition();
        System.out.println("Should be 2, 1, 4, 0");
        System.out.println(sip.searchInsert2(new int[]{1, 3, 5, 6}, 5));
        System.out.println(sip.searchInsert2(new int[]{1, 3, 5, 6}, 2));
        System.out.println(sip.searchInsert2(new int[]{1, 3, 5, 6}, 7));
        System.out.println(sip.searchInsert2(new int[]{1, 3, 5, 6}, 0));
    }

    public int searchInsert(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int start = 0, end = nums.length - 1;

        while (start <= end) {
            int mid = start + (end - start) / 2;
            int curr = nums[mid];

            if (curr == target) {
                return mid;
            }
            if (curr < target) {
                start = mid + 1;
            } else { // target < curr
                end = mid - 1;
            }
        }
        // check final position, this check is frustrating
        if (start >= nums.length) {
            return start;
        }
        if (nums[start] < target) {
            return start + 1;
        } else {
            return start;
        }
    }

    public int searchInsert2(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int start = 0, end = nums.length - 1;

        while (start < end) {
            int mid = start + (end - start) / 2;
            int curr = nums[mid];

            if (curr == target) {
                return mid;
            }
            if (curr < target) {
                start = mid + 1;
            } else { // target < curr
                end = mid - 1;
            }
        }
        System.out.println("Start: " + start + ", End: " + end);

        if (nums[start] < target) {
            return start + 1;
        } else {
            return start;
        }
    }
}
