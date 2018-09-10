package myservices;

import net.sf.json.JSONObject;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Validator extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        String csrfToken = SimpleStorage.getSimpleStorage().getItem(cookies[0].getValue());
        //System.out.println("Token inside doGet Method in"+csrfToken);
        resp.setContentType("application/json");
        JSONObject member = new JSONObject();
        member.put("csrfToken",csrfToken);
        PrintWriter pw =resp.getWriter();
        pw.print(member.toString());
        //pw.print(csrfToken);
        pw.flush();
        pw.close();
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        serviceNew(req, resp);
    }


    protected void serviceNew(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        try {
            String id = req.getParameter("id");
            String key = req.getParameter("key");
            String CSRFTokenRecieved = req.getParameter("tokentxt");
            out.println("Registration Number :" + id);
            out.println("Password :" + key);
            out.println("Token :" + CSRFTokenRecieved);
            Cookie[] cookies = req.getCookies();
            String CSRFToken = SimpleStorage.getSimpleStorage().getItem(cookies[0].getValue());
            if (CSRFTokenRecieved.equals(CSRFToken)) {
                out.println("Successfully Verfied !!");
            } else {
                out.println("Verification Failed !!");
            }
        }finally {
            out.close();
        }

    }
}



