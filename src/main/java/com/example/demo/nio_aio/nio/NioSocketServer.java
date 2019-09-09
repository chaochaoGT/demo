package com.example.demo.nio_aio.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Filename: NioSocketServer
 * @Description:
 * @Version: 1.0
 * @Author: wangchao
 * @Email: wangchao@hellogeek.com
 * @date: 2019/9/3 ;
 */
public class NioSocketServer {

    private Selector selector;

    private ExecutorService tp= Executors.newCachedThreadPool();

    private Map<Socket, Long> gymt=new HashMap<>(10240);



    class EchoClient{
        private LinkedList<ByteBuffer> outq;
        public EchoClient() {
            this.outq = new LinkedList<ByteBuffer>();
        }
        public LinkedList<ByteBuffer> getOutq() {
            return outq;
        }
        public void addByte(ByteBuffer bb) {
            this.outq.addFirst(bb);
        }
    }

    class EchoMsg implements Runnable{

        private SelectionKey sk;
        ByteBuffer bb;

        public EchoMsg(SelectionKey selectionKey, ByteBuffer bb) {
            this.sk = selectionKey;
            this.bb = bb;
        }

        @Override
        public void run() {
            EchoClient echoClient=(EchoClient)sk.attachment();
            echoClient.addByte(bb);

            sk.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
            //强制唤醒
            selector.wakeup();
        }
    }

    /**
     * 服务器-接收器
     */
    private void doAccept(SelectionKey sk){


    }
    /**
     * 服务器-read
     */
    private void doRead(SelectionKey sk){

    }
    /**
     * 服务器-write
     */
    private void doWrite(SelectionKey sk){

    }
    /**
     * 服务器-connect
     */
    private void doConnect(SelectionKey sk){

    }
    /**
     * 服务器-connect
     */
    public void starServer() throws IOException {
        //创建select
        selector = SelectorProvider.provider().openSelector();
        //创建
        ServerSocketChannel socketChannel=ServerSocketChannel.open();
        //阻塞模式，此处非阻塞
        socketChannel.configureBlocking(false);
        //配置端口
        InetSocketAddress address=new InetSocketAddress(8081);
        //绑定端口
        socketChannel.register(selector,SelectionKey.OP_ACCEPT);
        for (; ; ) {
            //轮询准备好的数据
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();

            Iterator<SelectionKey> it= keys.iterator();
            long e=0L;

            while (it.hasNext()){
                SelectionKey sk = it.next();
                it.remove();
                //接收事件
                if (sk.isAcceptable()){

                }
                else if (sk.isValid()&& sk.isConnectable()){

                }
                else if (sk.isValid() && sk.isReadable()){

                }
                else if (sk.isValid()&& sk.isWritable()){

                }


            }

        }


    }


    //test
    public static void main(String[] args) {

    }
}
