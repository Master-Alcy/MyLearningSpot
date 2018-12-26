package sorting;

/** Data movement is minimal, linear to array size */
public class Selection {
	
	@SuppressWarnings("rawtypes")
	public static void sort(Comparable[] a) { // Sort a[] into increasing order.
		int N = a.length; // array length
		
		for (int i = 0; i < N; i++) { // Exchange a[i] with smallest entry in a[i+1...N).
			int min = i; // index of minimal entry.
			for (int j = i + 1; j < N; j++)
				if (Test.less(a[j], a[min]))
					min = j;
			Test.exch(a, i, min);
		}
	}
}