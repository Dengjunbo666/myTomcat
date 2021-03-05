package com.dengjunbo.tomcat.http;

import cn.hutool.core.util.StrUtil;
import com.dengjunbo.tomcat.Bootstrap;
import com.dengjunbo.tomcat.catalina.Context;
import com.dengjunbo.tomcat.catalina.Engine;
import com.dengjunbo.tomcat.catalina.Host;
import com.dengjunbo.tomcat.util.MiniBrowser;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Request {

    private String requestString;
    private String uri;
    private Socket socket;
    private Context context;
    private Engine engine;
    public Request(Socket socket, Engine engine) throws IOException {
        this.socket = socket;
        this.engine = engine;
        parseHttpRequest();
        if(StrUtil.isEmpty(requestString))
            return;
        parseUri();
        parseContext();
        if(!"/".equals(context.getPath()))
            uri = StrUtil.removePrefix(uri, context.getPath());

    }

    private void parseContext() {
        String path = StrUtil.subBetween(uri, "/", "/");
        if (null == path)
            path = "/";
        else
            path = "/" + path;

        context = engine.getDefaultHost().getContext(path);
        if (null == context)
            context = engine.getDefaultHost().getContext("/");
    }

    private void parseHttpRequest() throws IOException {
        InputStream is = this.socket.getInputStream();
        byte[] bytes = MiniBrowser.readBytes(is);
        requestString = new String(bytes, "utf-8");
    }

    private void parseUri() {
        String temp;

        temp = StrUtil.subBetween(requestString, " ", " ");
        if (!StrUtil.contains(temp, '?')) {
            uri = temp;
            return;
        }
        temp = StrUtil.subBefore(temp, '?', false);
        uri = temp;
    }

    public Context getContext() {
        return context;
    }

    public String getUri() {
        return uri;
    }

    public String getRequestString(){
        return requestString;
    }

}