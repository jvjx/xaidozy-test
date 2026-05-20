package top.dozy.test.experiment4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * 实验四界面：左边输入，右边读取，三个按钮。
 */
public class ListFrame extends JFrame {
    private static final String FILE_NAME = "Hello.txt";

    private final JTextArea jTextArea1 = new JTextArea();
    private final JTextArea jTextArea2 = new JTextArea();

    public ListFrame() {
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JScrollPane jScrollPane1 = new JScrollPane(jTextArea1);
        JScrollPane jScrollPane2 = new JScrollPane(jTextArea2);
        JButton jButton1 = new JButton("存入文件");
        JButton jButton2 = new JButton("读取文件");
        JButton jButton3 = new JButton("退出");

        jScrollPane1.setBounds(43, 52, 127, 130);
        jScrollPane2.setBounds(208, 52, 135, 130);
        jButton1.setBounds(79, 200, 90, 30);
        jButton2.setBounds(240, 200, 100, 30);
        jButton3.setBounds(170, 245, 70, 30);

        jButton1.addActionListener(evt -> jButton1ActionPerformed());
        jButton2.addActionListener(evt -> jButton2ActionPerformed());
        jButton3.addActionListener(evt -> System.exit(0));

        add(jScrollPane1);
        add(jScrollPane2);
        add(jButton1);
        add(jButton2);
        add(jButton3);

        setSize(420, 340);
        setLocationRelativeTo(null);
    }

    private void jButton1ActionPerformed() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            bw.write(jTextArea1.getText());
            JOptionPane.showMessageDialog(this, "存入文件成功！");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void jButton2ActionPerformed() {
        jTextArea2.setText("");
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line = br.readLine();
            while (line != null) {
                jTextArea2.append(line + "\n");
                line = br.readLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
