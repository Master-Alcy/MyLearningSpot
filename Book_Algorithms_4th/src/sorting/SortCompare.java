package sorting;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

public class SortCompare {

	public static double time(String alg, Double[] a) {
		Stopwatch timer = new Stopwatch();
		switch (alg) {
		case "Insertion":
			Elementary.InsertionSort(a);
			break;
		case "InsertionX":
			Elementary.InsertionSortX(a);
			break;
		case "Bubble":
			Elementary.BubbleSort(a);
			break;
		case "Selection":
			Elementary.SelectionSort(a);
			break;
		case "Shell":
			Elementary.ShellSort(a);
			break;
		case "MergeX":
			Merge.sortX(a);
			break;
		case "Merge":
			Merge.sort(a);
			break;
		case "MergeBU":
			Merge.sortBU(a);
			break;
		case "ParallelMerge":
			Merge.parallelMergeSort(a);
			break;
		case "Quick":
			// Quick.sort(a);
			break;
		case "Heap":
			// Heap.sort(a);
			break;
		default:
			System.out.println(alg + " is not in switch.");
			break;
		}
		return timer.elapsedTime();
	}

	public static double timeRandomInput(String alg, int N, int T) {
		// Use alg to sort T random arrays of length N.
		double total = 0.0;
		Double[] a = new Double[N];
		for (int t = 0; t < T; t++) { // Perform one experiment (generate and sort an array).
			for (int i = 0; i < N; i++)
				a[i] = StdRandom.uniform();
			total += time(alg, a);
		}
		return total;
	}

	public static void main(String[] args) {
		String alg1 = "MergeBU";
		String alg2 = "Merge";
		int N = Integer.parseInt("1000000");
		int T = Integer.parseInt("10");
		double t1 = timeRandomInput(alg1, N, T); // total for alg1
		System.out.println("Method 1 finished in: " + t1);
		double t2 = timeRandomInput(alg2, N, T); // total for alg2
		System.out.println("Method 2 finished in: " + t2);
		StdOut.printf("For %d random Doubles in %d Arrays%n%s is", N, T, alg1);
		StdOut.printf(" %.3f times faster than %s\n", t2 / t1, alg2);
	}
}