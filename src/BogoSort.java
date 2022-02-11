import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author Arlind Idrizi
 * */
public class BogoSort extends JPanel {
    public void runBogoSort(ArrayList<Integer> nums, Graph graph, Visualization visualization) throws InterruptedException {
        Random random = new Random();
        for (int i = 0; i < nums.size(); i++) {
            nums.add(random.nextInt(100));

            while (!isListSorted(nums)) {
                Collections.shuffle(nums);
                System.out.println(nums);

                Thread.sleep(10);
                graph.removeAll();
                graph.updateArray(nums);
                graph.revalidate();
                graph.paintImmediately(0,30,1190,630);
            }
            break;
        }
    }
    // To check if array is sorted or not
    private static boolean isListSorted (List<Integer> numberList) {
        if (numberList == null) {
            return true;
        }
        int length = numberList.size();
        if (length <= 1) {
            return true;
        }
        for (int i = 0; i < length - 1; i++) {
            if (numberList.get(i) > numberList.get(i + 1)){
                return false;
            }
        }
        return true;
    }
}
