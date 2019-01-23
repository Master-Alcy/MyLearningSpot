package greedy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class QueueReconstructionByHeight {

    /**
     * 10ms, 88.41%. O( n log n)
     */
    private int[][] reconstructQueue(int[][] people) {
        if (people.length < 2)
            return people;
        Arrays.sort(people, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                // If same height, smaller k in front
                // If differ height, smaller height in front
                return o1[0] == o2[0] ? o1[1] - o2[1] : o2[0] - o1[0];
            }
        }); // Sorted like 7,0 7,1 6,1 5,0 5,2 4,4
        //System.out.println(Arrays.deepToString(people));
        List<int[]> list = new ArrayList<>();
        // This for each loop did exactly what was asked
        for (int[] person : people)
            list.add(person[1], person);
        // for multi dimensional list into multi D array
        return list.toArray(new int[list.size()][2]);
    }
}
