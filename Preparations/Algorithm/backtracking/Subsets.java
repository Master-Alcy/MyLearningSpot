package backtracking;

import java.util.Arrays;
import java.util.List;

public class Subsets {

    public static void main(String[] args) {
        Subsets ss = new Subsets();
        List<List<Integer>> ll = ss.subsets(new int[]{1, 2, 3, 4, 5});
        for (List<Integer> aL : ll) {
            System.out.println(Arrays.toString(aL.toArray()));
        }
    }

    private List<List<Integer>> subsets(int[] nums) {
        
        return null;
    }
}
