# LaiOffer Notes

## Chapter 1

### OOP

1. Everything is an object
    * Each with a type
    * defined by:
        * state: field it maintains. Field affect class
        * behavior: methods it can do
2. class: types should have (hou1ses)
3. object: each one build (each house)
4. reference: address of objects, de-referencing :(
5. primitive type is not objects, all else object
    * no reference, directly stored in memory.
    * no field, no method
6. Student firstStudent = new Student("Tom");
    1. Declaration: Student firstStudent
    2. Instantiation: new keyword to create object, firstStudent is a reference
    3. Initialization: Student("Tom") after new
7. Stack, Heap Memory
    1. Stack: A reference of reference of reference
    2. Heap: Dynamic Objects, All Objects. Object may have reference.
8. Object jack = object rose => reference change
9. ClassName, variableName. One public class per .java. Class id is just name and type. Order matters, but don't design like that.
10. Null. empty reference.
11. final class (no derived), final method (no overridden), final field (no assigned again).
12. Static:
    * members belong to class, not object (instance)

## Chapter 2

### params passing

1. java, always pass by value
    * primitive type: copy of value itself
    * objects: copy of the object reference
        * note, copy reference means created a new reference to the same object
2. array's elements are occupying consecutive(continuous) memory, Object
    * in stack is a reference to array, memory is in heap
    * array is encapsulation inside JVM, Arrays is not array
    * int[][], default of int[] is null, int[]>[]< is 0.

## Chapter 3

### LinkedIn

1. don't group sent
2. talk to them like they are real person
3. mention how you connect to them
4. be specific about the position you are looking for

### LinkedList

1. null is default init value for references
2. read null won't NPE, only when de-referencing null
3. get length of linked list
4. Java List API: ArrayList, LinkedList
5. before java 7, `List<Node> list = new LinkedList<>();` will throw

```java
class LinkedList {

    private class ListNode {
        private int value;
        private ListNode next;
        private ListNode prev;

        private ListNode(int value) {
            this.value = value;
            this.next = this.prev = null;
        }
    }

    // 1 -> 2 -> 3 -> null
    public int length(ListNode head) {
        int length = 0;
        while (head != null) {
            length++;
            head = head.next;
        } // end when head == null
        return length;
    }

    public ListNode get(ListNode head, int index) {
        while (index > 0 && head != null) {
            head = head.next;
            index--;
        } // index <= 0 || head == null
        return head;
    }

    public ListNode appendHead(ListNode head, int value) {
        ListNode newHead = new ListNode(value);
        newHead.next = head;
        return newHead;
    }

    public ListNode appendTail(ListNode head, int value) {
        if (head == null) {
            return new ListNode(value);
        }
        ListNode root = head; // make a copy of head
        while (head.next != null) {
            head = head.next;
        } // head.next == null
        head.next = new ListNode(value);
        return root;
    }

    public ListNode removeNode(ListNode head, int value) {
        if (head == null) {
            return head;
        } else if (head.value == value) {
            return head.next;
        } // handled length 0 and 1
        ListNode root = head;
        while (head.next != null && head.next.value != value) {
            head = head.next;
        } // head.next == null || head.next.value == value
        if (head.next != null) { // head.next.value == value
            head.next = head.next.next;
        }
        return root;
    }
}
```

### Interface (接口)

1. You cannot instantiate an object by abstract class or interface.
2. `List<Node> list = new ArrayList<>();` could change ArrayList
to LinkedList or any other implementation preferred.
3. To specifically use LinkedList, do `LinkedList<> list = new ...`
4. Better declare an interface then implementation

```java
interface Dictionary {
    // index out of bound will return null
    public Integer get(int index);
}

class Dictonaryimpl implements Dictionary {
    @Override
    public Integer get(int index) {
        return null;
    }
}
```

|   #   | Category          | Abstract Class                           | Interface                             |
| :---: | :---------------- | :--------------------------------------- | :------------------------------------ |
|   1   | Method            | Abstract and non-abstract method         | Only abstract method                  |
|   2   | Inheritance       | No multiple inheritance                  | Supports multiple inheritance         |
|   3   | Variable Modifier | **final, non-final, static, non-static** | Only **static, final**                |
|   4   | Method Modifier   | **static, main, constructor**            | None of **static, main, constructor** |
|   5   | Implementation    | Could implement interface                | Could not implement abstract class    |
|   6   | Keyword           | (abstract) Declare abstract class        | (interface) Declare interface         |

1. All class can only inherit one parent class, but can implements many interfaces
2. For Ex: A class that implements Hockey needs to implement all six methods.

```java
// Filename: Sports.java
public interface Sports {
   public void setHomeTeam(String name);
   public void setVisitingTeam(String name);
}

// Filename: Football.java
public interface Football extends Sports {
   public void homeTeamScored(int points);
   public void visitingTeamScored(int points);
   public void endOfQuarter(int quarter);
}

// Filename: Hockey.java
public interface Hockey extends Sports {
   public void homeGoalScored();
   public void visitingGoalScored();
   public void endOfPeriod(int period);
   public void overtimePeriod(int ot);
}

/**
 * A Java class can only extend one parent class. Multiple inheritance is not allowed.
 * Interfaces are not classes and an interface can extend more than one parent interface.
 * An interface with no methods in it is referred as a tagging interface.
 * 1. Creates a common parent: A common parent among a group of interfaces.
 * 2. Adds a data type to a class: The class becomes an interface type through polymorphism.
 */
public interface NewHockey extends Sports, Event {}
```

## Chapter 4

*. ArrayList
    * An array with potential unused cells
    * Dynamically expand (1.5)
*. for (int i : myList) { // Iterator, myList.remove() => ConcurrentModificationException }

```java
class LinkedList {
    private static int size; // eager computation O(1)
    public int getSize() {
        return this.size;
    }
}
```

| Operation                     | ArrayList          | LinkedList |
| :---------------------------- | :----------------- | :--------- |
| get(int index) at head/tail   | O(1)               | O(1)       |
| get(int index) in middle      | O(1)               | O(n)       |
| set(int index) at head/tail   | O(1)               | O(1)       |
| set(int index) in middle      | O(1)               | O(n)       |
| add(int index, E e) at middle | O(n)               | O(n)       |
| add(int index, E e) at head   | O(n)               | O(1)       |
| add(int index, E e) at tail   | **Amortized O(1)** | O(1)       |
| remove(int index) at head     | O(n)               | O(1)       |
| remove(int index) at tail     | O(1)               | O(1)       |
| remove(int index) at middle   | O(n)               | O(n)       |
| size()                        | O(1)               | O(1)       |
| isEmpty()                     | O(1)               | O(1)       |

* For add at tail, (n + 0.5 n) / 0.5 n = 3 in O(1)
* Choose ArrayList or LinkedList
  * Random Access: ArrayList
  * add/remove at end: ArrayList
    * Time Complexity similar, use ArrayList first. LinkedList uses more memory
    * For ex: a doubly LinkedList uses 3 times more memory (3 references than 1)
    * LinkedList, cached miss rate is high, it's everywhere, paging
  * Vector => ArrayList, Stack => Deque
    * arraydeque Java 6 and later, better than linkedlist's deque
* null and length == 0 is different
* if (arr.length == 0 || array == null) => might NPE
  * || => short circut => left to right
  * reference (ListNode, array, ...) => null check always first
  * what's the value when field out of loop
* understand all variables

```java
class LinkedList {
    public ListNode mid(ListNode root) {
        if (root == null) {
            return head;
        }
        ListNode slow = root;
        ListNode fast = root;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        } // end when 1. fast.next == null 2. fast.next.next == null
        // 1 -> 2 -> 3(slow) -> 4 -> 5(fast) -> 6
        // 1. fast.next.next == null, fast.next != null
        // mid at 5/2 => mid at 2(0,1,2) => slow
        // 1 -> 2 -> 3(slow) -> 4 -> 5(fast)
        // 4/2 = 2 => index 2 is slow
        return slow;
    }
    // 1. It could be helpful to create dummy node, when head may be changed
    // EX: insert a value into a sorted list, 1 -> 3 -> 5, insert 4, 0
    // 2. not sure which node would be head
    // EX: merge two list, 3 -> 5, 4 -> 6
    public ListNode merge(ListNode h1, ListNode h2) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;

        while (h1 != null && h2 != null) {
            if (h1.value <= h2.value) {
                curr.next = h1; // extend dummy
                h1 = h1.next; // h1 to next element
            } else {
                curr.next = h2;
                h2 = h2.next;
            }
            curr = curr.next; // do this anyway
        } // one or both h1 and h2 becomes null
        if (h1 != null) {
            curr.next = h1; // curr.next == rest
        }
        if (h2 != null) {
            curr.next = h2;
        }
        return dummy.next;
    }
}
```

## Chapter 5

1. Try not to use global variable
2. try to define variable inside loop

### Queue

| Operation | throw exception | return special value(null) |
| :--- | :--- | :--- |
| Insert | add(e) | offer(e) |
| Remove | remove() | poll() |
| Examine | element() | peek() |










# End of line Load