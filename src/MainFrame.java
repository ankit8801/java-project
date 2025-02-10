import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MainFrame extends JFrame {
    final private Font mainFont = new Font("Segoe print",Font.BOLD,18);
    JTextField tfFirstName,tfLastName;
    JLabel lbwelcome;
    

    public void initialize() {

    /*******************Form panel *******************/

    JLabel lbFisrtName = new JLabel("First Name");
    lbFisrtName.setFont(mainFont);

    tfFirstName = new JTextField();
    tfFirstName.setFont(mainFont);

    JLabel lbLastName = new JLabel("Last Name");
    lbFisrtName.setFont(mainFont);

    JPanel formJPanel = new JPanel();
    formJPanel.setLayout(new GridLayout(4, 1, 5, 5));
    formJPanel.add(lbFisrtName);
    formJPanel.add(tfFirstName);
    formJPanel.add(lbLastName);
    formJPanel.add(tfLastName);

    /*********************Welcome Label *********************/
    lbwelcome = new JLabel();
    lbwelcome.setFont(mainFont);
    
    /*********************Buttons panel ********************/
    JButton btnOk = new JButton("OK");
    btnOk.setFont(mainFont);
    btnOk.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
           String firstName = tfFirstName.getText();
           String lastName = tfLastName.getText();
           lbwelcome.setText("Hello" + firstName + "" + lastName);

        }
        
    });

    JButton btnClear = new JButton("Clear");
    btnClear.setFont(mainFont);
    btnClear.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            tfFirstName.setText("");
            tfLastName.setText("");
            lbwelcome.setText("");

        }
        
    });

     
    JPanel buttonsPanel = new JPanel();
    buttonsPanel.setLayout(new GridLayout(1, 2, 5, 5));
    buttonsPanel.add(btnOk);
    buttonsPanel.add(btnClear);


    JPanel mainPanel = new JPanel();
       mainPanel.setLayout(new BorderLayout());
       mainPanel.setBackground(new Color(128,128,255));
       mainPanel.add(formJPanel, BorderLayout.NORTH);  
       mainPanel.add(lbwelcome, BorderLayout.CENTER);
       mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

       add(mainPanel);


       setTitle("Welcome");
       setSize(500, 600);
       setMinimumSize(new Dimension(300,400));
       setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
       setVisible(true);
    }
    public static void main(String[] args) {
      MainFrame myFrame = new MainFrame();
      myFrame.initialize(); 
    }
}