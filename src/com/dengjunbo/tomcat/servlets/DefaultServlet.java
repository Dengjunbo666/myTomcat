package com.dengjunbo.tomcat.servlets;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import com.dengjunbo.tomcat.catalina.Context;
import com.dengjunbo.tomcat.http.Request;
import com.dengjunbo.tomcat.http.Response;
import com.dengjunbo.tomcat.util.Constant;
import com.dengjunbo.tomcat.util.WebXMLUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class DefaultServlet extends HttpServlet {
    private static DefaultServlet instance = new DefaultServlet();

    public static synchronized DefaultServlet getInstance() {
        return instance;
    }

    private DefaultServlet() {

    }

    public void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException, ServletException {
        Request request = (Request) httpServletRequest;
        Response response = (Response) httpServletResponse;

        Context context = request.getContext();

        String uri = request.getUri();
        if ("/500.html".equals(uri))
            throw new RuntimeException("this is a deliberately created exception");

        if ("/".equals(uri))
            uri = WebXMLUtil.getWelcomeFile(request.getContext());

        if(uri.endsWith(".jsp")){
            JspServlet.getInstance().service(request,response);
            return;
        }

        String fileName = StrUtil.removePrefix(uri, "/");
        File file = FileUtil.file(request.getRealPath(fileName));

        if (file.exists()) {
            String extName = FileUtil.extName(file);
            String mimeType = WebXMLUtil.getMimeType(extName);
            response.setContentType(mimeType);

            byte body[] = FileUtil.readBytes(file);
            response.setBody(body);

            if (fileName.equals("timeConsume.html"))
                ThreadUtil.sleep(1000);

            response.setStatus(Constant.CODE_200);
        } else {
            response.setStatus(Constant.CODE_404);
        }

    }

}