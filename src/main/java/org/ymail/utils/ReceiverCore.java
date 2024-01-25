package org.ymail.utils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.ServerSocket;
import java.net.Socket;


/**
 * 由于对方服务器直接建立连接
 * 并不合适采用mq
 * 采用线程池直接接受
 */
@Slf4j
@Component
@AllArgsConstructor
public class ReceiverCore {
    private final BaseUtils baseUtils;
    public void startReceive() {
        try {
            ServerSocket server = new ServerSocket(25);
            System.out.println("服务器启动等待连接......");
            while (true) {
                // 写日志文件
                // 启动服务
                ThreadPool.getThread(new Receiver(server.accept(),baseUtils));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("服务接收失败");
        }
    }

}
