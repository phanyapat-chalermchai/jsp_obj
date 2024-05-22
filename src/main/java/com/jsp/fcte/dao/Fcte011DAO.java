package com.jsp.fcte.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import com.jsp.fcte.dto.PaginationDTO;
import com.jsp.fcte.modal.*;

public class Fcte011DAO {
    private String jdbcURL;
    private String jdbcUsername;
    private String jdbcPassword;
    private String jdbcDriver;


    private static final String INSERT_FCTE011_SQL = "INSERT INTO taccpayment (cardid, custacct, custcode, account, transtype, rptype, bankcheqcode, bankcode, bankbranchcode, "
    		+ "bankaccno, bankacctype, bankcheqcodeextra, paytype, crosstype, effdate, enddate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    private static final String SELECT_ALL_FCTE011 = "SELECT ap.* FROM taccpayment ap LEFT JOIN tcustcode cc ON ap.custcode = cc.custcode ";
    
    public Fcte011DAO(ServletContext context) {
        this.jdbcURL = context.getInitParameter("jdbcURL");
        this.jdbcUsername = context.getInitParameter("jdbcUsername");
        this.jdbcPassword = context.getInitParameter("jdbcPassword");
        this.jdbcDriver = context.getInitParameter("jdbcDriver");
    }


    protected Connection getConnection() {
        Connection connection = null;
        try {
            // Load and register the JDBC driver
            Class.forName(jdbcDriver);
            DriverManager.registerDriver(new com.informix.jdbc.IfxDriver());
            // Open a connection
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } 
        return connection;
    }

    // Select all accPayments
    public PaginationDTO<AccPayment> searchListAccPayment(String inputAppId, String inputCustCode, String inputCustNameEN, String inputMarketingId, 
    		String inputChannel, String inputCardid, String inputCustNameTH, String inputBranch, int currentPage, int itemsPerPage) {
    	
    	String WHERE_CLAUSE = "";
        boolean hasPreviousCondition = false;
            
        if (inputAppId != null && !inputAppId.isEmpty()) {
        	WHERE_CLAUSE += " cc.settleid = '" + inputAppId + "'";
        	hasPreviousCondition = true;
        }
    	
	    if (inputChannel != null && !inputChannel.isEmpty()) {
	    	if (hasPreviousCondition) {
	    		WHERE_CLAUSE += " AND";
	    	}
	    	if (inputChannel.equals("ONLINE"))
	    		WHERE_CLAUSE += " (DATE(SYSDATE) BETWEEN DATE(cc.lineeffective) AND DATE(cc.lineexpire))";
	    	else
		    	WHERE_CLAUSE += " (DATE(SYSDATE) NOT BETWEEN DATE(cc.lineeffective) AND DATE(cc.lineexpire))";
	    	hasPreviousCondition = true;
	    }

    	WHERE_CLAUSE += addSqlStringCondition(inputCustCode, "ap.custcode", hasPreviousCondition);
	    if (!WHERE_CLAUSE.isEmpty()) hasPreviousCondition = true;
    	WHERE_CLAUSE += addSqlStringCondition(inputCardid, "ap.cardid", hasPreviousCondition);
	    if (!WHERE_CLAUSE.isEmpty()) hasPreviousCondition = true;
    	WHERE_CLAUSE += addSqlStringCondition(inputCustNameEN, "cc.efullname", hasPreviousCondition);
	    if (!WHERE_CLAUSE.isEmpty()) hasPreviousCondition = true;
    	WHERE_CLAUSE += addSqlStringCondition(inputCustNameTH, "cc.lfullname", hasPreviousCondition);
	    if (!WHERE_CLAUSE.isEmpty()) hasPreviousCondition = true;
    	WHERE_CLAUSE += addSqlStringCondition(inputMarketingId, "cc.mktid", hasPreviousCondition);
	    if (!WHERE_CLAUSE.isEmpty()) hasPreviousCondition = true;
    	WHERE_CLAUSE += addSqlStringCondition(inputBranch, "cc.branch", hasPreviousCondition);
	    if (!WHERE_CLAUSE.isEmpty()) hasPreviousCondition = true;
      

        // Calculate OFFSET and FETCH
        int offset = (currentPage - 1) * itemsPerPage;
        String CUSTOM_SELECT_ALL_FCTE011 = "SELECT" + " SKIP " + offset + " FIRST " + itemsPerPage + " ap.* FROM taccpayment ap LEFT JOIN tcustcode cc ON ap.custcode = cc.custcode ";
        
        if(hasPreviousCondition)
        	CUSTOM_SELECT_ALL_FCTE011 += " WHERE " + WHERE_CLAUSE;

//        System.out.println("9.59am_________________________");
//        System.out.println(CUSTOM_SELECT_ALL_FCTE011);
        
        
        List<AccPayment> accPayments = new ArrayList<>();
        int totalItems = 0;
        
        try (Connection connection = getConnection();
        	PreparedStatement preparedStatement = connection.prepareStatement(CUSTOM_SELECT_ALL_FCTE011)) {
        		ResultSet rs = preparedStatement.executeQuery();
	          	while (rs.next()) {
	                String cardid = rs.getString("cardid");
	                String custacct = rs.getString("custacct");
	                String custcode = rs.getString("custcode");
	                String account = rs.getString("account");
	                String transtype = rs.getString("transtype");
	                String rptype = rs.getString("rptype");
	                String bankcheqcode = rs.getString("bankcheqcode");
	                String bankcode = rs.getString("bankcode");
	                String bankbranchcode = rs.getString("bankbranchcode");
	                String bankaccno = rs.getString("bankaccno");
	                String bankacctype = rs.getString("bankacctype");
	                String bankcheqcodeextra = rs.getString("bankcheqcodeextra");
	                String paytype = rs.getString("paytype");
	                String crosstype = rs.getString("crosstype");
	                Date effdate = rs.getDate("effdate");
	                Date enddate = rs.getDate("enddate");
	                accPayments.add(new AccPayment(cardid, custacct, custcode, account, transtype, rptype, bankcheqcode, bankcode,
                            bankbranchcode, bankaccno, bankacctype, bankcheqcodeextra, paytype, crosstype,
                            effdate, enddate));
	          	}
	          	// Get total items count
	            String countQuery = "SELECT COUNT(*) FROM (" + SELECT_ALL_FCTE011 + (hasPreviousCondition ? " WHERE " : "") + WHERE_CLAUSE + ")";
//	            System.out.println(countQuery);
	            try (PreparedStatement countStatement = connection.prepareStatement(countQuery)) {
	                ResultSet countRs = countStatement.executeQuery();
	                if (countRs.next()) {
	                    totalItems = countRs.getInt(1);
	                }
	            }
        } catch (SQLException e) {
        	printSQLException(e);
        }

        // Calculate total pages
        int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);

        // Initialize the PaginationDTO object
        PaginationDTO<AccPayment> fcteDTO = new PaginationDTO<>(accPayments, totalItems, totalPages, currentPage, itemsPerPage);
        
        
        return fcteDTO;
    }

    public AccPayment selectAccPayment(String inputCardid, String inputCustcode,String inputAccount, String inputTranstype, String inputRptype) {
        AccPayment accPayment = null;
        String SELECT_FCTE011_BY_ID;

        if (inputCardid == null || inputCardid.isEmpty()) {
        	SELECT_FCTE011_BY_ID = "SELECT FIRST 1 * FROM taccpayment WHERE custcode = ? and account = ? and transtype = ? and rptype = ?";
        } else {
        	SELECT_FCTE011_BY_ID = "SELECT FIRST 1 * FROM taccpayment WHERE cardid = ? and custcode = ? and account = ? and transtype = ? and rptype = ?";
        }
        
//        System.out.println("_________start__selectAccPayment_____10.19am_____________");
//        System.out.println(inputCardid);
//        System.out.println(inputCustcode);
//        System.out.println(inputAccount);
//        System.out.println(inputTranstype);
//        System.out.println(inputRptype);
//        System.out.println(SELECT_FCTE011_BY_ID);
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FCTE011_BY_ID)) {
	            int paramIndex = 1;
	            if (inputCardid != null && !inputCardid.isEmpty()) {
	                preparedStatement.setString(paramIndex++, inputCardid);
	            }
	            preparedStatement.setString(paramIndex++, inputCustcode);
	            preparedStatement.setString(paramIndex++, inputAccount);
	            preparedStatement.setString(paramIndex++, inputTranstype);
	            preparedStatement.setString(paramIndex, inputRptype);
	            ResultSet rs = preparedStatement.executeQuery();
	          	while (rs.next()) {
	                String cardid = rs.getString("cardid");
	                String custacct = rs.getString("custacct");
	                String custcode = rs.getString("custcode");
	                String account = rs.getString("account");
	                String transtype = rs.getString("transtype");
	                String rptype = rs.getString("rptype");
	                String bankcheqcode = rs.getString("bankcheqcode");
	                String bankcode = rs.getString("bankcode");
	                String bankbranchcode = rs.getString("bankbranchcode");
	                String bankaccno = rs.getString("bankaccno");
	                String bankacctype = rs.getString("bankacctype");
	                String bankcheqcodeextra = rs.getString("bankcheqcodeextra");
	                String paytype = rs.getString("paytype");
	                String crosstype = rs.getString("crosstype");
	                Date effdate = rs.getDate("effdate");
	                Date enddate = rs.getDate("enddate");
	
	                accPayment = new AccPayment(cardid, custacct, custcode, account, transtype, rptype, bankcheqcode, bankcode,
	                        bankbranchcode, bankaccno, bankacctype, bankcheqcodeextra, paytype, crosstype,
	                        effdate, enddate);
	            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return accPayment;
    }
    

    public AccPayment defaultInfoAccPayment(String inputCardid) {
        AccPayment accPayment = null;
        String DEFAULT_FCTE011_BY_ID = "SELECT cc.cardid, cc.custcode, ol.custacct, ol.bankcode, ol.bankbranchcode, ol.bankaccno, bb.bankcheqcode"
        		+ " FROM tcustcode cc"
        		+ "	left join jopenonline ol on cc.custcode = ol.custcode"
        		+ "	left join tbankbranch bb on ol.bankcode = bb.bankcode and ol.bankbranchcode = bb.bankbranchcode"
        		+ " WHERE cc.cardid = ?";

        System.out.println(inputCardid);
        System.out.println("_________start__defaultInfoAccPayment_____2.18am___________");
        System.out.println(DEFAULT_FCTE011_BY_ID);
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DEFAULT_FCTE011_BY_ID)) {
	                preparedStatement.setString(1, inputCardid);
	            ResultSet rs = preparedStatement.executeQuery();
	          	while (rs.next()) {
	                String cardid = rs.getString("cardid");
	                String custacct = rs.getString("custacct");
	                String custcode = rs.getString("custcode");
	                String bankcheqcode = rs.getString("bankcheqcode");
	                String bankcode = rs.getString("bankcode");
	                String bankbranchcode = rs.getString("bankbranchcode");
	                String bankaccno = rs.getString("bankaccno");
	
	                accPayment = new AccPayment(cardid, custacct, custcode, null, null, null, bankcheqcode, bankcode,
	                        bankbranchcode, bankaccno, null, null, null, null,
	                        null, null);
	            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return accPayment;
    }

    public void insertAccPayment(AccPayment accPayment) throws SQLException {
//    	System.out.println(INSERT_FCTE011_SQL);
//    	System.out.println("INSERT_FCTE011_SQL_10.32PM");
        Connection connection = null;
        PreparedStatement preparedExistStatement = null;
        PreparedStatement preparedDeleteStatement = null;
        PreparedStatement preparedInsertStatement = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false); // Begin transaction
            int paramIndex;
            
            String SELECT_FCTE011_BY_ID;
            if (accPayment.getCardid() == null || accPayment.getCardid().isEmpty()) {
                SELECT_FCTE011_BY_ID = "SELECT FIRST 1 * FROM taccpayment WHERE custcode = ? and account = ? and transtype = ? and rptype = ?";
            } else {
                SELECT_FCTE011_BY_ID = "SELECT FIRST 1 * FROM taccpayment WHERE cardid = ? and custcode = ? and account = ? and transtype = ? and rptype = ?";
            }
            // Check for existence
            preparedExistStatement = connection.prepareStatement(SELECT_FCTE011_BY_ID);
            paramIndex = 1; // Reset paramIndex for this statement
            if (accPayment.getCardid() != null && !accPayment.getCardid().isEmpty()) {
                preparedExistStatement.setString(paramIndex++, accPayment.getCardid());
            }
            preparedExistStatement.setString(paramIndex++, accPayment.getCustcode());
            preparedExistStatement.setString(paramIndex++, accPayment.getAccount());
            preparedExistStatement.setString(paramIndex++, accPayment.getTranstype());
            preparedExistStatement.setString(paramIndex++, accPayment.getRptype());
            
            rs = preparedExistStatement.executeQuery();

            while (rs.next()) {
                // If found duplicate, delete it
                paramIndex = 1; // Reset paramIndex for this statement
                
                String DELETE_FCTE011_SQL;
                if (accPayment.getCardid() == null || accPayment.getCardid().isEmpty()) {
                    DELETE_FCTE011_SQL = "DELETE FROM taccpayment WHERE custcode = ? and account = ? and transtype = ? and rptype = ?";
                } else {
                    DELETE_FCTE011_SQL = "DELETE FROM taccpayment WHERE cardid = ? and custcode = ? and account = ? and transtype = ? and rptype = ?";
                }
                preparedDeleteStatement = connection.prepareStatement(DELETE_FCTE011_SQL);
                if (accPayment.getCardid() != null && !accPayment.getCardid().isEmpty()) {
                    preparedDeleteStatement.setString(paramIndex++, accPayment.getCardid());
                }
                preparedDeleteStatement.setString(paramIndex++, accPayment.getCustcode());
                preparedDeleteStatement.setString(paramIndex++, accPayment.getAccount());
                preparedDeleteStatement.setString(paramIndex++, accPayment.getTranstype());
                preparedDeleteStatement.setString(paramIndex++, accPayment.getRptype());
                
                preparedDeleteStatement.executeUpdate();
//                System.out.println("Duplicate found and deleted: " + rowDeleted);
            }

            // Insert new record
            preparedInsertStatement = connection.prepareStatement(INSERT_FCTE011_SQL);
            paramIndex = 1; // Reset paramIndex for this statement
            preparedInsertStatement.setString(paramIndex++, accPayment.getCardid());
            preparedInsertStatement.setString(paramIndex++, accPayment.getCustacct());
            preparedInsertStatement.setString(paramIndex++, accPayment.getCustcode());
            preparedInsertStatement.setString(paramIndex++, accPayment.getAccount());
            preparedInsertStatement.setString(paramIndex++, accPayment.getTranstype());
            preparedInsertStatement.setString(paramIndex++, accPayment.getRptype());
            preparedInsertStatement.setString(paramIndex++, accPayment.getBankcheqcode());
            preparedInsertStatement.setString(paramIndex++, accPayment.getBankcode());
            preparedInsertStatement.setString(paramIndex++, accPayment.getBankbranchcode());
            preparedInsertStatement.setString(paramIndex++, accPayment.getBankaccno());
            preparedInsertStatement.setString(paramIndex++, accPayment.getBankacctype());
            preparedInsertStatement.setString(paramIndex++, accPayment.getBankcheqcodeextra());
            preparedInsertStatement.setString(paramIndex++, accPayment.getPaytype());
            preparedInsertStatement.setString(paramIndex++, accPayment.getCrosstype());
            preparedInsertStatement.setDate(paramIndex++, accPayment.getEffdate());
            preparedInsertStatement.setDate(paramIndex++, accPayment.getEnddate());
            preparedInsertStatement.executeUpdate();

            connection.commit(); // Commit transaction
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); // Rollback transaction in case of error
                } catch (SQLException ex) {
                    printSQLException(ex);
                }
            }
            printSQLException(e);
        } finally {
            // Close resources
            if (rs != null) try { rs.close(); } catch (SQLException e) { printSQLException(e); }
            if (preparedExistStatement != null) try { preparedExistStatement.close(); } catch (SQLException e) { printSQLException(e); }
            if (preparedDeleteStatement != null) try { preparedDeleteStatement.close(); } catch (SQLException e) { printSQLException(e); }
            if (preparedInsertStatement != null) try { preparedInsertStatement.close(); } catch (SQLException e) { printSQLException(e); }
            if (connection != null) try { connection.close(); } catch (SQLException e) { printSQLException(e); }
        }
    }


    public boolean updateAccPayment(AccPayment accPayment) {
        boolean rowUpdated = false;
        String UPDATE_FCTE011_SQL;

        if (accPayment.getCardid() == null || accPayment.getCardid().isEmpty()) {
        	UPDATE_FCTE011_SQL = "UPDATE taccpayment SET bankcheqcode = ?, bankcode = ?, bankbranchcode = ?, bankaccno = ?, bankacctype = ?, bankcheqcodeextra = ?, paytype = ?, crosstype = ?, "
        			+ "effdate = ?, enddate = ? WHERE custcode = ? and account = ? and transtype = ? and rptype = ?";
        } else {
        	UPDATE_FCTE011_SQL = "UPDATE taccpayment SET bankcheqcode = ?, bankcode = ?, bankbranchcode = ?, bankaccno = ?, bankacctype = ?, bankcheqcodeextra = ?, paytype = ?, crosstype = ?, "
        			+ "effdate = ?, enddate = ? WHERE cardid = ? and custcode = ? and account = ? and transtype = ? and rptype = ?";
        }
        
//    	System.out.println(UPDATE_FCTE011_SQL);
        try (Connection connection = getConnection();
        	PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FCTE011_SQL)) {
	
	            int paramIndex = 1;
	            // Set parameters for the columns to be updated
	            preparedStatement.setString(paramIndex++, accPayment.getBankcheqcode());
	            preparedStatement.setString(paramIndex++, accPayment.getBankcode());
	            preparedStatement.setString(paramIndex++, accPayment.getBankbranchcode());
	            preparedStatement.setString(paramIndex++, accPayment.getBankaccno());
	            preparedStatement.setString(paramIndex++, accPayment.getBankacctype());
	            preparedStatement.setString(paramIndex++, accPayment.getBankcheqcodeextra());
	            preparedStatement.setString(paramIndex++, accPayment.getPaytype());
	            preparedStatement.setString(paramIndex++, accPayment.getCrosstype());
	            preparedStatement.setDate(paramIndex++, accPayment.getEffdate());
	            preparedStatement.setDate(paramIndex++, accPayment.getEnddate());
	
	            // Set parameters for the WHERE clause
	            if (accPayment.getCardid() != null && !accPayment.getCardid().isEmpty()) {
		            preparedStatement.setString(paramIndex++, accPayment.getCardid());
	            }
	            preparedStatement.setString(paramIndex++, accPayment.getCustcode());
	            preparedStatement.setString(paramIndex++, accPayment.getAccount());
	            preparedStatement.setString(paramIndex++, accPayment.getTranstype());
	            preparedStatement.setString(paramIndex++, accPayment.getRptype());
	            
	            rowUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            printSQLException(e);
        }
        return rowUpdated;
    }

    public boolean deleteAccPayment(String inputCardid, String inputCustcode,String inputAccount, String inputTranstype, String inputRptype) {
        boolean rowDeleted = false;
        String DELETE_FCTE011_SQL;

        if (inputCardid == null || inputCardid.isEmpty()) {
            DELETE_FCTE011_SQL = "DELETE FROM taccpayment WHERE custcode = ? and account = ? and transtype = ? and rptype = ?";
        } else {
            DELETE_FCTE011_SQL = "DELETE FROM taccpayment WHERE cardid = ? and custcode = ? and account = ? and transtype = ? and rptype = ?";
        }
        
//    	System.out.println(DELETE_FCTE011_SQL);
//    	System.out.println(inputCardid);
//    	System.out.println(inputCustcode);
//    	System.out.println(inputAccount);
//    	System.out.println(inputTranstype);
//    	System.out.println(inputRptype);
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FCTE011_SQL)) {
	            int paramIndex = 1;
	            if (inputCardid != null && !inputCardid.isEmpty()) {
	                preparedStatement.setString(paramIndex++, inputCardid);
	            }
	            preparedStatement.setString(paramIndex++, inputCustcode);
	            preparedStatement.setString(paramIndex++, inputAccount);
	            preparedStatement.setString(paramIndex++, inputTranstype);
	            preparedStatement.setString(paramIndex, inputRptype);
	            rowDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            printSQLException(e);
        }
        return rowDeleted;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
    
    public String addSqlStringCondition(String parameter, String column, boolean hasPreviousCondition) {
    	String conditionStr = "";
        if (parameter != null && !parameter.isEmpty()) {
            if (hasPreviousCondition) {
            	conditionStr += " AND";
            }
            
            if(column.equals("cc.efullname") || column.equals("cc.lfullname")) {
            	conditionStr += " " + column + " like '" + parameter + "%'";
            }
            else {
            	conditionStr += " " + column + " = '" + parameter + "'";
            }
        }
        return conditionStr;
    }
}
