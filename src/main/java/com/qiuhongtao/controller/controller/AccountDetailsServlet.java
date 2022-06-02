package com.Chenziyang.controller;

import com.Chenziyang.dao.OrderDao;
import com.Chenziyang.model.Order;
import com.Chenziyang.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet(name = "AccountDetailsServlet", value = "/accountDetails")
public class AccountDetailsServlet extends HttpServlet {
    Connection con=null;
    @Override
    public void init() throws ServletException {
        super.init();
        con=(Connection) getServletContext().getAttribute("con");
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session=request.getSession(false);
        if(session!=null && session.getAttribute("user")!=null){
            User user=(User) session.getAttribute("user");
            int id=user.getId();
                request.setAttribute("user",user);
                OrderDao orderDao=new OrderDao();
                List<Order> orderList=orderDao.findByUserId(con,id);
                request.setAttribute("orderList",orderList);
                request.getRequestDispatcher("/WEB-INF/views/accountDetails.jsp").forward(request,response);
        }else {
            response.sendRedirect("login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
