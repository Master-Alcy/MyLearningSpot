package sorting;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Certification; Running time; Extra memory; Types of data
 */

@SuppressWarnings("rawtypes")
public class Test {

	public static void sort(Comparable[] a) {
		/* See Algorithms 2.1, 2.2, 2.3, 2.4, 2.5, or 2.7. */
	}

	@SuppressWarnings("unchecked")
	private static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}

	@SuppressWarnings("unused")
	private static void exch(Comparable[] a, int i, int j) {
		Comparable t = a[i];
		a[i] = a[j];
		a[j] = t;
	}

	private static void show(Comparable[] a) { // Print the array, on a single line.
		for (int i = 0; i < a.length; i++)
			StdOut.print(a[i] + " ");
		StdOut.println();
	}

	public static boolean isSorted(Comparable[] a) { // Test whether the array entries are in order.
		for (int i = 1; i < a.length; i++)
			if (less(a[i], a[i - 1]))
				return false;
		return true;
	}

	public static void main(String[] args) { // Read strings from standard input, sort them, and print.
		String[] a = StdIn.readAllStrings();
		sort(a);
		if (isSorted(a)) {
			show(a);
		} else {
			System.out.println("Not Sorted.");
		}
	}

}
