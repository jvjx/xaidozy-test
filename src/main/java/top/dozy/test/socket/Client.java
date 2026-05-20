package top.dozy.test.socket;

import java.io.*;   // 导入输入输出流相关的类（BufferedReader, PrintWriter 等）
import java.net.*;  // 导入网络编程相关的类（Socket 等）

/**
 * 客户端程序 —— Socket 通信中的"打电话的人"
 *
 * 客户端主动去连接服务端，就像主动拨打别人的电话号码。
 * 需要知道两个信息：
 *   1. 服务端的 IP 地址（打给谁）  -> 这里用 "localhost" 表示本机
 *   2. 服务端的端口号（电话号码）  -> 这里用 12345，和 Server 中保持一致
 *
 * 【运行顺序】先运行 Server，再运行 Client
 *   如果先运行 Client 会报错：Connection refused（服务端还没准备好）
 *
 * 【重要：收发顺序要配对！】
 *   Client：先写（println）→ 再读（readLine）
 *   Server：先读（readLine）→ 再写（println）
 *   这样一个先发、一个先收，刚好配对，不会卡死。
 *   如果两边都"先读再写"，就会死锁（deadlock）——都在等对方发数据，谁也不发。
 */
public class Client {
    public static void main(String[] args) {
        try {
            // ===== 第1步：创建 Socket，主动连接服务端 =====
            // new Socket("localhost", 12345) 做了两件事：
            //   1. 创建一个 Socket 对象
            //   2. 立刻尝试连接到 localhost（本机）的 12345 端口
            //
            // "localhost" 是一个特殊的地址，表示"自己这台电脑"
            //   等价于 IP 地址 "127.0.0.1"
            //   如果要连接其他电脑，把它换成对方的 IP 地址即可
            //
            // 如果服务端没启动，这里会抛出 ConnectException: Connection refused
            Socket socket = new Socket("localhost", 12345);

            // ===== 第2步：向服务端发送消息（发送数据） =====
            // 从 Socket 获取"输出流"，相当于拿起"话筒"说话
            //
            // socket.getOutputStream()   -> 获取原始字节输出流
            // PrintWriter(..., true)     -> 包装成方便使用的打印流
            //   第二个参数 true = autoFlush（自动刷新）
            //   这样 println() 之后数据会立刻发送出去
            //   如果设为 false，数据可能会留在缓冲区，对方收不到
            PrintWriter out = new PrintWriter(
                    socket.getOutputStream(), true);
            out.println("Hello Server!"); // 发送一行文字给服务端

            // ===== 第3步：接收服务端的响应（接收数据） =====
            // 从 Socket 获取"输入流"，相当于拿起"听筒"听对方说什么
            //
            // socket.getInputStream()        -> 获取原始字节输入流
            // InputStreamReader(...)          -> 字节流 -> 字符流（解码）
            // BufferedReader(...)             -> 加缓冲，支持 readLine() 按行读取
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            // readLine() 会阻塞等待，直到服务端发来一行数据
            String response = in.readLine();
            System.out.println("Server response: " + response);

            // ===== 第4步：关闭连接 =====
            // 通信结束，关闭 Socket 释放资源
            // 关闭 Socket 后，与之关联的输入流和输出流也会自动关闭
            socket.close();

        } catch (IOException e) {
            // 捕获可能出现的异常，比如：
            //   - ConnectException: 服务端没启动，连接被拒绝
            //   - SocketException: 通信过程中连接意外断开
            e.printStackTrace();
        }
    }
}

