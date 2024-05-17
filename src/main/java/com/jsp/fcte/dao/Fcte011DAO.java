package com.jsp.fcte.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jsp.fcte.modal.*;

public class Fcte011DAO {
	private String jdbcURL = "jdbc:informix-sqli://zircon:61000/refdbdbsv3:INFORMIXSERVER=sbaserver;DB_LOCALE=th_th.thai620;";
    private String jdbcUsername = "inforef";
    private String jdbcPassword = "inforef";

    // JDBC driver class name
    private static final String JDBC_DRIVER = "com.informix.jdbc.IfxDriver";

    private static final String INSERT_FCTE011_SQL = "INSERT INTO taccpayment (cardid, custacct, custcode, account, transtype, rptype, bankcheqcode, bankcode, bankbranchcode, "
    		+ "bankaccno, bankacctype, bankcheqcodeextra, paytype, crosstype, effdate, enddate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    private static final String SELECT_FCTE011_BY_ID = "SELECT FIRST 1 * FROM taccpayment WHERE custcode = ? and account = ? and transtype = ? and rptype = ?";
    
    private static final String SELECT_ALL_FCTE011 = "SELECT FIRST 10 * FROM taccpayment";
    
    private static final String UPDATE_FCTE011_SQL = "UPDATE taccpayment SET transtype = ?, effectdate = ?, amount = ?, remark = ? "
    		+ "WHERE custcode = ? and account = ? and transtype = ? and rptype = ?";
	
    private static final String DELETE_FCTE011_SQL = "DELETE FIRST 1 FROM taccpayment WHERE custcode = ? and account = ? and transtype = ? and rptype = ?";

    
    public Fcte011DAO() {
    }


    protected Connection getConnection() {
        Connection connection = null;
        try {
            // Load and register the JDBC driver
            Class.forName(JDBC_DRIVER);
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
    public List<AccPayment> searchListAccPayment(String appId, String custCode, String custName, String marketingId, 
    		String channel, String cardId, String fullName, String branch) {
    	
    	String WHERE_CLAUSE = " WHERE";
        boolean hasPreviousCondition = false;
            
        if (appId != null && !appId.isEmpty()) {
        	WHERE_CLAUSE += " appId = '" + appId + "'";
        	hasPreviousCondition = true;
        }
            
        if (custCode != null && !custCode.isEmpty()) {
        	if (hasPreviousCondition) {
        		WHERE_CLAUSE += " AND";
        	}
        	WHERE_CLAUSE += " custcode = '" + custCode + "'";
        	hasPreviousCondition = true;
        }
      
        String CUSTOM_SELECT_ALL_FCTE011 = SELECT_ALL_FCTE011;
        if(hasPreviousCondition)
        	CUSTOM_SELECT_ALL_FCTE011 += WHERE_CLAUSE;

        System.out.println("518__________________________________________");
        System.out.println(CUSTOM_SELECT_ALL_FCTE011);
        
        List<AccPayment> accPayments = new ArrayList<>();
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
        } catch (SQLException e) {
        	printSQLException(e);
        }
        return accPayments;
    }

    public AccPayment selectAccPayment(String inputCustcode,String inputAccount, String inputTranstype, String inputRptype) {
        AccPayment accPayment = null;
    	System.out.println(SELECT_FCTE011_BY_ID);
    	System.out.println(inputCustcode);
    	System.out.println(inputAccount);
    	System.out.println(inputTranstype);
    	System.out.println(inputRptype);
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FCTE011_BY_ID)) {
	            preparedStatement.setString(1, inputCustcode);
	            preparedStatement.setString(2, inputAccount);
	            preparedStatement.setString(3, inputTranstype);
	            preparedStatement.setString(4, inputRptype);
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

    public void insertAccPayment(AccPayment accPayment) throws SQLException {
    	System.out.println(INSERT_FCTE011_SQL);
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_FCTE011_SQL)) {
	            preparedStatement.setString(1, accPayment.getCardid());
	            preparedStatement.setString(2, accPayment.getCustacct());
	            preparedStatement.setString(3, accPayment.getCustcode());
	            preparedStatement.setString(4, accPayment.getAccount());
	            preparedStatement.setString(5, accPayment.getTranstype());
	            preparedStatement.setString(6, accPayment.getRptype());
	            preparedStatement.setString(7, accPayment.getBankcheqcode());
	            preparedStatement.setString(8, accPayment.getBankcode());
	            preparedStatement.setString(9, accPayment.getBankbranchcode());
	            preparedStatement.setString(10, accPayment.getBankaccno());
	            preparedStatement.setString(11, accPayment.getBankacctype());
	            preparedStatement.setString(12, accPayment.getBankcheqcodeextra());
	            preparedStatement.setString(13, accPayment.getPaytype());
	            preparedStatement.setString(14, accPayment.getCrosstype());
	            preparedStatement.setDate(15, accPayment.getEffdate());
	            preparedStatement.setDate(16, accPayment.getEnddate());
	        	System.out.println(preparedStatement.toString());
	            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public boolean updateAccPayment(AccPayment accPayment) {
        boolean rowUpdated = false;
    	System.out.println(UPDATE_FCTE011_SQL);
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FCTE011_SQL)) {
	            preparedStatement.setString(7, accPayment.getBankcheqcode());
	            preparedStatement.setString(8, accPayment.getBankcode());
	            preparedStatement.setString(9, accPayment.getBankbranchcode());
	            preparedStatement.setString(10, accPayment.getBankaccno());
	            preparedStatement.setString(11, accPayment.getBankacctype());
	            preparedStatement.setString(12, accPayment.getBankcheqcodeextra());
	            preparedStatement.setString(13, accPayment.getPaytype());
	            preparedStatement.setString(14, accPayment.getCrosstype());
	            preparedStatement.setDate(15, accPayment.getEffdate());
	            preparedStatement.setDate(16, accPayment.getEnddate());
	            rowUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            printSQLException(e);
        }
        return rowUpdated;
    }

    public boolean deleteAccPayment(String inputCustcode,String inputAccount, String inputTranstype, String inputRptype) {
        boolean rowDeleted = false;
    	System.out.println(DELETE_FCTE011_SQL);
    	System.out.println(inputCustcode);
    	System.out.println(inputAccount);
    	System.out.println(inputTranstype);
    	System.out.println(inputRptype);
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FCTE011_SQL)) {
	        	preparedStatement.setString(1, inputCustcode);
	            preparedStatement.setString(2, inputAccount);
	            preparedStatement.setString(3, inputTranstype);
	            preparedStatement.setString(4, inputRptype);
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
}
