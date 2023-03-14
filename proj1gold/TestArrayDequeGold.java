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
        }
        for(int i = 0; i < 600; i++) {
            Integer a = s1.removeFirst();
            Integer b = s2.removeFirst();
            assertEquals(a, b);
        }
        for(int i = 0; i < 100; i++) {
            int randomNum = StdRandom.uniform(1, 1000);
            s1.addLast(randomNum);
            s2.addLast(randomNum);
        }
        for(int i = 0; i < 200; i++) {
            Integer actual = s1.removeLast();
            Integer expect = s2.removeLast();
            String mess = "" + "removeLast(" + expect + ")\n";
            assertEquals(mess,actual, expect);
        }

    }

}
