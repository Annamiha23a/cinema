package view;

import model.*;
//import tableModel.DoctorTableModel;
//import tableModel.VisitTableModel;

import javax.swing.*;
import java.awt.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PlacesFrame extends JFrame {
    private JPanel mainPanel;
    private JTextField экранTextField;
    private JButton button1;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button2;
    private JButton closeFrameButton;


    private ObjectOutputStream output = MainFrame.output;
    private ObjectInputStream input = MainFrame.input;

    private Places places;

    //-------------------------------ИНИЦИАЛИЗАЦИЯ ФРЕЙМА-------------------------------


    @SuppressWarnings("unchecked")
    private void initComponents() {
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Места");
        setSize(1000, 300);
        setContentPane(mainPanel);
        setResizable(false);





        pack();
        setLocationRelativeTo(null);
    }


    //-------------------------------КОНСТРУКТОР ФРЕЙМА-------------------------------


    public PlacesFrame(int USER_ID, Places place) {

        initComponents();
        closeFrameButton.addActionListener(e -> {
            dispose();});
        if(place.getPhoneNumber() == null || place.getPhoneNumber().equals("")){
            button1.setBackground(Color.GREEN);
            button2.setBackground(Color.GREEN);
            button3.setBackground(Color.GREEN);
            button4.setBackground(Color.GREEN);
            button5.setBackground(Color.GREEN);

        }
        else {
            button1.setBackground(Color.RED);
            button2.setBackground(Color.RED);
            button3.setBackground(Color.RED);
            button4.setBackground(Color.RED);
            button5.setBackground(Color.RED);}

    }


}