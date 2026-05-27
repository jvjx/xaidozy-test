package top.dozy.test.experiment6;

import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * 登录界面：将输入的用户名和密码与 MySQL 数据库中的数据进行比较。
 */
public class LoginFrame extends JFrame {
    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/userdatabase"
            + "?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
    private static final String MYSQL_USER = "root";
    private static final String MYSQL_PASSWORD = "123456";

    private final JTextField userField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();
    private int errorCount = 0;

    public LoginFrame() {
        initComponents();
    }

    private void initComponents() {
        setTitle("实验六 - 数据库登录");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel titleLabel = new JLabel("用户登录");
        JLabel userLabel = new JLabel("用户名：");
        JLabel passwordLabel = new JLabel("密码：");
        JButton okButton = new JButton("确定");
        JButton cancelButton = new JButton("取消");

        titleLabel.setBounds(165, 25, 80, 30);
        userLabel.setBounds(75, 75, 80, 30);
        userField.setBounds(150, 75, 170, 30);
        passwordLabel.setBounds(75, 120, 80, 30);
        passwordField.setBounds(150, 120, 170, 30);
        okButton.setBounds(100, 185, 90, 32);
        cancelButton.setBounds(220, 185, 90, 32);

        okButton.addActionListener(this::okButtonActionPerformed);
        cancelButton.addActionListener(evt -> System.exit(0));

        add(titleLabel);
        add(userLabel);
        add(userField);
        add(passwordLabel);
        add(passwordField);
        add(okButton);
        add(cancelButton);

        setSize(420, 300);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void okButtonActionPerformed(ActionEvent evt) {
        String userName = userField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (userName.isEmpty() || password.isEmpty()) {
            handleLoginFailure("用户名和密码不能为空！");
            return;
        }

        try {
            if (checkLogin(userName, password)) {
                MainFrame mainFrame = new MainFrame(userName);
                mainFrame.setVisible(true);
                dispose();
            } else {
                handleLoginFailure("输入错误，请重新输入");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "数据库连接或查询失败，请检查 MySQL 服务、数据库和表是否正确。", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean checkLogin(String userName, String password) throws SQLException {
        String sql = "select * from `user` where `Name` = ? and `Password` = ?";
        try (Connection connection = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    private void handleLoginFailure(String message) {
        errorCount++;
        if (errorCount >= 3) {
            JOptionPane.showMessageDialog(this, "您已经输入三次错误密码，登录失败！系统退出！");
            System.exit(0);
        }
        JOptionPane.showMessageDialog(this, message);
        passwordField.setText("");
        passwordField.requestFocusInWindow();
    }
}