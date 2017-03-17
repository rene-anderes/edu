package org.anderes.edu.websample;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
    name = "SimpleServlet",
    description = "Einfaches Servlet",
    urlPatterns = {"/SimpleServlet.do"}
)
public class SimpleServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
                    throws IOException, ServletException {

        // 1. process request-parameters and -headers...
        String userName = request.getParameter("userName");
        if (userName == null) {
            userName = "Stranger";
        }
        String client = request.getHeader("User-Agent");
        // 2. prepare the response...
        response.setContentType("text/html");
        // 3. get access to a PrintWriter or to an OutputStream...
        PrintWriter out = response.getWriter();
        // 4. write to PrintWriter or OutputStream...
        out.println("<html>");
        out.println("<head>");
        out.format("<title>%s</title>", this.getServletName());
        out.println("</head>");
        out.println("<body>");
        out.format("Hello %s<br>You are using: %s", userName, client);
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                    throws IOException, ServletException {
        // let doGet() do all the work...
        doGet(request, response);
    }

}