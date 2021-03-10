package com.dengjunbo.javaweb;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ClientJumpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("hello");
    }
}