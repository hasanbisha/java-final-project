import java.util.ArrayList;
import java.util.Collections;
import javax.swing.*;
import java.awt.*;

/**
 * @author Xhuljo Balli
 * */
public class Graph extends JPanel {
    ArrayList<Integer> nums;
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
            for (int i = 0; i < nums.size(); i++) {
                g.setColor(Color.BLACK);
                g.fillRect(+16 * 1 * (i) + 1 * (i), 660 - nums.get(i), 15, nums.get(i));
            }
    }
    public Graph(ArrayList<Integer> array) {
        nums = array;
    }
    public void updateArray(ArrayList<Integer> array) {
        nums = array;
    }
}
