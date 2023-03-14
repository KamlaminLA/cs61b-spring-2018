import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    @Test
    public void testStudentDeque() {
        StudentArrayDeque<Integer> s1 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> s2 = new ArrayDequeSolution<>();
        for(int i = 0; i < 1000; i++) {
            int randomNum = StdRandom.uniform(1, 1000);
            s1.addFirst(randomNum);
            s2.addFirst(randomNum);
            String mess = "" + "addFirst(" + s2.get(0) + ")\n";
            assertEquals(mess, s2.get(0), s1.get(0));
        }
        for(int i = 0; i < 600; i++) {
            Integer a = s1.removeFirst();
            Integer b = s2.removeFirst();
            String mess = "" + "removeFirst(" + b + ")\n";
            assertEquals(mess, b, a);
        }
        for(int i = 0; i < 100; i++) {
            int randomNum = StdRandom.uniform(1, 1000);
            s1.addLast(randomNum);
            s2.addLast(randomNum);
            String mess = "" + "addLast(" + s2.size() + ")\n";
            assertEquals(mess, s2.size(), s1.size());
        }
        for(int i = 0; i < 200; i++) {
            Integer actual = s1.removeLast();
            Integer expect = s2.removeLast();
            String mess = "" + "removeLast(" + expect + ")\n";
            assertEquals(mess, expect, actual);
        }

    }

}
