package com.jsp.fcte.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.fcte.dao.*;
import com.jsp.fcte.dto.PaginationDTO;
import com.jsp.fcte.modal.*;


@WebServlet("/")
public class Fcte011Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Fcte011DAO accPaymentDAO;

    @Override
    public void  init() throws ServletException {
        ServletContext context = getServletContext();
        accPaymentDAO = new Fcte011DAO(context);
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
			case "/defaultInfo":
				showDefaultInfoForm(request, response);
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
			case "/searchList":
				listAccPayment(request, response);
				break;
			default:
				resetListAccPayment(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

    private void resetListAccPayment(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {

        List<AccPayment> listAccPayment = new ArrayList<>();
        request.setAttribute("listAccPayment", listAccPayment);
        RequestDispatcher dispatcher = request.getRequestDispatcher("list.jsp");
        
        dispatcher.forward(request, response);
    }

    private void listAccPayment(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
    	
        String appId = request.getParameter("appId");
        String channel = request.getParameter("channel");
        String custNameEN = request.getParameter("custNameEN");
        String custNameTH = request.getParameter("custNameTH");
        String custCode = request.getParameter("custcode");
        String cardid = request.getParameter("cardid");
        String marketingId = request.getParameter("marketingId");
        String branch = request.getParameter("branch");

        int currentPage = request.getParameter("currentPage") != null ? Integer.valueOf(request.getParameter("currentPage")) : 1;
        int itemsPerPage = request.getParameter("itemsPerPage")!= null ? Integer.valueOf(request.getParameter("itemsPerPage")) : 5;
        
        PaginationDTO<AccPayment> fcte011DTO = accPaymentDAO.searchListAccPayment(appId, custCode, custNameEN,
        		marketingId, channel, cardid, custNameTH, branch, currentPage, itemsPerPage);
        request.setAttribute("listAccPayment", fcte011DTO.getData());
        
        request.setAttribute("listAccPayment", fcte011DTO.getData());
        request.setAttribute("totalItems", fcte011DTO.getTotalItems());
        request.setAttribute("totalPages", fcte011DTO.getTotalPages());
        request.setAttribute("currentPage", fcte011DTO.getCurrentPage());
        request.setAttribute("itemsPerPage", fcte011DTO.getItemsPerPage());
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("form.jsp");
        dispatcher.forward(request, response);
    }

    private void showDefaultInfoForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
    	System.out.println("showEditForm   showEditForm  showEditForm");
        String cardid = request.getParameter("cardid");
        AccPayment existingAccPayment = accPaymentDAO.defaultInfoAccPayment(cardid);
        RequestDispatcher dispatcher = request.getRequestDispatcher("form.jsp");
        request.setAttribute("accPayment", existingAccPayment);
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
    	System.out.println("showEditForm   showEditForm  showEditForm");
        String cardid = request.getParameter("cardid");
        String custcode = request.getParameter("custcode");
        String account = request.getParameter("account");
        String transtype = request.getParameter("transtype");
        String rptype = request.getParameter("rptype");
        AccPayment existingAccPayment = accPaymentDAO.selectAccPayment(cardid, custcode, account, transtype, rptype);
        RequestDispatcher dispatcher = request.getRequestDispatcher("form.jsp");
        request.setAttribute("accPayment", existingAccPayment);
        dispatcher.forward(request, response);
    }

    private void insertAccPayment(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
    	// Retrieve the form parameters
    	String cardid = request.getParameter("cardid");
        String custacct = request.getParameter("custacct");
        String custcode = request.getParameter("custcode");
        String account = request.getParameter("account");
        String transtype = request.getParameter("transtype");
        String rptype = request.getParameter("rptype");
        String bankcheqcode = request.getParameter("bankcheqcode");
        String bankcode = request.getParameter("bankcode");
        String bankbranchcode = request.getParameter("bankbranchcode");
        String bankaccno = request.getParameter("bankaccno");
        String bankacctype = request.getParameter("bankacctype");
        String bankcheqcodeextra = request.getParameter("bankcheqcodeextra");
        String paytype = request.getParameter("paytype");
        String crosstype = request.getParameter("crosstype");
        
        String effectdateStr = request.getParameter("effectdate");
        System.out.println(effectdateStr);
        Date effectdate = null;
        if (effectdateStr != null && !effectdateStr.isEmpty()) {
        	effectdate = Date.valueOf(effectdateStr);
        }
        
        String enddateStr = request.getParameter("enddate");
        System.out.println(enddateStr);
        Date enddate = null;
        if (enddateStr != null && !enddateStr.isEmpty()) {
        	enddate = Date.valueOf(enddateStr);
        }

        // Create a new AccPayment object with the retrieved values
        AccPayment newAccPayment = new AccPayment(cardid, custacct, custcode, account, transtype, rptype, bankcheqcode,
                bankcode, bankbranchcode, bankaccno, bankacctype, bankcheqcodeextra, paytype, crosstype, effectdate,
                enddate);
        
        // Insert the newAccPayment into the database using accPaymentDAO
        accPaymentDAO.insertAccPayment(newAccPayment);
        
        // Redirect the user to the list page
        response.sendRedirect("searchList?custcode="+ custcode + "&cardid=" + cardid);
    }

    private void updateAccPayment(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
    	// Retrieve the form parameters
        String cardid = request.getParameter("cardid");
        String custacct = request.getParameter("custacct");
        String custcode = request.getParameter("custcode");
        String account = request.getParameter("account");
        String transtype = request.getParameter("transtype");
        String rptype = request.getParameter("rptype");
        String bankcheqcode = request.getParameter("bankcheqcode");
        String bankcode = request.getParameter("bankcode");
        String bankbranchcode = request.getParameter("bankbranchcode");
        String bankaccno = request.getParameter("bankaccno");
        String bankacctype = request.getParameter("bankacctype");
        String bankcheqcodeextra = request.getParameter("bankcheqcodeextra");
        String paytype = request.getParameter("paytype");
        String crosstype = request.getParameter("crosstype");

        System.out.println(cardid);
        System.out.println(custcode);
        
        String effectdateStr = request.getParameter("effectdate");
        System.out.println(effectdateStr);
        Date effectdate = null;
        if (effectdateStr != null && !effectdateStr.isEmpty()) {
        	effectdate = Date.valueOf(effectdateStr);
        }
        
        String enddateStr = request.getParameter("enddate");
        System.out.println(enddateStr);
        Date enddate = null;
        if (enddateStr != null && !enddateStr.isEmpty()) {
        	enddate = Date.valueOf(enddateStr);
        }

        // Create a new AccPayment object with the retrieved values
        AccPayment accPayment = new AccPayment(cardid, custacct, custcode, account, transtype, rptype, bankcheqcode,
                bankcode, bankbranchcode, bankaccno, bankacctype, bankcheqcodeextra, paytype, crosstype, effectdate,
                enddate);
        
        // Update the accPayment in the database using accPaymentDAO
        accPaymentDAO.updateAccPayment(accPayment);
        
        // Redirect the user to the list page
        response.sendRedirect("searchList?custcode="+ custcode + "&cardid=" + cardid);
    }

    private void deleteAccPayment(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String cardid = request.getParameter("cardid");
        String custcode = request.getParameter("custcode");
        String account = request.getParameter("account");
        String transtype = request.getParameter("transtype");
        String rptype = request.getParameter("rptype");
        accPaymentDAO.deleteAccPayment(cardid, custcode, account, transtype, rptype);
        response.sendRedirect("searchList?custcode="+ custcode + "&cardid=" + cardid);
    }

}
