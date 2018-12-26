package sorting;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Merge {
	@SuppressWarnings("rawtypes")
	private static Comparable[] aux; // auxiliary array for merges

	@SuppressWarnings("rawtypes")
	public static void sort(Comparable[] a) {
		aux = new Comparable[a.length]; // Allocate space just once.
		sort(a, 0, a.length - 1);
	}

	@SuppressWarnings("rawtypes")
	private static void sort(Comparable[] a, int lo, int hi) { // Sort a[lo..hi].
		if (hi <= lo)
			return;
		int mid = lo + (hi - lo) / 2;
		sort(a, lo, mid); // Sort left half.
		sort(a, mid + 1, hi); // Sort right half.
		merge(a, lo, mid, hi); // Merge results (code on page 271).
	}

	@SuppressWarnings("rawtypes")
	private static void merge(Comparable[] a, int lo, int mid, int hi) { // Merge a[lo..mid] with a[mid+1..hi].
		int i = lo, j = mid + 1;
		for (int k = lo; k <= hi; k++) // Copy a[lo..hi] to aux[lo..hi].
			aux[k] = a[k];
		for (int k = lo; k <= hi; k++) // Merge back to a[lo..hi].
			if (i > mid)
				a[k] = aux[j++];
			else if (j > hi)
				a[k] = aux[i++];
			else if (Test.less(aux[j], aux[i]))
				a[k] = aux[j++];
			else
				a[k] = aux[i++];
	}

	/** parallel merge sort entry, around 2.2 times faster for large array(100k) */
	@SuppressWarnings("rawtypes")
	public static Comparable[] parallelMergeSort(Comparable[] list) {
		RecursiveTask<Comparable[]> mainTask = new SortTask(list);
		ForkJoinPool pool = new ForkJoinPool();
		return pool.invoke(mainTask);
	}

	@SuppressWarnings("rawtypes")
	private static class SortTask extends RecursiveTask<Comparable[]> {
		private static final long serialVersionUID = 1L;
		private final int THRESHOLD = 500;
		private Comparable[] list;

		private SortTask(Comparable[] list) {
			this.list = list;
		}

		@Override
		protected Comparable[] compute() {
			if (list.length < THRESHOLD)
				java.util.Arrays.sort(list);
			else {
				// Obtain the first half
				int firstHalfLength = list.length / 2;
				Comparable[] firstHalf = new Comparable[firstHalfLength];
				System.arraycopy(list, 0, firstHalf, 0, firstHalfLength);

				// Obtain the second half
				int secondHalfLength = list.length - firstHalfLength;
				Comparable[] secondHalf = new Comparable[secondHalfLength];
				System.arraycopy(list, firstHalfLength, secondHalf, 0, secondHalfLength);

				// Recursively sort the two halves
				invokeAll(new SortTask(firstHalf), new SortTask(secondHalf));

				// Merge firstHalf with secondHalf into list
				merge(firstHalf, secondHalf, list);
			}
			return list;
		}
	}

	/** Merge two sorted lists */
	@SuppressWarnings("rawtypes")
	private static void merge(Comparable[] list1, Comparable[] list2, Comparable[] temp) {
		int current1 = 0; // Current index in list1
		int current2 = 0; // Current index in list2
		int current3 = 0; // Current index in temp

		while (current1 < list1.length && current2 < list2.length) {
			if (Test.less(list1[current1], list2[current2])) {
				temp[current3++] = list1[current1++];
			} else {
				temp[current3++] = list2[current2++];
			}
		}
		while (current1 < list1.length) {
			temp[current3++] = list1[current1++];
		}
		while (current2 < list2.length) {
			temp[current3++] = list2[current2++];
		}
	}
}