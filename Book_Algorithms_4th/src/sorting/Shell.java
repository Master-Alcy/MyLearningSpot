package sorting;

/**
 * Experienced programmers sometimes choose ShellSort because it has acceptable
 * running time even for moderately large arrays; it requires a small amount of
 * code; and it uses no extra space.
 */
public class Shell {

	@SuppressWarnings("rawtypes")
	public static void sort(Comparable[] a) { // Sort a[] into increasing order.
		int N = a.length;
		int h = 1;

		while (h < N / 3)	// After last loop h is at N/3 * 3 + 1 which is around N
			h = 3 * h + 1; // 1, 4, 13, 40, 121, 364, 1093, ...
		while (h >= 1) { // h-sort the array.
			for (int i = h; i < N; i++) {
				// Insert a[i] among a[i-h], a[i-2*h], a[i-3*h]... .
				for (int j = i; j >= h && Test.less(a[j], a[j - h]); j -= h)
					Test.exch(a, j, j - h);
			}
			h = h / 3;
		}
	}
}
