/*
 * @lc app=leetcode id=702 lang=java
 *
 * [702] Search in a Sorted Array of Unknown Size
 *
 * https://leetcode.com/problems/search-in-a-sorted-array-of-unknown-size/description/
 *
 * algorithms
 * Medium (62.60%)
 * Likes:    205
 * Dislikes: 17
 * Total Accepted:    15.7K
 * Total Submissions: 25.1K
 * Testcase Example:  '[-1,0,3,5,9,12]\n9'
 *
 * Given an integer array sorted in ascending order, write a function to search
 * target in nums.  If target exists, then return its index, otherwise return
 * -1. However, the array size is unknown to you. You may only access the array
 * using an ArrayReader interface, where ArrayReader.get(k) returns the element
 * of the array at index k (0-indexed).
 * 
 * You may assume all integers in the array are less than 10000, and if you
 * access the array out of bounds, ArrayReader.get will return 2147483647.
 * 
 * 
 * 
 * Example 1:
 * 
 * 
 * Input: array = [-1,0,3,5,9,12], target = 9
 * Output: 4
 * Explanation: 9 exists in nums and its index is 4
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: array = [-1,0,3,5,9,12], target = 2
 * Output: -1
 * Explanation: 2 does not exist in nums so return -1
 * 
 * 
 * 
 * Note:
 * 
 * 
 * You may assume that all elements in the array are unique.
 * The value of each element in the array will be in the range [-9999, 9999].
 * 
 * 
 */

// @lc code=start
class Solution {
    public int search(ArrayReader reader, int target) {
        if (reader.get(0) == Integer.MAX_VALUE) {
            return -1;
        } else if (reader.get(0) == target) {
            return 0;
        } else if (reader.get(1) == Integer.MAX_VALUE) {
            return reader.get(1) == target ? 1 : -1;
        }
        
        int low = 0;
        int high = 1;

        while(reader.get(high) != Integer.MAX_VALUE) {
            int curr = reader.get(high);
            int next = reader.get(high * 2);

            if (curr == target) {
                return high;
            } else if (next > target) {
                low = high;
                high = high * 2;
                break;
            } else {
                low = high;
                high = high * 2;
            }
        }

        while (low + 1 < high) {
            int mid = low + (high - low) / 2;
            int midNum = reader.get(mid);

            if (midNum == target) {
                return mid;
            } else if (midNum > target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        if (reader.get(low) == target) {
            return low;
        }
        if (reader.get(high) == target) {
            return high;
        }

        return -1;
    }
}
// @lc code=end

