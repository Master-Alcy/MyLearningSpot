package hashmap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class twosum {

	public static void main(String[] args) {
		twosum t = new twosum();
		int[] a = { 3, 2, 4 };
		int[] result = t.twoSum(a, 6);
		System.out.println(Arrays.toString(result));
	}

	public int[] twoSum(int[] nums, int target) {
		Map<Integer, Integer> map = new HashMap<>();

		for (int i = 0; i < nums.length; i++) {
			if (map.containsKey(target - nums[i])) {
				return new int[] { map.get(target - nums[i]), i };
			}
			map.put(nums[i], i);
		}
		return new int[] { 0, 0 };
	}
}