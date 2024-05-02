package com.jsp.creditmanagement.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.creditmanagement.dao.*;
import com.jsp.creditmanagement.modal.*;


@WebServlet("/")
public class CreditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CreditDAO creditDAO;
	
	public void init() {
		creditDAO = new CreditDAO();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();

		try {
			switch (action) {
			case "/new":
				showNewForm(request, response);
				break;
			case "/insert":
				insertCredit(request, response);
				break;
			case "/delete":
				deleteCredit(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/update":
				updateCredit(request, response);
				break;
			default:
				listCredit(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

    private void listCredit(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Credit> listCredit = creditDAO.selectAllCredits();
        request.setAttribute("listCredit", listCredit);
        RequestDispatcher dispatcher = request.getRequestDispatcher("credit-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("credit-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String accountno = request.getParameter("accountno");
        Credit existingCredit = creditDAO.selectCredit(accountno);
        RequestDispatcher dispatcher = request.getRequestDispatcher("credit-form.jsp");
        request.setAttribute("credit", existingCredit);
        dispatcher.forward(request, response);
    }

    private void insertCredit(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String accountno = request.getParameter("accountno");
        String transtype = request.getParameter("transtype");
        
        String effectDateStr = request.getParameter("effectdate");
        java.sql.Date sqlEffectDate = null;
        if(effectDateStr != null && effectDateStr != "")
	        try {
	            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	            java.util.Date utilEffectDate = dateFormat.parse(effectDateStr);
	            sqlEffectDate = new Date(utilEffectDate.getTime());
	        } catch (ParseException e) {
	            // Handle parsing exception
	            e.printStackTrace();
	        }
        
        BigDecimal amount = request.getParameter("amount") != "" ? new BigDecimal(request.getParameter("amount")) : null;
        String remark = request.getParameter("remark");
        
        Credit newCredit = new Credit(accountno, transtype, sqlEffectDate, amount, remark);
        creditDAO.insertCredit(newCredit);
        response.sendRedirect("list");
    }

    private void updateCredit(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String accountno = request.getParameter("accountno");
        String transtype = request.getParameter("transtype");
        
        String effectDateStr = request.getParameter("effectdate");
        java.sql.Date sqlEffectDate = null;
        System.out.print(effectDateStr);
        if(effectDateStr != null && effectDateStr != "")
	        try {
	            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	            java.util.Date utilEffectDate = dateFormat.parse(effectDateStr);
	            sqlEffectDate = new Date(utilEffectDate.getTime());
	        } catch (ParseException e) {
	            // Handle parsing exception
	            e.printStackTrace();
	        }

        BigDecimal amount = request.getParameter("amount") != "" ? new BigDecimal(request.getParameter("amount")) : null;
        String remark = request.getParameter("remark");

        Credit credit = new Credit(accountno, transtype, sqlEffectDate, amount, remark);
        creditDAO.updateCredit(credit);
        response.sendRedirect("list");
    }

    private void deleteCredit(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String accountno = request.getParameter("accountno");
        creditDAO.deleteCredit(accountno);
        response.sendRedirect("list");
    }

}
