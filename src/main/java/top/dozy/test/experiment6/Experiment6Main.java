package top.dozy.test.experiment6;

import javax.swing.SwingUtilities;

/**
 * 实验六程序入口：Java 数据库访问登录程序。
 */
public class Experiment6Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}