package com.example.redissontest.nio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * description:
 * author: 丁波琪 <dingboqi@css.com.cn>
 * date: 2023/7/7 9:10
 * Copyright: ©China software and Technical service Co.Ltd
 */
public class NioClient {

    public static void main(String[] args) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate("hello world!".getBytes().length);
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1",8083));
        byteBuffer.put("hello world!".getBytes());
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
        socketChannel.close();
        byteBuffer.clear();

        Socket socket = new Socket("127.0.0.1",8083);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("hello world12334!".getBytes());
        outputStream.close();
        socket.close();

    }
}
