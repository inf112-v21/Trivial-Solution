package GUIMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIRegister implements ActionListener {

    int count = 0;
    private JLabel label;
    private JFrame frame;
    private JPanel panel;
    private JButton button;
    private JButton button1;

    public static void main(String[] args){
        new GUIRegister();
    }
    public GUIRegister(){
        frame = new JFrame();
        button = new JButton("Click me");
        button.addActionListener(this);
        label = new JLabel("Number of clicks: 0");
        button1 = new JButton("Reset");
        button1.addActionListener(this);
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(300,300,100,300));
        panel.setLayout(new GridLayout(0,1));
        panel.add(button);
        panel.add(label);
        panel.add(button1);

        frame.add(panel,BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle(("GUIRegister Test"));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button1){
            count = 0;
        }
        else {
            count++;
        }
        label.setText("Number of clicks: "+count);
    }
}
