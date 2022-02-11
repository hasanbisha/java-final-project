import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Arlind Idrizi
 * */
public class Run extends JFrame implements ActionListener {

    JButton button;
    JButton button2;
    JPanel panel;
    JLabel label;
    JLabel label2;

    Run() {
        this.setTitle("Searching & Sorting Algorithms");
        this.setSize(1200, 830);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon("src/logo/wallpaper.jpg");
        ImageIcon icon1 = new ImageIcon("src/logo/logo.png");
        this.setIconImage(icon1.getImage());

        panel = new JPanel();
        LayoutManager overlay = new OverlayLayout(panel);
        panel.setLayout(overlay);

        label = new JLabel("Sorting Algorithm Visualizer & Dijkstra Algorithm(shortest path)");
        label.setFont(new Font("Arial Rounded MT Bolt", Font.BOLD, 26));
        label.setForeground(Color.WHITE);

        label2 = new JLabel();
        label2.setAlignmentX(0.15f);
        label2.setAlignmentY(0.03f);
        label2.setIcon(icon);

        button = new JButton("Sorting Algorithms");
        button.setBackground(Color.gray);
        button.setFont(new Font("Arial Rounded MT Bolt", Font.BOLD, 15));
        button2 = new JButton("Dijkstra find the shortest path");
        button2.setBackground(Color.gray);
        button2.setFont(new Font("Arial Rounded MT Bolt", Font.BOLD, 15));
        button.setFocusable(false);
        button2.setFocusable(false);
        button.setBounds(300,360,250,25);
        button2.setBounds(600,360,250,25);
        button.addActionListener(this);
        button2.addActionListener(this);

        label2.add(button);
        label2.add(button2);
        panel.add(label);
        panel.add(label2);

        this.add(panel);
        this.validate();
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            Visualization Visualization = new Visualization();
            this.dispose();
        }
        if(e.getSource() == button2) {
            new Thread(() -> {
                new Dijkstra();
            }).start();
            this.dispose();
        }
    }
    public static void main(String[] args) {
        new Run();
    }
}