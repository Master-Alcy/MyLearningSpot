package binary.search;

public class SearchInRotatedSortedArrayII {

    public static void main(String[] args) {
        SearchInRotatedSortedArrayII si2 = new SearchInRotatedSortedArrayII();
        boolean res = si2.search(new int[]{1, 1, 3, 1}, 3);
        System.out.println(res);
    } // Note that element could dup in this one

    /**
     * Optimal solution from discussion
     */
//    public boolean search2(int[] nums, int target) {
//        // note here end is initialized to len instead of (len-1)
//        int start = 0, end = nums.length;
//        while (start < end) {
//            int mid = start + (end - start) / 2;
//
//            if (nums[mid] == target) {
//                return true;
//            }
//
//            if (nums[mid] > nums[start]) {
//                // nums[start..mid] is sorted
//                // check if target in left half
//                if (target < nums[mid] && target >= nums[start]) {
//                    end = mid;
//                } else {
//                    start = mid + 1;
//                }
//            } else if (nums[mid] < nums[start]) {
//                // nums[mid..end] is sorted
//                // check if target in right half
//                if (target > nums[mid] && target < nums[start]) {
//                    start = mid + 1;
//                } else {
//                    end = mid;
//                }
//            } else {
//                // have no idea about the array, but we can exclude
//                // nums[start] because nums[start] == nums[mid]
//                start++;
//            }
//        }
//        return false;
//    }

    /**
     * Yeeeeeesssss. Optimal Solution with JiuZhang Template
     */
    public boolean search3(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        // note here end is initialized to len instead of (len-1)
        int start = 0, end = nums.length - 1;

        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) {
                return true;
            }

            if (nums[mid] > nums[start]) {
                // nums[start..mid] is sorted
                // check if target in left half
                if (target < nums[mid] && target >= nums[start]) {
                    end = mid;
                } else {
                    start = mid;
                }
            } else if (nums[mid] < nums[start]) {
                // nums[mid..end] is sorted
                // check if target in right half
                if (target > nums[mid] && target < nums[start]) {
                    start = mid;
                } else {
                    end = mid;
                }
            } else {
                // nums[mid] == nums[start]
                // have no idea about the array, but we can exclude
                // nums[start] because nums[start] == nums[mid]
                start++;
            }
        }

        if (nums[start] == target || nums[end] == target) {
            return true;
        }

        return false;
    }

    /**
     * Worst-Case O(n), Brute-Force is much better
     * This is my WRONG WRONG WRONG first approach
     */
    public boolean search(int[] nums, int target) {
        int N = nums.length;
        if (nums == null || N == 0) {
            return false;
        }
        int start = 0, end = N - 1;
        int pivot = 0, base = nums[end];
        // find pivot
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            // curr = nums[mid]
            // prev = nums[mid - 1]
            // if (prev > curr) {
            // pivot = curr
            // break;}
            // if (curr => O(n)
            // To make sure the position of pivot, need O(n)
            // Then just brute-force
            if (nums[mid] <= base) {
                end = mid;
            } else {
                start = mid;
            }
        }
        // set pivot to [peak, pivot, pivot + 1]
        if (nums[start] <= base) {
            // for straight line, pivot = 0
            pivot = start;
        } else if (nums[end] <= base) {
            pivot = end;
        }
        // set new start and end, nothing else
        if (target <= base) {
            end = N - 1;
            start = pivot;
        } else if (target > base && pivot == 0) {
            // straight line case while target out of bound
            return false;
        } else {
            start = 0;
            System.out.println(pivot);
            end = pivot - 1;
        }
        // find target
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;

            if (nums[mid] == target) {
                return true;
            }
            if (target < nums[mid]) {
                end = mid;
            } else {
                start = mid;
            }
        }
        // check where is the target
        if (nums[start] == target || nums[end] == target) {
            return true;
        }
        // else means no target
        return false;
    }
}
