import edu.princeton.cs.algs4.Queue;

public class MergeSort {
    /**
     * Removes and returns the smallest item that is in q1 or q2.
     *
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable> Item getMin(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /** Returns a queue of queues that each contain one item from items. */
    private static <Item extends Comparable> Queue<Queue<Item>>
            makeSingleItemQueues(Queue<Item> items) {
        // below new Queue's Queue haven't instantiated yet.
        // will get null if not instantiate
        Queue<Queue<Item>> queue = new Queue<>();
        int size = items.size();
        for (int i = 0; i < size; i += 1) {
            Queue<Item> tempQueue = new Queue<>();
            tempQueue.enqueue(items.dequeue());
            queue.enqueue(tempQueue);
        }
        return queue;
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     *
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      A Queue containing all the q1 and q2 in sorted order, from least to
     *              greatest.
     *
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2) {
        // Your code here!
        Queue<Item> queue = new Queue<>();
        while (!q1.isEmpty() || !q2.isEmpty()) {
            Item smallestItem = getMin(q1, q2);
            queue.enqueue(smallestItem);
        }
        return queue;
    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> mergeSort(
            Queue<Item> items) {
        // Your code here!
        Queue<Queue<Item>> tempQueue = makeSingleItemQueues(items);
        //After tempQueue.size == 1, every item is now sorted in the first queue of tempQueue
        while (tempQueue.size() > 1) {
            Queue<Item> q1 = tempQueue.dequeue();
            Queue<Item> q2 = tempQueue.dequeue();
            tempQueue.enqueue(mergeSortedQueues(q1, q2));
        }

        Queue<Item> sortedQueue = tempQueue.dequeue();
        return sortedQueue;
    }

    /** For test the mergeSort.  */
    public static void main(String[] args) {
        Queue<String> students = new Queue<>();
        students.enqueue("Felix");
        students.enqueue("Alice");
        students.enqueue("Cindy");
        students.enqueue("Lucy");
        students.enqueue("Judy");
        System.out.println(students);
        Queue<String> sorted = MergeSort.mergeSort(students);
        // The queue "students" is now empty since we modified it
        System.out.println(students);
        System.out.println(sorted);
    }

}
