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

    private static final String INSERT_FCTE011_SQL = "INSERT INTO accPayment (accountno, transtype, effectdate, amount, remark) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_FCTE011_BY_ID = "SELECT * FROM taccpayment WHERE custcode = ? and account = ? and transtype = ? and rptype = ?";
//    private static final String SELECT_ALL_FCTE011 = "SELECT * FROM accPayment";
    private static final String SELECT_ALL_FCTE011 = "SELECT FIRST 5 * FROM taccpayment";
	
    private static final String DELETE_FCTE011_SQL = "DELETE FROM taccpayment WHERE custcode = ? and account = ? and transtype = ? and rptype = ?";
    private static final String UPDATE_FCTE011_SQL = "UPDATE accPayment SET transtype = ?, effectdate = ?, amount = ?, remark = ? WHERE accountno = ?";

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
    public List<AccPayment> selectAllAccPayments() {
    	System.out.println(SELECT_ALL_FCTE011);
        
        List<AccPayment> accPayments = new ArrayList<>();
        try (Connection connection = getConnection();
        	PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_FCTE011)) {
	        	System.out.println(SELECT_ALL_FCTE011);
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


    
    // Handle SQL exceptions
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

    public void insertAccPayment(AccPayment accPayment) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_FCTE011_SQL)) {

//            preparedStatement.setString(1, newAccountNo);
//            preparedStatement.setString(2, accPayment.getTranstype());
//            preparedStatement.setDate(3, accPayment.getEffectdate());
//            preparedStatement.setBigDecimal(4, accPayment.getAmount());
//            preparedStatement.setString(5, accPayment.getRemark());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public AccPayment selectAccPayment(String inputCustcode,String inputAccount, String inputTranstype, String inputRptype) {
        AccPayment accPayment = null;
    	System.out.println("2");
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

    public boolean deleteAccPayment(String accountno) {
        boolean rowDeleted = false;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_FCTE011_SQL)) {
            statement.setString(1, accountno);
            rowDeleted = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            printSQLException(e);
        }
        return rowDeleted;
    }

    public boolean updateAccPayment(AccPayment accPayment) {
        boolean rowUpdated = false;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_FCTE011_SQL)) {
//            statement.setString(1, accPayment.getTranstype());
//            statement.setDate(2, accPayment.getEffectdate());
//            statement.setBigDecimal(3, accPayment.getAmount());
//            statement.setString(4, accPayment.getRemark());
//            statement.setString(5, accPayment.getAccountno());
            rowUpdated = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            printSQLException(e);
        }
        return rowUpdated;
    }

    private String parseSql(String sql, Map<String, Object> params) {
        for (String key : params.keySet()) {
            sql = sql.replace(":" + key, "?");
        }
        return sql;
    }

//    private void printSQLException(SQLException ex) {
//        for (Throwable e : ex) {
//            if (e instanceof SQLException) {
//                e.printStackTrace(System.err);
//                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
//                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
//                System.err.println("Message: " + e.getMessage());
//                Throwable t = ex.getCause();
//                while (t != null) {
//                    System.out.println("Cause: " + t);
//                    t = t.getCause();
//                }
//            }
//        }
//    }
}