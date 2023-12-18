package com.example.redissontest.nio;

import org.springframework.expression.spel.ast.Selection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * description:
 * author: 丁波琪 <dingboqi@css.com.cn>
 * date: 2023/7/7 9:10
 * Copyright: ©China software and Technical service Co.Ltd
 */
public class NioServer {

    public static void main(String[] args) throws IOException {

        Selector selector = Selector.open();

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);


        ServerSocket socket = serverSocketChannel.socket();
        SocketAddress socketAddress = new InetSocketAddress("127.0.0.1",8083);
        socket.bind(socketAddress);


        while(true){
            selector.select();
            Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();
            while(selectionKeyIterator.hasNext()){
                SelectionKey selectionKey = selectionKeyIterator.next();
                if(selectionKey.isAcceptable()){
                    //监听连接
                    ServerSocketChannel serverSocketChannel1 = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel socketChannel = serverSocketChannel1.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }else if(selectionKey.isReadable()){
                    //监听读
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    System.out.println("服务器接收到消息 : " + readMessage(socketChannel));
                    socketChannel.close();
                }
            }
            selectionKeyIterator.remove();
        }
    }

    public static String readMessage(SocketChannel channel) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        StringBuffer stringBuffer = new StringBuffer();
        while(true){
            int s = channel.read(byteBuffer);
            if(s == -1){
                break;
            }
            byteBuffer.flip();
            char[] result = new char[byteBuffer.limit()];
            for(int i=0;i<byteBuffer.limit();i++){
                result[i] = (char) byteBuffer.get(i);
            }
            stringBuffer.append(result);
        }
        return stringBuffer.toString();
    }
}
