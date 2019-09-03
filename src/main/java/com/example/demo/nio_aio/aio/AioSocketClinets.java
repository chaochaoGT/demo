package com.example.demo.nio_aio.aio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.LockSupport;

/**
 * @Filename: AioSocketServer
 * @Description:
 * @Version: 1.0
 * @Author: wangchao
 * @Email: wangchao@hellogeek.com
 * @date: 2019/9/2 ;
 */
public class AioSocketClinets {
    private static ExecutorService executorService= Executors.newCachedThreadPool();
    private static long time=1000*1000*1000;

    public static class EchoClient implements Runnable{
        private  int i;
        public EchoClient(int i) {
            this.i=i;
        }

        @Override
        public void run() {
            Socket client=new Socket();
            PrintWriter writer=null;
            BufferedReader reader=null;
            try {
                client.connect(new InetSocketAddress("localhost", 8081));
                writer=new PrintWriter(client.getOutputStream(),true);
                writer.print("w");
                LockSupport.parkNanos(time);
                writer.print("a");
                LockSupport.parkNanos(time);
                writer.print("n");
                LockSupport.parkNanos(time);
                writer.print("g");
                LockSupport.parkNanos(time);
                writer.print("w");
                LockSupport.parkNanos(time);
                writer.print("c");
                LockSupport.parkNanos(time);
                writer.print("h");
                LockSupport.parkNanos(time);
                writer.print("a");
                LockSupport.parkNanos(time);
                writer.print("o");
                LockSupport.parkNanos(time);
                writer.println(i);
                writer.flush();

                reader=new BufferedReader(new InputStreamReader(client.getInputStream()));
                System.out.println(reader.readLine());
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
                    if (client!=null){
                        client.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            executorService.execute(new EchoClient(i));
        }
    }

}
