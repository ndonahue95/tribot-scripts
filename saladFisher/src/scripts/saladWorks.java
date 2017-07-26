package scripts;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Nicholas on 7/20/2017.
 */
public class saladWorks extends JFrame {
    private JPanel panel1;
    private JList list1;
    private JButton startButton;

    public static boolean canStart = false;

    public saladWorks() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 200, 300);

        panel1 = new JPanel();
        panel1.setBorder(new EmptyBorder(5,5,5,5));
        setContentPane(panel1);


        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canStart = true;
            }
        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    saladWorks frame = new saladWorks();
                    frame.pack();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
