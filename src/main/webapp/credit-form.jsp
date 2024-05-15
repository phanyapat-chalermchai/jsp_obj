<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.jsp.creditmanagement.modal.Credit" %>

<html>
<head>
<title>Credit Management Application</title>
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
					class="nav-link">Credits</a></li>
			</ul>
		</nav>
	</header>
	<br>
	<div class="container col-md-5">
		<div class="card">
			<div class="card-body">
				<c:if test="${credit != null}">
					<form action="update" method="post" onsubmit="return validateForm()">
				</c:if>
				<c:if test="${credit == null}">
					<form action="insert" method="post" onsubmit="return validateForm()">
				</c:if>

				<caption>
					<h2>
						<c:if test="${credit != null}">
            			Edit Credit
            		</c:if>
						<c:if test="${credit == null}">
            			Add New Credit
            		</c:if>
					</h2>
				</caption>

				<c:if test="${credit != null}">
				    <input type="hidden" name="accountno" value="<c:out value='${credit.accountno}' />" />
				</c:if>
				
				<fieldset class="form-group">
				    <label>Transaction Type</label> 
				    <select class="form-control" name="transtype">
				        <option value="db" <c:if test="${credit.transtype == 'db'}">selected</c:if>>Debit</option>
				        <option value="cd" <c:if test="${credit.transtype == 'cd'}">selected</c:if>>Credit</option>
				        <option value="mn" <c:if test="${credit.transtype == 'mn'}">selected</c:if>>Money</option>
				    </select>
				</fieldset>
				
				<c:if test="${credit != null && credit.getEffectdate() != null}">
				    <%
				        Credit credit = (Credit) request.getAttribute("credit");
				        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				        String formattedDate = null;
				        if (credit != null && credit.getEffectdate() != null){
				            formattedDate = sdf.format(credit.getEffectdate());
				    %>
				    <fieldset class="form-group">
				        <label>Effect Date</label> 
				        <input type="date" value="<%=formattedDate%>" class="form-control" name="effectdate" maxlength="4">
				    </fieldset>
				    <%
				        }
				    %>
				</c:if>
				<c:if test="${credit == null || credit.getEffectdate() == null}">
				    <fieldset class="form-group">
				        <label>Effect Date</label> 
				        <input type="date" class="form-control" name="effectdate" maxlength="4">
				    </fieldset>
				</c:if>
								
				
				<fieldset class="form-group">
				    <label>Amount</label> <input type="text" 
				    oninput="this.value = this.value.replace(/[^\d.]/g, ''); limitDecimalPoint(this);" onpaste="return false;"
				        value="<c:out value='${credit.amount}' />" class="form-control"
				        name="amount">
				</fieldset>
				
				<fieldset class="form-group">
				    <label>Remark</label> <input type="text"
				        value="<c:out value='${credit.remark}' />" class="form-control"
				        name="remark">
				</fieldset>

				<button type="submit" class="btn btn-success">Save</button>
				</form>
				
				<script>
				    function limitDecimalPoint(input) {
				        // Get the current value of the input field
				        var value = input.value;
				        
				        // Count the number of decimal points in the value
				        var decimalCount = (value.match(/\./g) || []).length;
				        
				        // If there's already one decimal point, prevent typing another one
				        if (decimalCount > 1) {
				            input.value = value.slice(0, -1); // Remove the last character typed
				        }
				    }
				    function limitYearLength(input, maxLength) {
			            console.log(input.value.length);
				        if (input.value.length > maxLength) {
				            input.value = input.value.slice(0, maxLength);
				            console.log(input.value.length);
				        }
				    }
				</script>

			</div>
		</div>
	</div>
</body>
</html>
