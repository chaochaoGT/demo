package com.example.demo.nio_aio.aio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Filename: AioSocketClient
 * @Description:
 * @Version: 1.0
 * @Author: wangchao
 * @Email: wangchao@hellogeek.com
 * @date: 2019/9/2 ;
 */
public class AioSocketServers {
    private static ExecutorService executorService= Executors.newCachedThreadPool();

    public static class EncoClientServer implements Runnable {
        Socket server;

        public EncoClientServer(Socket server) {
            this.server=server;
        }

        @Override
        public void run() {
            if(server==null){
                return;
            }
            BufferedReader reader = null;
            PrintWriter writer = null;
            try {
                 reader=new BufferedReader(new InputStreamReader(server.getInputStream()));
                 writer=new PrintWriter(server.getOutputStream(),true);
                long b=System.currentTimeMillis();
                String s=null;
                while ((s=reader.readLine())!=null){
                    writer.println(s);
                }
                long a=System.currentTimeMillis();
                System.out.println("current is user time ="+(a-b));
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    if (reader!=null){
                        reader.close();
                    }
                    if (writer!=null){
                        writer.close();
                    }
                        server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        ServerSocket serverSocket=null;
        Socket clientServer=null;

        try {
            serverSocket=new ServerSocket(8081);
            while (true){
                try {
                    clientServer=serverSocket.accept();
                    System.out.println("currentes  client is =" +clientServer.getRemoteSocketAddress()+"connontion！！！");
                    executorService.execute(new  EncoClientServer(clientServer));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
