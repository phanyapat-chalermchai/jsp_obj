<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Management Application</title>
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
					Management App </a>
			</div>

			<ul class="navbar-nav">
				<li><a href="<%=request.getContextPath()%>/list"
					class="nav-link">List</a></li>
			</ul>
		</nav>
	</header>
	<br>

	<div class="row">
		<!-- <div class="alert alert-success" *ngIf='message'>{{message}}</div> -->

		<div class="container">
			<h3 class="text-center">FCTE011</h3>
			<hr>
			<div class="container text-left">

				<a href="<%=request.getContextPath()%>/new" class="btn btn-success">Add
					New</a>
			</div>
			<br>
			<table class="table table-bordered">
				<thead style="text-align: center">
					<tr>
						<th>Action</th>
						<th>Customer Code</th>
						<th>Account</th>
						<th>Type</th>
						<th>Receive/Payment</th>
						<th>Bank Code</th>
						<th>Bank Branch Code</th>
						<th>Bank Account No.</th>
						<th>Effect Date</th>
						<th>Expire Date</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="AccPayment" items="${listAccPayment}">

						<tr>
							<td></td>
							<td><c:out value="${AccPayment.custcode}" /></td>
							<td><c:out value="${AccPayment.account}" /></td>
							<td><c:out value="${AccPayment.transtype}" /></td>
							<td><c:out value="${AccPayment.rptype}" /></td>
							<td><c:out value="${AccPayment.bankcode}" /></td>
							<td><c:out value="${AccPayment.bankbranchcode}" /></td>
							<td><c:out value="${AccPayment.bankaccno}" /></td>
							<td><c:out value="${AccPayment.effdate}" /></td>
							<td><c:out value="${AccPayment.enddate}" /></td>
							<td class="text-center">
							    <a href="edit?custcode=<c:out value='${AccPayment.custcode}' />&account=<c:out value='${AccPayment.account}'/>&transtype=<c:out value='${AccPayment.transtype}' />&rptype=<c:out value='${AccPayment.rptype}' />">Edit</a>
							    &nbsp;&nbsp;&nbsp;&nbsp;
							    <a href="delete?accountno=<c:out value='${AccPayment.custcode}' />'">Delete</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>

			</table>
		</div>
	</div>
</body>
</html>
