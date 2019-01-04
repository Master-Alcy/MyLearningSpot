package NewQuestions;

@SuppressWarnings("unused")
public class NumberOfIslands {

	public static void main(String[] args) {
		NumberOfIslands noi = new NumberOfIslands();
		char[][] grid1 = { { '1', '1', '1', '1', '0' }, { '1', '1', '0', '1', '0' }, { '1', '1', '0', '0', '0' },
				{ '0', '0', '0', '0', '0' } };
		char[][] grid2 = { { '1', '1', '0', '0', '0' }, { '1', '1', '0', '0', '0' }, { '0', '0', '1', '0', '0' },
				{ '0', '0', '0', '1', '1' } };

		System.out.println(noi.numIslands(grid1));

	}

	/** first try with UF */
	private int numIslands(char[][] grid) {
		int count = 0;
		int colMax = grid.length;
		int rowMax = grid[0].length;

		for (int i = 0; i < colMax; i++) {
			for (int j = 0; j < rowMax; j++) {
				char c = grid[i][j];
			}
		}

		return -1;
	}
}
