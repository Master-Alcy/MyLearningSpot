import java.util.LinkedList;
import java.util.Queue;

/*
 * @lc app=leetcode id=200 lang=java
 *
 * [200] Number of Islands
 *
 * https://leetcode.com/problems/number-of-islands/description/
 *
 * algorithms
 * Medium (43.64%)
 * Likes:    3579
 * Dislikes: 131
 * Total Accepted:    484.3K
 * Total Submissions: 1.1M
 * Testcase Example:  '[["1","1","1","1","0"],["1","1","0","1","0"],["1","1","0","0","0"],["0","0","0","0","0"]]'
 *
 * Given a 2d grid map of '1's (land) and '0's (water), count the number of
 * islands. An island is surrounded by water and is formed by connecting
 * adjacent lands horizontally or vertically. You may assume all four edges of
 * the grid are all surrounded by water.
 * 
 * Example 1:
 * 
 * 
 * Input:
 * 11110
 * 11010
 * 11000
 * 00000
 * 
 * Output: 1
 * 
 * 
 * Example 2:
 * 
 * 
 * Input:
 * 11000
 * 11000
 * 00100
 * 00011
 * 
 * Output: 3
 * 
 */

// @lc code=start
class Solution {

    private static final int[][] DIRS = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };

    // Try DFS solution
    public int numIslands2(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
    
        int rowNum = grid.length;
        int colNum = grid[0].length;
        int numIsland = 0;
    
        for (int row = 0; row < rowNum; row++) {
            for (int col = 0; col < colNum; col++) {
                if (grid[row][col] == '1') {
                    numIsland++;
                    dfs(grid, row, col, rowNum, colNum);
                }
            }
        }

        return numIsland;
    }

    private void dfs(char[][] grid, int row, int col, int rowNum, int colNum) {
        // check if out of bound
        if (row < 0 || row >= rowNum || col < 0 || col >= colNum || grid[row][col] == '0') {
            return;
        }
        // omit current land to sea
        grid[row][col] = '0';
        for (int[] dir : DIRS) {
            dfs(grid, row + dir[0], col + dir[1], rowNum, colNum);
        }
    }

    // BFS
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
    
        int rowNum = grid.length;
        int colNum = grid[0].length;
        int numIsland = 0;
    
        for (int row = 0; row < rowNum; row++) {
            for (int col = 0; col < colNum; col++) {
                if (grid[row][col] == '1') {
                    numIsland++;
                    grid[row][col] = '0';
                    bfs(grid, row, col, rowNum, colNum);
                }
            }
        }

        return numIsland;
    }

    private void bfs(char[][] grid, int row, int col, int rowNum, int colNum) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(row * colNum + col);

        while(!queue.isEmpty()) {
            int num = queue.poll();
            int r = num / colNum;
            int c = num % colNum;

            for (int[] dir : DIRS) {
                int newRow = r + dir[0];
                int newCol = c + dir[1];
                if (newRow < 0 || newRow >= rowNum || newCol < 0 || newCol >= colNum 
                    || grid[newRow][newCol] == '0') {
                    continue;
                }
                queue.offer(newRow * colNum + newCol);
                grid[newRow][newCol] = '0';
            }
        }
    }
}
// @lc code=end

