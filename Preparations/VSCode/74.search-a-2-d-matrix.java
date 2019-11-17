/*
 * @lc app=leetcode id=74 lang=java
 *
 * [74] Search a 2D Matrix
 */

// @lc code=start
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }

        int rowCount = matrix.length;
        int colCount = matrix[0].length;
        int low = 0;
        int high = rowCount * colCount - 1;

        while(low + 1 < high) {
            int mid = low + (high - low) / 2;
            int row = mid / colCount;
            int col = mid % colCount;

            if (matrix[row][col] == target) {
                return true;
            } else if (matrix[row][col] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        if (matrix[low / colCount][low % colCount] == target 
        || matrix[high / colCount][high % colCount] == target) {
            return true;
        }
        
        return false;
    }
}
// @lc code=end

