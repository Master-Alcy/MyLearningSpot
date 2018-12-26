package sorting;

/** Data movement is minimal, linear to array size */
public class Selection {
	
	@SuppressWarnings("rawtypes")
	public static void sort(Comparable[] a) { // Sort a[] into increasing order.
		int N = a.length; // array length
		
		for (int i = 0; i < N; i++) { // Exchange a[i] with smallest entry in a[i+1...N).
			int min = i; // index of minimal entry.
			for (int j = i + 1; j < N; j++)
				if (less(a[j], a[min]))
					min = j;
			exch(a, i, min);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}

	@SuppressWarnings("rawtypes")
	private static void exch(Comparable[] a, int i, int j) {
		Comparable t = a[i];
		a[i] = a[j];
		a[j] = t;
	}
}
