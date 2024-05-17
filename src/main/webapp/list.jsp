<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>FCTE011</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<style>
    tbody tr:hover {
        background-color: #bee0ec;
    }
</style>
</head>
<body>

    <jsp:include page="navbar.jsp" />
    
	<br>
	<div class="row">
		<div class="container">
            <br>
			
            <!-- Two-Column Search Form with Inline Labels and Inputs -->
            <form id="searchForm" method="get" action="<%=request.getContextPath()%>/searchList" class="form">
                <div class="form-row" style="text-align: center; align-items:center; justify-content-:center; ">
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
            			<button type="reset" class="btn btn-secondary" onclick="window.location.href='<%=request.getContextPath()%>/list'">Reset</button>
                    </div>
                </div>
            </form>
			<hr>
            
			<div class="container text-left">
				<a href="<%=request.getContextPath()%>/new" class="btn btn-success">Add New</a>
			</div>
			<br>
            
			<table class="table table-bordered">
				<thead style="vertical-align: middle;text-align: center; background-color: #2596be; color: white">
					<tr>
			            <th style="width: 8%">Action</th>
			            <th style="width: 8%">Customer Code</th>
			            <th style="width: 8%">Account</th>
			            <th style="width: 8%">Type</th>
			            <th style="width: 8%">Receive/ Payment</th>
			            <th style="wid9h: 8%">Bank Code</th>
			            <th style="width: 8%">Bank Branch Code</th>
			            <th style="width: 8%">Bank Account No.</th>
			            <th style="width: 10%">Effect Date</th>
			            <th style="width: 10%">Expire Date</th>
						<th style="width: 15%"></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="AccPayment" items="${listAccPayment}">
						<tr data-custcode="${AccPayment.custcode}" data-account="${AccPayment.account}" data-transtype="${AccPayment.transtype}" data-rptype="${AccPayment.rptype}" onclick="goToEditPage(this)">
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
							    <a href="delete?accountno=<c:out value='${AccPayment.custcode}' />">Delete</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

<script>
    function goToEditPage(row) {
        var custcode = row.getAttribute('custcode');
        var account = row.getAttribute('account');
        var transtype = row.getAttribute('transtype');
        var rptype = row.getAttribute('rptype');
        var url = `edit?custcode=${custcode}&account=${account}&transtype=${transtype}&rptype=${rptype}`;
        window.location.href = url;
    }
</script>

</body>
</html>
