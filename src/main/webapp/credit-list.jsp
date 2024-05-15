<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
				<a class="navbar-brand"> 
					Credit Management App </a>
			</div>

			<ul class="navbar-nav">
				<li><a href="<%=request.getContextPath()%>/list"
					class="nav-link">Credits</a></li>
			</ul>
		</nav>
	</header>
	<br>

	<div class="row">
		<!-- <div class="alert alert-success" *ngIf='message'>{{message}}</div> -->

		<div class="container">
			<h3 class="text-center">List of Credits</h3>
			<hr>
			<div class="container text-left">

				<a href="<%=request.getContextPath()%>/new" class="btn btn-success">Add
					New Credit</a>
			</div>
			<br>
			<table class="table table-bordered">
				<thead>
					<tr>
						<th>Account No</th>
						<th>Transaction Type</th>
						<th>Effect Date</th>
						<th>Amount</th>
						<th>Remark</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="Credit" items="${listCredit}">

						<tr>
							<td><c:out value="${Credit.accountno}" /></td>
							<td><c:out value="${Credit.transtype == 'db' ? 'Debit' : Credit.transtype == 'cd' ? 'Credit' : 'Money'}" /></td>
							<td><c:out value="${Credit.effectdate}" /></td>
							<td class="text-right">
							    <c:if test="${Credit.amount ne null}">
							        <c:out value="${String.format('%,.2f', Credit.amount)}" />
							    </c:if>
							</td>
							<td><c:out value="${Credit.remark}" /></td>
							<td class="text-center"><a href="edit?accountno=<c:out value='${Credit.accountno}' />">Edit</a>
								&nbsp;&nbsp;&nbsp;&nbsp; <a
								href="delete?accountno=<c:out value='${Credit.accountno}' />">Delete</a></td>
						</tr>
					</c:forEach>
				</tbody>

			</table>
		</div>
	</div>
</body>
</html>
