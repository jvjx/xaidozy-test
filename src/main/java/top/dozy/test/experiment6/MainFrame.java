package top.dozy.test.experiment6;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * 登录成功后进入的主界面。
 */
public class MainFrame extends JFrame {
    public MainFrame(String userName) {
        initComponents(userName);
    }

    private void initComponents(String userName) {
        setTitle("实验六 - 主界面");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel welcomeLabel = new JLabel("登录成功，欢迎您：" + userName);
        JButton exitButton = new JButton("退出");

        welcomeLabel.setBounds(85, 55, 250, 35);
        exitButton.setBounds(155, 125, 90, 32);
        exitButton.addActionListener(evt -> System.exit(0));

        add(welcomeLabel);
        add(exitButton);

        setSize(420, 260);
        setResizable(false);
        setLocationRelativeTo(null);
    }
}