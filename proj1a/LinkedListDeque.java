public class LinkedListDeque<T> {
    /* make an inner class node */
    private class Node {
        public T item;
        public Node next;
        public Node prev;

        /* constructor for the Node */
        public Node(Node p, T i, Node n) {
            prev = p;
            item = i;
            next = n;
        }
    }
    /* size of the Deque */
    private int size;
    /* the sentinel node*/
    private Node sentinel;

    /* constructor for the Deque, size = 0 */
    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    public int size() {
        return size;
    }
    /* add the type T item to the front deque */
    public void addFirst(T item) {
        Node newNode = new Node(sentinel, item, sentinel.next);
        sentinel.next.prev = newNode; // 这里sentinel.next.prev指的是新的node
        sentinel.next = newNode;
        size += 1;
    }
    /* sentinel.prev is the last node of the entire Deque
    * 需要有三个Node比较容易理解
    */
    public void addLast(T item) {
        /* here sentinel.prv can ensure get the current last Node */
        Node newNode = new Node(sentinel.prev, item, sentinel);
        sentinel.prev.next = newNode;
        sentinel.prev = newNode; // here prev is the previous prev.
        size += 1;
    }
    public void printDeque() {
        int i;
        Node temp = sentinel.next;
        for(i = 0; i < size; i++) {
            System.out.print(temp.item + " ");
            temp = temp.next;
        }
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T res = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size -= 1;
        return res;
    }
    /* remove the last element, and re-assign the last Node,
    now, new Last node will connect to the sentinel
     */
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T res = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        return res;
    }
    /* use iteration to get i index item in the Node */
    public T get(int index) {
        if (index > size) {
            return null;
        }
        Node temp = sentinel.next;
        int i;
        for(i = 0; i < index; i++) {
            temp = temp.next;
        }
        return temp.item;
    }
    /* get an N index item by using recursive */
    public T getRecursive(int index) {
        if (index > size) {
            return null;
        }
        return recursiveHelp(sentinel.next, index);
    }
    /* recursiveHelp is useful once we need implenting method recursively,
    recursiveHelp will take 2 parameter, then keep track whether the node is
    current Node. Until the index is 0, the Node is the current Node we looking for,
    then we just need to return currentNode.item.
     */
    private T recursiveHelp(Node p, int n) {
        if (n == 0) {
            return p.item;
        }
        return recursiveHelp(p.next, n - 1);
    }


}
