import javax.swing.*;
import java.util.*;

/**
 * @author Xhuljo Balli
 * */

public class InsertionSort extends JPanel {
    public void runInsertionSort(ArrayList<Integer> nums, Graph graph, Visualization visualization) throws InterruptedException {
        for (int i = 1; i < nums.size(); i++) {
            int move = i;
            int compare = move - 1;

            while (compare >= 0 && nums.get(compare) > nums.get(move)) {
                Collections.swap(nums, compare, move);
                move = move - 1;
                compare = compare - 1;

                Thread.sleep(25);
                graph.removeAll();
                graph.updateArray(nums);
                graph.revalidate();
                graph.paintImmediately(0,30,1190,630);
            }
        }
        visualization.needRefresh = true;
    }
}
