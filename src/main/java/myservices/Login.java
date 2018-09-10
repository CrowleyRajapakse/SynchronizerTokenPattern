package myservices;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

public class Login extends HttpServlet{

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //generate a session id and store it as a cookie in the browser
        String sessionid = UUID.randomUUID().toString();
        Cookie c1 = new Cookie("sessionid", sessionid);
        c1.setMaxAge(3600); //1 hour
        c1.setSecure(false);
        resp.addCookie(c1);
        service(req, resp);
    }

    private static String generateCSRFToken(){
        String CSRFToken = null;
        byte[] bytes = new byte[16];
        try {
            SecureRandom secureRandom = SecureRandom.getInstanceStrong();//only works with Java 8
            secureRandom.nextBytes(bytes);
            CSRFToken = new String(Base64.getEncoder().encode(bytes));//only works with Java 8
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
      // Console.log("  ");
        return CSRFToken;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        try {
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            Cookie[] cookies = req.getCookies();//request for auto generated cookies in the browser
            cookies[0].setPath("/");

            if (username.equals("admin") && password.equals("admin")) {
                HttpSession session = req.getSession();
                session.setAttribute("username", username);
                SimpleStorage.getSimpleStorage().addItem(cookies[0].getValue(), generateCSRFToken());
                System.out.println("Inside Service Method in Login CSRF token="+SimpleStorage.getSimpleStorage().getItem(cookies[0].getValue()));
                SimpleStorage.getSimpleStorage().print();
                resp.sendRedirect("home.jsp");
            } else {
                out.println("Invalid username or password | try 'admin' for both username and password");
            }
        }finally {
            out.close();
        }

    }
}
