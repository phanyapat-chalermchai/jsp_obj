<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>FCTE011</title>
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
					FCTE011 </a>
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
			<!-- <h3 class="text-center">FCTE011</h3> -->
            <br>
			
            
            <!-- Two-Column Search Form with Inline Labels and Inputs -->
            <form method="get" action="<%=request.getContextPath()%>/list" class="form">
                <div class="form-row" style="text-align: center">
                    <!-- First Column -->
                    <div class="col-md-6">
                        <div class="form-group row">
                            <label for="appId" class="col-sm-4 col-form-label text-right">Application ID :</label>
                            <div class="col-sm-6">
                                <input type="text" class="form-control" id="appId" name="appId" placeholder="Application ID" value="${param.appId}">
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="custId" class="col-sm-4 col-form-label text-right">Customer ID :</label>
                            <div class="col-sm-6">
                                <input type="text" class="form-control" id="custId" name="custId" placeholder="Customer ID" value="${param.custId}">
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="custName" class="col-sm-4 col-form-label text-right">Customer Name :</label>
                            <div class="col-sm-6">
                                <input type="text" class="form-control" id="custName" name="custName" placeholder="Customer Name" value="${param.custName}">
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="marketingId" class="col-sm-4 col-form-label text-right">Marketing ID :</label>
                            <div class="col-sm-6">
                                <input type="text" class="form-control" id="marketingId" name="marketingId" placeholder="Marketing ID" value="${param.marketingId}">
                            </div>
                        </div>
                    </div>
                    <!-- Second Column -->
                    <div class="col-md-5">
                        <div class="form-group row">
                            <label for="channel" class="col-sm-4 col-form-label text-right">Channel :</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" id="channel" name="channel" placeholder="Channel" value="${param.channel}">
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="cardId" class="col-sm-4 col-form-label text-right">Card ID :</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" id="cardId" name="cardId" placeholder="Card ID" value="${param.cardId}">
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="fullName" class="col-sm-4 col-form-label text-right">ชื่อ - นามสกุล :</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" id="fullName" name="fullName" placeholder="ชื่อ - นามสกุล" value="${param.fullName}">
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="branch" class="col-sm-4 col-form-label text-right">Branch :</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" id="branch" name="branch" placeholder="Branch" value="${param.branch}">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-md-12 text-right">
                        <button type="submit" class="btn btn-primary">Search</button>
                    </div>
                </div>
            </form>
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
							<td><c:out value="${AccPayment.cardid}" /></td>
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
