import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.*;

/**
 * @author Arlind Idrizi
 * ArrayNums class: takes the number given by the user
 * */
public class ArrayNums implements ActionListener  {
    JFormattedTextField input = new JFormattedTextField();
    JButton enter = new JButton("ENTER");
    JOptionPane pane = new JOptionPane();

    ArrayNums() {
        input.setBounds(770, 50, 140, 30);
        input.setText("Enter a number");

        enter.setBounds(915, 50, 140, 30);
        enter.setBackground(new Color(192,192,192));
        enter.setFont(new Font("Arial Rounded MT Bolt", Font.BOLD, 15));
        enter.addActionListener(this);
        enter.setFocusable(false);
    }
    /**
     * @return nums
     * */
    String value = "0";
    public ArrayList<Integer> createArray() {
        ArrayList<Integer> nums = new ArrayList<>();

        for (int i = 1; i < Integer.parseInt(value); i++) {
                nums.add(9 * i);
        }
        Collections.shuffle(nums);
        System.out.println(nums);
        return nums;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == enter) {
            value = input.getText();
            if(Integer.parseInt(value) > 70) {
                pane.showMessageDialog(null, "Enter a smaller number!", "Selection Sort", JOptionPane.WARNING_MESSAGE);
            }
            else if(Integer.parseInt(value) < 5) {
                pane.showMessageDialog(null, "Enter a larger number!", "Selection Sort", JOptionPane.WARNING_MESSAGE);
            }
            else {
                System.out.println(value);
            }
        }
    }
}
