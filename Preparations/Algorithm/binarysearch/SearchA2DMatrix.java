package binarysearch;

public class SearchA2DMatrix {

    public static void main(String[] args) {
        SearchA2DMatrix s2d = new SearchA2DMatrix();
        int[][] matrix = new int[][]{
                {1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 50}};
        System.out.println(s2d.searchMatrix2(matrix, 3));
    }

    public boolean searchMatrix2(int[][] matrix, int target) {
        int rows = matrix.length;
        if (matrix == null || rows == 0 || matrix[0].length == 0) {
            return false;
        }
        int rowTop = 0, rowBottom = rows - 1, cols = matrix[0].length;

        while (rowTop <= rowBottom) {
            int mid = rowTop + (rowBottom - rowTop) / 2;
            int currHead = matrix[mid][0], currTail = matrix[mid][cols - 1];

            // wrote target >= currTail before. careful!
            if (currHead <= target && target <= currTail) {
                // This can make sure target is in this line or not exist
                return searchArray(matrix[mid], target);
            }
            if (target < currHead) {
                rowBottom = mid - 1;
            } else {
                rowTop = mid + 1;
            }
        }
        // if it's here, then no row match for target.
        return false;
    }

    /**
     * AC in one try, O(log mn)
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        int rows = matrix.length;
        if (matrix == null || rows == 0 || matrix[0].length == 0) {
            return false;
        }
        int rowTop = 0, rowBottom = rows - 1;

        while (rowTop + 1 < rowBottom) {
            int mid = rowTop + (rowBottom - rowTop) / 2;
            int currHead = matrix[mid][0], currTail = matrix[mid][matrix[0].length - 1];
            if (currHead == target) {
                return true;
            }
            if (target < currHead) {
                // there must have prev row
                rowBottom = mid - 1;
            } else {
                // target could be in this row or next row
                rowTop = mid;
            }
        }
        // target could be in rowTop row or rowBottom row
        // we hope it's in rowTop
        if (searchArray(matrix[rowTop], target)) {
            return true;
        }
        if (searchArray(matrix[rowBottom], target)) {
            return true;
        }
        return false;
    }

    private boolean searchArray(int[] arr, int target) {
        int start = 0, end = arr.length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (arr[mid] == target) {
                return true;
            }

            if (arr[mid] > target) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        if (arr[start] == target || arr[end] == target) {
            return true;
        }
        return false;
    }
}
