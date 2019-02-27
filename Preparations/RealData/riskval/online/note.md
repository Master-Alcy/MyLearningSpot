# Thoughts

1. Animal Inheritance for dog, cow, duck
    * super(true, false);
2. Hackerrank: Buying Show Tickets
    * handle ppl after p
3. Find lone number
    * hashmap, Map.entry

~~~java
class Test {
    public static long times(List<Integers> tickets, int p) {
        int base = tickets.get(p);
        long times = base;
        for (int i = 0; i < tickets.size(); i++) {
            int num = tickets.get(i);
            if (i < p) { // ppl before p
                if (num < base) {
                    times += num;
                } else {
                    time += base;
                }
            } else if (i > p) { // ppl after p
                if (num < base) {
                    times += num;
                } else {
                    times += base - 1;
                }
            }
        }
        return times;
    }
}
~~~