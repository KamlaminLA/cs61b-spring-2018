package synthesizer;
import java.util.Iterator;

public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        //   Create new array with capacity elements.
        //   first, last, and fillCount should all be set to 0.
        //    this.capacity should be set appropriately. Note that the local variable
        //    here shadows the field we inherit from AbstractBoundedQueue, so
        //    you'll need to use this.capacity to set the capacity.
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;
        // here local variable capacity has same name with capacity in AbstractBoundedQueue
        this.capacity = capacity;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    @Override
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update last.
        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        }
        rb[last] = x;
        fillCount += 1;
        last = (last + 1) % capacity;  // here we get 0 once last equal capacity
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException(). Exceptions
     * covered Monday.
     */
    @Override
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and update
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        T item = rb[first];
        fillCount -= 1;
        first = (first + 1) % capacity; // here we get 0 once first equal capacity
        return item;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    @Override
    public T peek() {
        // TODO: Return the first item. None of your instance variables should change.
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        return rb[first];
    }

    @Override
    public Iterator<T> iterator() {
        return new BoundedQueueIterator();
    }

    private class BoundedQueueIterator implements Iterator<T> {
        private int index;
        private int remainItems;
        public  BoundedQueueIterator() {
            index = first;
            remainItems = fillCount;
        }
        public boolean hasNext() {
            return remainItems > 0;
        }
        public T next() {
            T returnItem = rb[index];
            index = (index + 1) % capacity;
            remainItems -= 1;
            return returnItem;
        }

    }

}
