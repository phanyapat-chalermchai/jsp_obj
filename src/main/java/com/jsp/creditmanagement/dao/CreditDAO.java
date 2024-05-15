package com.jsp.creditmanagement.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.jsp.creditmanagement.modal.*;

public class CreditDAO {
	private String jdbcURL = "jdbc:informix-sqli://zircon:61000/refdbdbsv3:INFORMIXSERVER=sbaserver;DB_LOCALE=th_th.thai620;";
    private String jdbcUsername = "inforef";
    private String jdbcPassword = "inforef";

    // JDBC driver class name
    private static final String JDBC_DRIVER = "com.informix.jdbc.IfxDriver";

    private static final String INSERT_CREDIT_SQL = "INSERT INTO credit (accountno, transtype, effectdate, amount, remark) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_CREDIT_BY_ID = "SELECT * FROM credit WHERE accountno = ?";
//    private static final String SELECT_ALL_CREDIT = "SELECT * FROM credit";
    private static final String SELECT_ALL_CREDIT = "SELECT * FROM infocust.taccpayment";
	
    private static final String DELETE_CREDIT_SQL = "DELETE FROM credit WHERE accountno = ?";
    private static final String UPDATE_CREDIT_SQL = "UPDATE credit SET transtype = ?, effectdate = ?, amount = ?, remark = ? WHERE accountno = ?";

    public CreditDAO() {
    }


    protected Connection getConnection() {
        Connection connection = null;
        try {
            // Load and register the JDBC driver
            Class.forName(JDBC_DRIVER).newInstance();
            DriverManager.registerDriver(new com.informix.jdbc.IfxDriver());
            // Open a connection
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return connection;
    }

    // Select all credits
    public List<Credit> selectAllCredits() {
        System.out.println("goin1");
        List<Credit> credits = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CREDIT);
             ResultSet rs = preparedStatement.executeQuery()) {
            System.out.println("goin2");
            while (rs.next()) {
                String custcode = rs.getString("custcode");
                if (custcode != null) {
                    System.out.println(custcode);
                    // Assuming you have a Credit constructor that takes relevant parameters
                    // Add your own field mappings here
                }
            }
        } catch (SQLException e) {
            System.out.println("goin3");
            printSQLException(e);
        }
        return credits;
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

    public void insertCredit(Credit credit) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CREDIT_SQL)) {
        	
        	String SELECT_CREDIT_BY_TRANSTYPE = "select MAX(accountno) as accountno from credit where transtype = '" + credit.getTranstype() + "'";
            PreparedStatement preparedStatementFindMaxAccountNo = connection.prepareStatement(SELECT_CREDIT_BY_TRANSTYPE);
            System.out.println(preparedStatementFindMaxAccountNo);
            ResultSet resultMaxAccountNo = preparedStatementFindMaxAccountNo.executeQuery();
        	
        	String newAccountNo = credit.getTranstype() + "0000000000001";
        	if (resultMaxAccountNo.next()) {
        	    String accountNo = resultMaxAccountNo.getString("accountno");
        	    if (!"null".equals(accountNo)) {
        	        int no = Integer.parseInt(accountNo.substring(2));
        	        newAccountNo = String.valueOf(++no);
        	        while (newAccountNo.length() < 13) {
        	            newAccountNo = "0" + newAccountNo;
        	        }
        	        newAccountNo = credit.getTranstype() + newAccountNo;
        	    }
        	}

            preparedStatement.setString(1, newAccountNo);
            preparedStatement.setString(2, credit.getTranstype());
            preparedStatement.setDate(3, credit.getEffectdate());
            preparedStatement.setBigDecimal(4, credit.getAmount());
            preparedStatement.setString(5, credit.getRemark());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public Credit selectCredit(String accountno) {
        Credit credit = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CREDIT_BY_ID)) {
            preparedStatement.setString(1, accountno);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String transtype = rs.getString("transtype");
                Date effectdate = rs.getDate("effectdate");
                BigDecimal amount = rs.getBigDecimal("amount");
                String remark = rs.getString("remark");
                credit = new Credit(accountno, transtype, effectdate, amount, remark);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return credit;
    }


//    public List<Credit> selectAllCredits() {
//        List<Credit> credits = new ArrayList<>();
//        try (Connection connection = getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CREDIT)) {
//            ResultSet rs = preparedStatement.executeQuery();
//            while (rs.next()) {
//                String accountno = rs.getString("accountno");
//                String transtype = rs.getString("transtype");
//                Date effectdate = rs.getDate("effectdate");
//                BigDecimal amount = rs.getBigDecimal("amount");
//                String remark = rs.getString("remark");
//                credits.add(new Credit(accountno, transtype, effectdate, amount, remark));
//            }
//            
//            credits.forEach(e -> {
//                System.out.print(e.);
//            });
//        } catch (SQLException e) {
//            printSQLException(e);
//        }
//        return credits;
//    }

    public boolean deleteCredit(String accountno) {
        boolean rowDeleted = false;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_CREDIT_SQL)) {
            statement.setString(1, accountno);
            rowDeleted = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            printSQLException(e);
        }
        return rowDeleted;
    }

    public boolean updateCredit(Credit credit) {
        boolean rowUpdated = false;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_CREDIT_SQL)) {
            statement.setString(1, credit.getTranstype());
            statement.setDate(2, credit.getEffectdate());
            statement.setBigDecimal(3, credit.getAmount());
            statement.setString(4, credit.getRemark());
            statement.setString(5, credit.getAccountno());
            rowUpdated = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            printSQLException(e);
        }
        return rowUpdated;
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
