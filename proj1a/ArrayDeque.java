public class ArrayDeque<T> {

    /** arrary to save the data */
    private T[] itemArray;

    /** size of the array */
    private int length = 8;

    /** front index */
    private int left;

    /** last index */
    private int right;

    /** size of the deque */
    private int size;

    //ArrayDeque Constructor
    public ArrayDeque() {
        itemArray = (T[]) new Object[length];
        left = 0;
        right = 0;
        size = 0;
    }

    public int size() {
        return size;
    }
    /** return True if size == 0, false otherwise */
    public boolean isEmpty() {
        return left == right;
    }
    /** index minus one
      while the index point 0, after minus one,
      we should point the previous one which is last item
      in the array
     */
    private int minusOne(int index) {
        if (index == 0) {
            return length - 1;
        }
        return index - 1;
    }
    /** index plus one
     * while index == module - 1, return 0
     */
    private int plusOne(int index, int module) {
        if(index == module - 1) {
            return 0;
        }
        return index + 1;
    }

    /** double the size and make a new array.
      when we need to double our array size,
      we need to copy the item in the prev array to newArray.
     */
    private void reSize(int newSize) {
        T[] newArray = (T[]) new Object[newSize];
        int i = 0;
        int temp = left;
        while(temp != right) {
            newArray[i] = itemArray[temp];
            temp += 1;
            i += 1;
        }
        left = 0;
        right = size;
        itemArray = newArray;
        length = newSize;
    }

    /** increase length size once the array have no enough space
     * to store the value
     */
    private boolean isFull() {
        return size() == length - 1;
    }

    private boolean isLowUsage() {
        return size() >= 16 && length / size() > 4;
    }

    /** add an item to the left deque */
    public void addFirst(T item) {
        if (isFull()) {
            reSize(length * 2);
        }
        left = minusOne(left);
        itemArray[left] = item;
        size += 1;
    }

    /** add an item to the right deque */
    public void addLast(T item) {
        if (isFull()) {
            reSize(length * 2);
        }
        itemArray[right] = item;
        right = (right + 1 + length) / length;
        size += 1;
    }
    /** remove the first element from deque */
     public T removeFirst() {
         if (size == 0) {
             return null;
         }
         T item = itemArray[left];
         left = (left + 1) % length; // if left = 0, our first item is [1]
         if (isLowUsage()) {
             reSize(length / 2);
         }
         size -= 1;
         return item;
     }

     /** remove the last element from the deque */
     public T removeLast() {
         if (size == 0) {
             return null;
         }
         right = minusOne(right); // if right is 0, our last item is length -1
         T item = itemArray[right];
         if (isLowUsage()) {
             reSize(length / 2);
         }
         size -= 1;
         return item;
     }

     /** print out all the element in the deque */
     public void printDeque() {
         if (size == 0) {
             System.out.println("Does not exist");
         }
         int temp = right;
         for(int i = left; i != right; plusOne(i, length)) {
             System.out.print(itemArray[i] + " ");
         }

     }
     /** get the index item in the array */
     public T get(int index) {
        if (size == 0 || index >= size) {
            return null;
        }
        int temp = left;
        for(int i = 0; i < index; i++) {
            temp = plusOne(temp, length);
        }
        return itemArray[temp];
     }

}