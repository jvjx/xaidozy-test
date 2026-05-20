package top.dozy.test.socket;

import java.io.*;   // 导入输入输出流相关的类（BufferedReader, PrintWriter 等）
import java.net.*;  // 导入网络编程相关的类（ServerSocket, Socket 等）

/**
 * 服务端程序 —— Socket 通信中的"接电话的人"
 *
 * 【什么是 Socket？】
 * Socket（套接字）是网络通信的端点，可以理解为"电话"。
 * 两台电脑之间要通信，就像两个人打电话一样：
 *   - 服务端 = 接电话的人（先准备好，等着别人打过来）
 *   - 客户端 = 打电话的人（主动拨号连接服务端）
 *
 * 【通信流程】
 *   1. 服务端在某个端口上"开机等待"（创建 ServerSocket）
 *   2. 客户端"拨号"连接到服务端的 IP + 端口（创建 Socket）
 *   3. 连接建立后，双方通过"输入流"和"输出流"收发数据
 *   4. 通信结束后，双方关闭连接
 *
 * 【运行顺序】先运行 Server，再运行 Client
 *
 * 【重要：收发顺序要配对！】
 *   Server：先读（readLine）→ 再写（println）
 *   Client：先写（println）→ 再读（readLine）
 *   这样一个先发、一个先收，刚好配对，不会卡死。
 *   如果两边都"先读再写"，就会死锁（deadlock）——都在等对方发数据，谁也不发。
 */
public class Server {
    public static void main(String[] args) {
        try {
            // ===== 第1步：创建 ServerSocket，开始监听端口 =====
            // ServerSocket 就像是一部座机电话，12345 是电话号码（端口号）
            // 端口号范围：0-65535，其中 0-1023 是系统保留端口，建议用 1024 以上的
            // 这一步相当于：把电话接好，等着别人打过来
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Server started. Waiting for client...");

            // ===== 第2步：等待客户端连接（阻塞方法） =====
            // accept() 方法会让程序在这里"暂停"，一直等到有客户端连接进来
            // "阻塞"的意思是：代码不会往下执行，就一直卡在这里等
            // 一旦客户端连上了，就会返回一个 Socket 对象，代表和这个客户端的连接
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected.");

            // ===== 第3步：从客户端读取消息（接收数据） =====
            // 要读取客户端发来的数据，需要从 Socket 获取"输入流"（InputStream）
            // 可以理解为：电话的"听筒"，用来听对方说什么
            //
            // clientSocket.getInputStream()  -> 获取原始的字节输入流
            // InputStreamReader(...)         -> 把字节流转成字符流（字节 -> 文字）
            // BufferedReader(...)            -> 加一层缓冲，可以按行读取（readLine）
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

            // readLine() 读取客户端发来的一行文字
            // 如果客户端还没发送数据，这里也会阻塞等待
            String message = in.readLine();
            System.out.println("Client says: " + message);

            // ===== 第4步：向客户端发送响应（发送数据） =====
            // 要向客户端发送数据，需要从 Socket 获取"输出流"（OutputStream）
            // 可以理解为：电话的"话筒"，用来对对方说话
            //
            // clientSocket.getOutputStream()  -> 获取原始的字节输出流
            // PrintWriter(..., true)          -> 包装成更方便的打印流
            //   第二个参数 true 表示"自动刷新"（autoFlush），
            //   即每次调用 println() 后立刻把数据发出去，不会留在缓冲区里
            PrintWriter out = new PrintWriter(
                    clientSocket.getOutputStream(), true);
            out.println("Hello Client!"); // 向客户端发送一行文字

            // ===== 第5步：关闭连接 =====
            // 通信结束后要关闭资源，就像打完电话要挂机一样
            // 注意关闭顺序：先关客户端连接，再关服务端
            clientSocket.close();   // 关闭与客户端的连接
            serverSocket.close();   // 关闭服务端，不再接受新连接

        } catch (IOException e) {
            // 如果在通信过程中出现了异常（比如端口被占用、连接断开等），
            // 就会进入这里，打印错误信息方便排查问题
            e.printStackTrace();
        }
    }
}