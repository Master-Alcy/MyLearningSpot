package binary_search;

public class SearchA2DMatrixII {

    public static void main(String[] args) {
        SearchA2DMatrixII s2d2 = new SearchA2DMatrixII();
        int[][] matrix = new int[][]{
                {1, 4, 7, 11, 15},
                {2, 5, 8, 12, 19},
                {3, 6, 9, 16, 22},
                {10, 13, 14, 17, 24},
                {18, 21, 23, 26, 30}
        };
        System.out.println(s2d2.searchMatrix(matrix, 5)); // true
        System.out.println(s2d2.searchMatrix(matrix, 20)); // false
    }

    /**
     * This is could be a binary search problem,
     * but this better to be a two pointer problem.
     * currCol and currRow would narrow the possible
     * box where target may be in
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        int rows = matrix.length;
        if (matrix == null || rows == 0 || matrix[0].length == 0) {
            return false;
        }

        int cols = matrix[0].length;
        int currRow = 0, currCol = cols - 1;
        while (currRow < rows && currCol >= 0) {
            int currNum = matrix[currRow][currCol];

            if (target == currNum) {
                return true;
            }
            if (target < currNum) {
                currCol --;
            } else { // target > currNum
                currRow ++;
            }
        }
        return false;
    }
}
