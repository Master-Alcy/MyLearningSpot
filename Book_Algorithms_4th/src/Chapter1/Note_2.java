package Chapter1;

import edu.princeton.cs.algs4.*;

public class Note_2 {

	public static void main(String[] args) {
		Counter heads = new Counter("heads");
		Counter tails = new Counter("tails");
		heads.increment();
		int a = heads.tally() - tails.tally();
		StdOut.println(a);
		StdOut.println(heads);
	}

}
