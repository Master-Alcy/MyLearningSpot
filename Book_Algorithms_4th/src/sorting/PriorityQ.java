package sorting;

import java.io.File;
import java.util.Scanner;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Transaction;

import static sorting.SortUtils.*;

public class PriorityQ {

	public static void main(String[] args) {
		TestTopM("algs4-data\\tinyBatch.txt");

	}

	private void swim(int k) {
		while (k > 1 && less(k / 2, k)) {
			exch(k / 2, k);
			k = k / 2;
		}
	}

	private void sink(int k) {
		while (2 * k <= N) {
			int j = 2 * k;
			if (j < N && less(j, j + 1))
				j++;
			if (!less(k, j))
				break;
			exch(k, j);
			k = j;
		}
	}

	private static void TestTopM(String filePath) {
		try {
			// Print the top M lines in the input stream.
			int M = Integer.parseInt("10");
			MinPQ<Transaction> pq = new MinPQ<Transaction>(M + 1);
			Scanner sc = new Scanner(new File(filePath));

			while (sc.hasNextLine()) {
				pq.insert(new Transaction(sc.nextLine()));
				if (pq.size() > M)
					pq.delMin(); // Remove minimum if M+1 entries on the PQ.
			} // Top M entries are on the PQ.

			Stack<Transaction> stack = new Stack<Transaction>();
			while (!pq.isEmpty())
				stack.push(pq.delMin());

			for (Transaction t : stack)
				StdOut.println(t);

			sc.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("TopM test END.");
		}
	}

}
