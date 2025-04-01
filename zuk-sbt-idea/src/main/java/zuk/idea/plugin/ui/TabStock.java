package zuk.idea.plugin.ui;

import javax.swing.*;

public class TabStock {
    private JPanel panelMain;
    private JTextField textField1;
    private JTextField textField2;
    private JTextArea textArea1;
    private JButton button1;
    private JPanel panelMain1;

    public JPanel getPanelMain() {
        return panelMain;
    }

    public static void main(String[] args) {
        TabStock tabStock = new TabStock();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(tabStock.getPanelMain());
        frame.setVisible(true);
        frame.setSize(100,200);



    }
}
