<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.jsp.fcte.modal.AccPayment" %>

<html>
<head>
<title> Management Application</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
<body>

	<header>
		<nav class="navbar navbar-expand-md navbar-dark"
			style="background-color: grey">
			<div>
				<a class="navbar-brand"> Credit Management App </a>
			</div>

			<ul class="navbar-nav">
				<li><a href="<%=request.getContextPath()%>/list"
					class="nav-link">List</a></li>
			</ul>
		</nav>
	</header>
	<br>
	<div class="container col-md-5">
		<div class="card">
			<div class="card-body">	
				<c:if test="${accPayment != null}">
					<form action="update" method="post" onsubmit="return validateForm()">
				</c:if>
				<c:if test="${accPayment == null}">
					<form action="insert" method="post" onsubmit="return validateForm()">
				</c:if>

				<caption>
					<h2>
						<c:if test="${accPayment != null}">
            			Edit FCTE011
            		</c:if>
						<c:if test="${accPayment == null}">
            			Add New FCTE011
            		</c:if>
					</h2>
				</caption>
				<br/>
				
                <fieldset class="form-group">
                    <label>Customer Account</label> 
                    <input type="text" class="form-control" name="custacct" value="<c:out value='${accPayment.custacct}' />">
                </fieldset>
                
                <fieldset class="form-group">
                    <label>Customer Code</label> 
                    <input type="text" class="form-control" name="custcode" value="<c:out value='${accPayment.custcode}' />">
                </fieldset>

                <fieldset class="form-group">
                    <label>Account</label> 
                    <input type="text" class="form-control" name="account" value="<c:out value='${accPayment.account}' />">
                </fieldset>

                <fieldset class="form-group">
                    <label>Transaction Type</label> 
                    <input type="text" class="form-control" name="transtype" value="<c:out value='${accPayment.transtype}' />">
                </fieldset>
                
				<fieldset class="form-group">
				    <label>RP Type</label> 
				    <select class="form-control" name="rptype">
				        <option value="R" <c:if test="${accPayment.rptype == 'R'}">selected</c:if>>Revieve</option>
				        <option value="P" <c:if test="${accPayment.rptype == 'P'}">selected</c:if>>Payment</option>
				    </select>
				</fieldset>

                <fieldset class="form-group">
                    <label>Bank Cheque Code</label> 
                    <input type="text" class="form-control" name="bankcheqcode" value="<c:out value='${accPayment.bankcheqcode}' />">
                </fieldset>
                
                <fieldset class="form-group">
                    <label>Bank Code</label> 
                    <input type="text" class="form-control" name="bankcode" value="<c:out value='${accPayment.bankcode}' />">
                </fieldset>

                <fieldset class="form-group">
                    <label>Bank Branch Code</label> 
                    <input type="text" class="form-control" name="bankbranchcode" value="<c:out value='${accPayment.bankbranchcode}' />">
                </fieldset>

                <fieldset class="form-group">
                    <label>Bank Account Number</label> 
                    <input type="text" class="form-control" name="bankaccno" value="<c:out value='${accPayment.bankaccno}' />">
                </fieldset>

                <fieldset class="form-group">
                    <label>Bank Account Type</label> 
                    <input type="text" class="form-control" name="bankacctype" value="<c:out value='${accPayment.bankacctype}' />">
                </fieldset>

                <fieldset class="form-group">
                    <label>Bank Cheque Code Extra</label> 
                    <input type="text" class="form-control" name="bankcheqcodeextra" value="<c:out value='${accPayment.bankcheqcodeextra}' />">
                </fieldset>

                <fieldset class="form-group">
                    <label>Payment Type</label> 
                    <input type="text" class="form-control" name="paytype" value="<c:out value='${accPayment.paytype}' />">
                </fieldset>

                <fieldset class="form-group">
                    <label>Cross Type</label> 
                    <input type="text" class="form-control" name="crosstype" value="<c:out value='${accPayment.crosstype}' />">
                </fieldset>
				
				
				<c:if test="${accPayment != null && accPayment.getEffdate() != null}">
				    <%
				    	AccPayment accPayment = (AccPayment) request.getAttribute("accPayment");
				        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				        String formattedEffectDate = null;
				        if (accPayment != null && accPayment.getEffdate() != null){
				            formattedEffectDate = sdf.format(accPayment.getEffdate());
				    %>
				    <fieldset class="form-group">
				        <label>Effect Date</label> 
				        <input type="date" value="<%=formattedEffectDate%>" class="form-control" name="effectdate">
				    </fieldset>
				    <%
				        }
				    %>
				</c:if>
				<c:if test="${accPayment == null || accPayment.getEffdate() == null}">
				    <fieldset class="form-group">
				        <label>Effect Date</label> 
				        <input type="date" class="form-control" name="effectdate" maxlength="4">
				    </fieldset>
				</c:if>
				
				
				
				
				
				<c:if test="${accPayment != null && accPayment.getEnddate() != null}">
				    <%
				   	 	AccPayment accPayment = (AccPayment) request.getAttribute("accPayment");
				        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				        String formattedEndDate = null;
				        if (accPayment != null && accPayment.getEnddate() != null){
				            formattedEndDate = sdf.format(accPayment.getEnddate());
				    %>
				    <fieldset class="form-group">
				        <label>Effect Date</label> 
				        <input type="date" value="<%=formattedEndDate%>" class="form-control" name="effectdate">
				    </fieldset>
				    <%
				        }
				    %>
				</c:if>
				<c:if test="${accPayment == null || accPayment.getEnddate() == null}">
				    <fieldset class="form-group">
				        <label>End Date</label> 
				        <input type="date" class="form-control" name="effectdate" maxlength="4">
				    </fieldset>
				</c:if>
				
				

				<button type="submit" class="btn btn-success">Save</button>
				</form>

			</div>
		</div>
	</div>
</body>
</html>
