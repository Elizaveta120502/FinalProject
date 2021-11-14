package com.epam.jwd.web;

import com.epam.jwd.logger.LoggerProvider;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/servlet")
public class MainServlet extends HttpServlet {

    private static final long serialVersionUID = 3937496878618720002L;
   @Override

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  {
       try (final PrintWriter writer = resp.getWriter()) {
           writer.println("<h1> Hello from class</h1>");
           writer.println("<b> hey </b>");
       } catch (IOException e) {
           LoggerProvider.getLOG().error("io exception");
       }
   }
}
