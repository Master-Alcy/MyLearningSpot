package sorting;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

public class SortCompare {

	public static double time(String alg, Double[] a) {
		Stopwatch timer = new Stopwatch();
		switch (alg) {
		case "Insertion":
			Insertion.sort(a);
			break;
		case "InsertionX":
			Insertion.sortX(a);
			break;
		case "Bubble":
			Bubble.sort(a);
			break;
		case "Selection":
			Selection.sort(a);
			break;
		case "Shell":
			// Shell.sort(a);
			break;
		case "Merge":
			// Merge.sort(a);
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
		String alg1 = "Bubble";
		String alg2 = "InsertionX";
		int N = Integer.parseInt("5000");
		int T = Integer.parseInt("100");
		double t1 = timeRandomInput(alg1, N, T); // total for alg1
		double t2 = timeRandomInput(alg2, N, T); // total for alg2
		StdOut.printf("For %d random Doubles\n %s is", N, alg1);
		StdOut.printf(" %.1f times faster than %s\n", t2 / t1, alg2);
	}
}