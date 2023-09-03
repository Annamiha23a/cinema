package view;

import model.User;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MainFrame extends JFrame{
    private JPanel panelMain;
    private JLabel LoginLabel;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JLabel passwordLabel;
    private JButton clearButton;
    private JButton entranceButton;

    public static Socket clientSocket;
    public static ObjectOutputStream output;
    public static ObjectInputStream input;
    public static int user_id;

    //установление соединения
    public static void connect(){
        try{
            clientSocket = new Socket("127.0.0.1", 2625);
            output = new ObjectOutputStream(clientSocket.getOutputStream());
            input = new ObjectInputStream(clientSocket.getInputStream());
        }
        catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка соединения", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    //инфа об окне
    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Главное меню");
        setContentPane(panelMain);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    public static void createGraph(DefaultCategoryDataset dataSet, String title){
        try {
        JFreeChart chart = ChartFactory.createBarChart(title, "", "", dataSet, PlotOrientation.VERTICAL, false, false, false);
        CategoryPlot catPlot = chart.getCategoryPlot();
        catPlot.setRangeGridlinePaint(Color.BLACK);
        ChartPanel chartPanel = new ChartPanel(chart);
        JFrame frame = new JFrame(title);
        frame.setSize(1000,600);
        JPanel panel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        panel.removeAll();
        panel.add(chartPanel, BorderLayout.CENTER);
        panel.validate();
        frame.add(panel);
        frame.setVisible(true);



            File barChart3D = new File("D:\\barChart3D.jpeg");
            ChartUtilities.saveChartAsJPEG(barChart3D, chart, 700, 600);
        }
        catch (Exception e){
            e.printStackTrace();
        }





    }


    public MainFrame() {
        //создание окна
        initComponents();
        //прослушиватель действий
        entranceButton.addActionListener(new ActionListener() {
            @Override
            //функция обработки действия
            public void actionPerformed(ActionEvent e) {
                try{
                    ObjectOutputStream output = MainFrame.output;
                    ObjectInputStream input = MainFrame.input;
                    User user = new User();
                    user.setLogin(loginField.getText());
                    user.setPassword(passwordField.getText());
                    //не введён логин
                    if(user.getLogin().equals("")){
                        JOptionPane.showMessageDialog(null, "Вы не ввели логин!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    //неподходящая длина логина
                    else if (user.getLogin().length() <= 4 || user.getLogin().length() >= 15){
                        JOptionPane.showMessageDialog(null, "Логин должен быть больше 4 и меньше 15 символов!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    //не ввели пароль
                    else if(user.getPassword().equals("")) {
                        JOptionPane.showMessageDialog(null, "Вы не ввели пароль!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    //неверная длина пароля
                    else if(user.getPassword().length() <= 4 || user.getPassword().length() >= 15) {
                        JOptionPane.showMessageDialog(null, "Пароль должен быть больше 4 и меньше 15 символов!", "Ошибка входа!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    //если
                    else{
                        output.writeObject("authorization");
                        output.writeObject(user);
                        user = (User) input.readObject();
                        if(user.getRole().equals("wrong")){
                            JOptionPane.showMessageDialog(null, "Неправильно введён логин/пароль!", "Ошибка входа!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        else if(user.getRole().equals("admin")) {
                            JOptionPane.showMessageDialog(null, "вход админа", "Ошибка", JOptionPane.ERROR_MESSAGE);
                            new AdminFrame(user.getId()).setVisible(true);
                            dispose();
                        }
                        else if(user.getRole().equals("user")){
                            JOptionPane.showMessageDialog(null, "вход пользователя", "Ошибка", JOptionPane.ERROR_MESSAGE);
                            new UserFrame(user.getId()).setVisible(true);
                           dispose();
                        }

                    }
                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginField.setText("");
                passwordField.setText("");
            }
        });
    }

    public static void main(String args[]) {
        //вызов функции для установления соединения
        MainFrame.connect();
        //передача в другой поток
        java.awt.EventQueue.invokeLater(new Runnable() {
            //делает окно видимым, конструктор класса
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
}