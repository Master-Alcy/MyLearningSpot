package Chapter1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Note_7 {

	public static void main(String[] args) {
		/** A early study for multi-threading */
		// Try testing the three sum funciton
		Note_7 n = new Note_7();
		MultiThreading R1 = n.new MultiThreading("T1");
		R1.start();
		MultiThreading R2 = n.new MultiThreading("T2");
		R2.start();
		MultiThreading R3 = n.new MultiThreading("T3");
		R3.start();
		MultiThreading R4 = n.new MultiThreading("T4");
		R4.start();
	}

	/** implements Runnable is said to be better than extend thread */
	private class MultiThreading implements Runnable {
		private Thread t;
		private String threadName;

		MultiThreading(String name) {
			threadName = name;
			System.out.println("Creating " + threadName);
		}

		@Override
		public void run() {
			System.out.println("Running " + threadName);
			// My Tasks are distributed here
			try {
				switch(threadName){
				case "T1":
					Note_7.TestThreeSum("algs4-data\\1Kints.txt", 1000);
				case "T2":
					Note_7.TestThreeSum("algs4-data\\2Kints.txt", 2000);
				case "T3":
					Note_7.TestThreeSum("algs4-data\\4Kints.txt", 4000);
				case "T4":
					Note_7.TestThreeSum("algs4-data\\8Kints.txt", 8000);
				default:
					throw new Exception("No threadName matched");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("Thread " + threadName + " exiting.");
		}

		public void start() {
			System.out.println("Starting " + threadName);
			if (t == null) {
				t = new Thread(this, threadName);
				t.start();
			}
		}
	}

	/** Try counting the actual time it takes to finish the method */
	public static void TestThreeSum(String filePath, int size)
			throws FileNotFoundException {
		Scanner sc = new Scanner(new File(filePath));
		int[] arr = new int[size];
		int index = 0;

		Stopwatch timer1 = new Stopwatch();
		while (sc.hasNext()) {
			arr[index++] = sc.nextInt();
		}
		double readTime = timer1.elapsedTime();
		sc.close();
		Stopwatch timer2 = new Stopwatch();
		int count = ThreeSum.BruteForceCount(arr);
		double methodTime = timer2.elapsedTime();

		System.out.println("THREAD on size: " + size
				+ "\nThe time it takes to read the file is: " + readTime
				+ "secs\nTest if array is full: " + arr[size - 1]
				+ "\nThe time it takes to compute is: " + methodTime
				+ "secs.\nThe count is: " + count);
	}
}