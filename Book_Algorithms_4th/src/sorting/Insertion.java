package sorting;

/** Almost linear time for partially sorted array */
public class Insertion {

	@SuppressWarnings("rawtypes")
	public static void sort(Comparable[] a) { // Sort a[] into increasing order.
		int N = a.length;
		
		for (int i = 1; i < N; i++) { // Insert a[i] among a[i-1], a[i-2], a[i-3]... ..
			for (int j = i; j > 0 && Test.less(a[j], a[j - 1]); j--)
				Test.exch(a, j, j - 1);
		}
	}

	// We can shortening its inner loop to move the larger entries
	// to the right one position rather than doing full exchanges
	@SuppressWarnings("rawtypes")
	public static void sortX(Comparable[] a) { // Sort a[] into increasing order.
		int n = a.length;
		int exchanges = 0;
		// put smallest element in position to serve as sentinel
		for (int i = n - 1; i > 0; i--) {
			if (Test.less(a[i], a[i - 1])) {
				Test.exch(a, i, i - 1);
				exchanges++;
			}
		}
		if (exchanges == 0)
			return;
		// insertion sort with half-exchanges
		for (int i = 2; i < n; i++) {
			Comparable v = a[i];
			int j = i;
			while (Test.less(v, a[j - 1])) {
				a[j] = a[j - 1];
				j--;
			}
			a[j] = v;
		}
	}
}