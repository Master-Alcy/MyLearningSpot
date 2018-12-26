package sorting;

public class Bubble {

	@SuppressWarnings("rawtypes")
	public static void sort(Comparable[] a) { // Sort a[] into increasing order.
		int length = a.length;

		for (int i = 0; i < length - 1; i++) {
			for (int j = 0; j < length - 1 - i; j++) {
				if (!Test.less(a[j], a[j + 1])) {
					Test.exch(a, j, j + 1);
				}
			}
		}
	}
}