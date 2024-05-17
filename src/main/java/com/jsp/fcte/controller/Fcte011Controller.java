package com.jsp.fcte.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
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

import com.jsp.fcte.dao.*;
import com.jsp.fcte.modal.*;


@WebServlet("/")
public class Fcte011Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Fcte011DAO accPaymentDAO;
	
	public void init() {
		accPaymentDAO = new Fcte011DAO();
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
				insertAccPayment(request, response);
				break;
			case "/delete":
				deleteAccPayment(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/update":
				updateAccPayment(request, response);
				break;
			default:
				listAccPayment(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

    private void listAccPayment(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
    	
        String appId = getNonNullString(request, "appId");
        String custCode = getNonNullString(request, "custId");
        String custName = getNonNullString(request, "custName");
        String marketingId = getNonNullString(request, "marketingId");
        String channel = getNonNullString(request, "channel");
        String cardId = getNonNullString(request, "cardId");
        String fullName = getNonNullString(request, "fullName");
        String branch = getNonNullString(request, "branch");
        
        List<AccPayment> listAccPayment = accPaymentDAO.seachListAccPayment(appId, custCode, custName,
        		marketingId, channel, cardId, fullName, branch);
        request.setAttribute("listAccPayment", listAccPayment);
        RequestDispatcher dispatcher = request.getRequestDispatcher("list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        System.out.println("goin showEditForm");
        String custcode = getNonNullString(request, "custcode");
        String account = getNonNullString(request, "account");
        String transtype = getNonNullString(request, "transtype");
        String rptype = getNonNullString(request, "rptype");
        AccPayment existingAccPayment = accPaymentDAO.selectAccPayment(custcode, account, transtype, rptype);
        RequestDispatcher dispatcher = request.getRequestDispatcher("form.jsp");
        request.setAttribute("accPayment", existingAccPayment);
        dispatcher.forward(request, response);
    }

    private void insertAccPayment(HttpServletRequest request, HttpServletResponse response)
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
        
        AccPayment newAccPayment = new AccPayment();
        accPaymentDAO.insertAccPayment(newAccPayment);
        response.sendRedirect("list");
    }

    private void updateAccPayment(HttpServletRequest request, HttpServletResponse response)
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

        AccPayment accPayment = new AccPayment();
        accPaymentDAO.updateAccPayment(accPayment);
        response.sendRedirect("list");
    }

    private void deleteAccPayment(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String accountno = request.getParameter("accountno");
        accPaymentDAO.deleteAccPayment(accountno);
        response.sendRedirect("list");
    }
    
    private String getNonNullString(HttpServletRequest rq, String columnLabel) throws SQLException {
        String value = rq.getParameter(columnLabel);
        return value != null && !value.isEmpty() ? value : null;
    }

}
