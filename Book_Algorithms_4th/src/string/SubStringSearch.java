package string;

public class SubStringSearch {

	public static void main(String[] args) {
		int num = search("abc", "abcabcabcd");
		System.out.println(num);
	}
	
	/** KMP substring search */
	private static int KMPsearch(String pat, String txt) {
		
		return -1;
	}

	/** Brute-force, ~NM compares for pattern M text N in worst case. */
	private static int search(String pat, String txt) {			
		int M = pat.length();
		int N = txt.length();
		
		for (int i = 0; i <= N - M; i++) {
			int j;

			for (j = 0; j < M; j++)
				if (txt.charAt(i + j) != pat.charAt(j))
					break;

			if (j == M)
				return i; // found
		}
		return N; // not found

		// explicit backup
//		int M = pat.length();
//		int N = txt.length();
//		int i, j;
//
//		for (i = 0, j = 0; i < N && j < M; i++) {
//			if (txt.charAt(i) == pat.charAt(j)) {
//				j++;
//			} else {
//				i -= j;
//				j = 0;
//			}
//		}
//		if (j == M)
//			return i - M; // found
//		else
//			return N; // not found
	}

}
