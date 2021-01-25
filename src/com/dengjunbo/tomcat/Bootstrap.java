package com.dengjunbo.tomcat;

import cn.hutool.core.util.NetUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Bootstrap {
    public static void main(String[] args) {
        try {
            int port = 18080;
            //NetUtil.isUsableLocalPort用来判断端口是否被占用
            if (!NetUtil.isUsableLocalPort(port)){
                System.out.println(port+"端口已经被占用了，排查并关闭本端口的办法请用：\r\nhttps://www.dengjunbo.xyz");
                return;
            }
            ServerSocket ss = new ServerSocket(port);
            //外面还套了一层循环，处理掉一个Socket链接请求之后，再处理下一个链接请求。
            while (true){
                //收到一个浏览器客户端的请求
                Socket s = ss.accept();
                //打开输入流，准备接受浏览器提交的信息
                InputStream is = s.getInputStream();

                int bufferSize = 1024;
                byte[]buffer = new byte[bufferSize];
                is.read(buffer);
                //把字节数组转换成字符串，并且打印出来
                String requestString = new String(buffer,"utf-8");
                System.out.println("浏览器的输入信息：\r\n"+requestString);
                //打开输出流，准备给客户端输出信息
                OutputStream os = s.getOutputStream();
                String response_head = "HTTP/1.1 200 OK \r\n"+"Content-Type:text/html\r\n\r\n";
                String responseString = "Hello DIY Tomcat from how2j.cn";
                responseString = response_head + responseString;
                os.write(responseString.getBytes());
                os.flush();
                s.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
