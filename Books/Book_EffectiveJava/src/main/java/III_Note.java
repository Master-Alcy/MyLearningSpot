import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

public class III_Note {

    private static class Stack<E> {
        public Stack() {
        }

        public void push(E e) {

        }

        public E pop() {
            return null;
        }

        public boolean isEmpty() {
            return true;
        }

        // pushAll method without wildcard type - deficient!
        public void pushAllBad(Iterable<E> src) {
            for (E e: src) {
                push(e);
            }
        }

        public void pushAll(Iterable<? extends E> src) {
            for (E e: src) {
                push(e);
            }
        }

        // popAll method without wildcard type - deficient!
        public void popAllBad(Collection<E> dst) {
            while (!isEmpty())
                dst.add(pop());
        }

        public void popAll(Collection<? super E> dst) {
            while (!isEmpty())
                dst.add(pop());
        }
    }

    // Comparator<? super T> better than Comparator<T>
    public static <T extends Comparable<? super T>> T max(List<? extends  T> list) {

        return null;
    }


    public static void main(String[] args) {
        Stack<Number> numberStack = new Stack<>();
        List<Integer> a = new ArrayList<>();
        a.add(1);
        a.add(2);
        Iterable<Integer> integers = (Iterable<Integer>)a;
        // incompatible types: Iterable<Integer> cannot be converted
        // to Iterable<Number> with pushAllBad
        numberStack.pushAll(integers);
        Collection<Object> objects = new ArrayList<>();
        // Same problem with popAllBad
        numberStack.popAll(objects);

        List<ScheduledFuture<?>> scheduledFutures;
    }
}
