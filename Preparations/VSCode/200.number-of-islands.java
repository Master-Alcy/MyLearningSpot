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
    public int numIslands3(char[][] grid) {
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

        while (!queue.isEmpty()) {
            int num = queue.poll();
            int r = num / colNum;
            int c = num % colNum;

            for (int[] dir : DIRS) {
                int newRow = r + dir[0];
                int newCol = c + dir[1];
                if (newRow < 0 || newRow >= rowNum || newCol < 0 || newCol >= colNum || grid[newRow][newCol] == '0') {
                    continue;
                }
                queue.offer(newRow * colNum + newCol);
                grid[newRow][newCol] = '0';
            }
        }
    }

    // Union Find
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        int rowNum = grid.length;
        int colNum = grid[0].length;
        UnionFind uf = new UnionFind(grid);

        for (int row = 0; row < rowNum; row++) {
            for (int col = 0; col < colNum; col++) {
                if (grid[row][col] == '1') {
                    for (int[] dir : DIRS) {
                        int x = row + dir[0];
                        int y = col + dir[1];
                        if (x >= 0 && x < rowNum && y >= 0 && y < colNum
                            && grid[x][y] == '1') {
                            int oldId = row * colNum + col;
                            int newId = x * colNum + y;
                            uf.union(oldId, newId);
                        }
                    }
                }
            }
        }

        return uf.getCount();
    }

    /**
     * Optimized Weighted quick-union by rank with path compression by halving.
     * Constructor: O(N), Union: O(near 1), Find O(near 1)
     */
    private class UnionFind {
        private int[] parent; // parent of node
        private byte[] rank; // rank of subtree rooted at node, never more than 31
        private int count; // number of components

        public UnionFind(char[][] grid) {
            int rowNum = grid.length;
            int colNum = grid[0].length;
            int size = rowNum * colNum;
            parent = new int[size];
            rank = new byte[size]; // array size and byte value is two things
            for (int i = 0; i < rowNum; i++) {
                for (int j = 0; j < colNum; j++) {
                    if (grid[i][j] == '1') {
                        int id = i * colNum + j;
                        parent[id] = id;
                        rank[id] = 0; // Initially all rank is 0
                        count++;
                    }
                }
            }
        }

        public int getCount() {
            return count;
        }

        public boolean isConnected(int node1, int node2) {
            return find(node1) == find(node2);
        }

        public int find(int node) {
            while (node != parent[node]) {
                parent[node] = parent[parent[node]]; // path compression by halving
                node = parent[node];
            }
            return node;
        }

        public void union(int node1, int node2) {
            int root1 = find(node1);
            int root2 = find(node2);
            if (root1 == root2)
                return;
            // make root of smaller rank point to root of larger rank
            if (rank[root1] < rank[root2])
                parent[root1] = root2;
            else if (rank[root1] > rank[root2])
                parent[root2] = root1;
            else {
                // if rank the same, attach to root1 and make root1's rank one level larger
                parent[root2] = root1;
                rank[root1]++;
            }
            count--;
        }
    }// End of Optimized UF
}
// @lc code=end

