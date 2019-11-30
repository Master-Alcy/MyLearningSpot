import java.util.LinkedList;
import java.util.Queue;

/*
 * @lc app=leetcode id=542 lang=java
 *
 * [542] 01 Matrix
 *
 * https://leetcode.com/problems/01-matrix/description/
 *
 * algorithms
 * Medium (37.62%)
 * Likes:    919
 * Dislikes: 92
 * Total Accepted:    60.5K
 * Total Submissions: 160.8K
 * Testcase Example:  '[[0,0,0],[0,1,0],[0,0,0]]'
 *
 * Given a matrix consists of 0 and 1, find the distance of the nearest 0 for
 * each cell.
 * 
 * The distance between two adjacent cells is 1.
 * 
 * 
 * 
 * Example 1: 
 * 
 * 
 * Input:
 * [[0,0,0],
 * ⁠[0,1,0],
 * ⁠[0,0,0]]
 * 
 * Output:
 * [[0,0,0],
 * [0,1,0],
 * [0,0,0]]
 * 
 * 
 * Example 2: 
 * 
 * 
 * Input:
 * [[0,0,0],
 * ⁠[0,1,0],
 * ⁠[1,1,1]]
 * 
 * Output:
 * [[0,0,0],
 * ⁠[0,1,0],
 * ⁠[1,2,1]]
 * 
 * 
 * 
 * 
 * Note:
 * 
 * 
 * The number of elements of the given matrix will not exceed 10,000.
 * There are at least one 0 in the given matrix.
 * The cells are adjacent in only four directions: up, down, left and right.
 * 
 * 
 */

// @lc code=start
class Solution {

    public int[][] updateMatrix(int[][] matrix) {
        int rowMax = matrix.length;
        int colMax = matrix[0].length;

        Queue<int[]> queue = new LinkedList<>();
        for (int row = 0; row < rowMax; row++) {
            for (int col = 0; col < colMax; col++) {
                if (matrix[row][col] == 0) {
                    queue.offer(new int[] { row, col });
                } else {
                    matrix[row][col] = Integer.MAX_VALUE;
                }
            }
        }

        int[][] dirs = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };

        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int currRow = cell[0];
            int currCol = cell[1];

            for (int[] dir : dirs) {
                int newRow = currRow + dir[0];
                int newCol = currCol + dir[1];
                // if out of bound or new distance is smaller or equal to curr distance + 1
                if (newRow < 0 || newRow >= rowMax || newCol < 0 || newCol >= colMax
                        || matrix[newRow][newCol] <= matrix[currRow][currCol] + 1) {
                    continue;
                }
                queue.add(new int[] { newRow, newCol });
                matrix[newRow][newCol] = matrix[currRow][currCol] + 1;
            }
        }

        return matrix;
    }

    public int[][] updateMatrix2(int[][] matrix) {
        int rowNum = matrix.length;
        int colNum = matrix[0].length;
        int[][] result = new int[rowNum][colNum];

        for (int row = 0; row < rowNum; row++) {
            for (int col = 0; col < colNum; col++) {
                int curr = matrix[row][col];
                if (curr == 1) {
                    boolean[][] visited = new boolean[rowNum][colNum];
                    int distance = dfs(matrix, visited, result, row, col, rowNum, colNum);
                    result[row][col] = distance;
                }
            }
        }

        return result;
    }

    private int dfsHelper(int[][] matrix, boolean[][] visited, int row, int col, int rowNum, int colNum) {
        int curr = matrix[row][col];
        if (curr == 0) {
            return 0;
        } else {
            visited[row][col] = true;
        }
        int minDistance = Integer.MAX_VALUE / 2;
        if (row - 1 >= 0 && !visited[row - 1][col]) {
            minDistance = Math.min(dfsHelper(matrix, visited, row - 1, col, rowNum, colNum), minDistance);
            if (minDistance == 0) {
                visited[row][col] = false;
                return 1;
            }
        }
        if (row + 1 < rowNum && !visited[row + 1][col]) {
            minDistance = Math.min(dfsHelper(matrix, visited, row + 1, col, rowNum, colNum), minDistance);
            if (minDistance == 0) {
                visited[row][col] = false;
                return 1;
            }
        }
        if (col - 1 >= 0 && !visited[row][col - 1]) {
            minDistance = Math.min(dfsHelper(matrix, visited, row, col - 1, rowNum, colNum), minDistance);
            if (minDistance == 0) {
                visited[row][col] = false;
                return 1;
            }
        }
        if (col + 1 < colNum && !visited[row][col + 1]) {
            minDistance = Math.min(dfsHelper(matrix, visited, row, col + 1, rowNum, colNum), minDistance);
            if (minDistance == 0) {
                visited[row][col] = false;
                return 1;
            }
        }
        visited[row][col] = false;
        return minDistance + 1;
    }

    private final int[][] DIRS = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };

    private int dfs(int[][] matrix, boolean[][] visited, int[][] result, int row, int col, int rowNum, int colNum) {
        if (row >= 0 && row < rowNum && col >= 0 && col < colNum && !visited[row][col]) {
            if (matrix[row][col] == 0) {
                return 0;
            } else if (result[row][col] != 0) {
                return result[row][col];
            } else {
                visited[row][col] = true;
            }
            int minDistance = Integer.MAX_VALUE / 2;
            for (int[] dir : DIRS) {
                minDistance = Math.min(minDistance,
                        dfs(matrix, visited, result, row + dir[0], col + dir[1], rowNum, colNum));
                if (minDistance == 0) {
                    visited[row][col] = false;
                    return 1;
                }
            }
            visited[row][col] = false;
            return minDistance + 1;
        } else {
            return Integer.MAX_VALUE / 2;
        }

    }
}
// @lc code=end
