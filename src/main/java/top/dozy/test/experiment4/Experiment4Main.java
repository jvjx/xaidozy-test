package top.dozy.test.experiment4;

import javax.swing.SwingUtilities;

/**
 * 实验四程序入口。
 */
public class Experiment4Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ListFrame().setVisible(true));
    }
}
