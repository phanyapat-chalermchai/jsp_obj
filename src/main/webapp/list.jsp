<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.jsp.fcte.modal.AccPayment" %>


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

            <form id="searchForm" method="get" action="<%=request.getContextPath()%>/searchList" class="form">
			    <div class="form-row" style="text-align: center; align-items:center; justify-content-:center;">
			        <!-- First Column -->
			        <div class="col-md-6">
			            <div class="form-group row">
			                <label for="appId" class="col-sm-4 col-form-label text-right">Application ID :</label>
			                <div class="col-sm-6">
			                    <input type="text" class="form-control" id="appId" name="appId" placeholder="Application ID" value="${param.appId}">
			                </div>
			            </div>
			            <div class="form-group row">
			                <label for="custcode" class="col-sm-4 col-form-label text-right">Customer ID :</label>
			                <div class="col-sm-6">
			                    <input type="text" class="form-control" id="custcode" name="custcode" placeholder="Customer ID" step="1" oninput="this.value = this.value.replace(/\D/g, '')" value="${param.custcode}">
			                </div>
			            </div>
			            <div class="form-group row">
			                <label for="custName" class="col-sm-4 col-form-label text-right">Customer Name :</label>
			                <div class="col-sm-6">
			                    <input type="text" class="form-control" id="custNameEN" name="custNameEN" placeholder="Customer Name" value="${param.custNameEN}">
			                </div>
			            </div>
			            <div class="form-group row">
			                <label for="marketingId" class="col-sm-4 col-form-label text-right">Marketing ID :</label>
			                <div class="col-sm-6">
			                    <input type="text" class="form-control" id="marketingId" name="marketingId" placeholder="Marketing ID" step="1" oninput="this.value = this.value.replace(/\D/g, '')" value="${param.marketingId}">
			                </div>
			            </div>
			        </div>
			        <!-- Second Column -->
			        <div class="col-md-5">
			            <div class="form-group row">
			                <label for="channel" class="col-sm-4 col-form-label text-right">Channel :</label>
			                <div class="col-sm-8">
			                    <select class="form-control" name="channel">
			                        <option value="" ${param.channel == '' ? 'selected' : ''}></option>
			                        <option value="ONLINE" ${param.channel == 'ONLINE' ? 'selected' : ''}>ONLINE</option>
			                        <option value="OFFLINE" ${param.channel == 'OFFLINE' ? 'selected' : ''}>OFFLINE</option>
			                    </select>
			                </div>
			            </div>
			            <div class="form-group row">
			                <label for="cardid" class="col-sm-4 col-form-label text-right">Card ID :</label>
			                <div class="col-sm-8">
			                    <input type="text" class="form-control" id="cardid" name="cardid" placeholder="Card ID" step="1" oninput="this.value = this.value.replace(/\D/g, '')" value="${param.cardid}">
			                </div>
			            </div>
			            <div class="form-group row">
			                <label for="fullName" class="col-sm-4 col-form-label text-right">ชื่อ - นามสกุล :</label>
			                <div class="col-sm-8">
			                    <input type="text" class="form-control" id="custNameTH" name="custNameTH" placeholder="ชื่อ - นามสกุล" value="${param.custNameTH}">
			                </div>
			            </div>
			            <div class="form-group row">
			                <label for="branch" class="col-sm-4 col-form-label text-right">Branch :</label>
			                <div class="col-sm-8">
			                    <input type="text" class="form-control" id="branch" name="branch" placeholder="Branch" step="1" oninput="this.value = this.value.replace(/\D/g, '')" value="${param.branch}">
			                </div>
			            </div>
			        </div>
			    </div>
			    <div class="form-row">
			        <div class="col-md-12 text-right">
			            <button type="submit" class="btn btn-primary mr-3">Search</button>
			            <button type="reset" class="btn btn-secondary" onclick="window.location.href='<%=request.getContextPath()%>/list'">Reset</button>
			        </div>
			    </div>
			</form>
            <hr>

            <div class="container d-flex justify-content-between align-items-center">
                <div>
                    <a href="<%=request.getContextPath()%>/new" class="btn btn-success">Add New</a>
                </div>
                <div class="d-flex align-items-center">
                    <label class="mr-2 mb-0">Items per page</label>
                    <div>
                        <select class="form-control" name="itemsPerPage" onchange="changeItemsPerPage(this.value)">
                            <option value="5" ${itemsPerPage == '5' ? 'selected' : ''}>5</option>
                            <option value="10" ${itemsPerPage == '10' ? 'selected' : ''}>10</option>
                            <option value="25" ${itemsPerPage == '25' ? 'selected' : ''}>25</option>
                            <option value="50" ${itemsPerPage == '50' ? 'selected' : ''}>50</option>
                            <option value="100" ${itemsPerPage == '100' ? 'selected' : ''}>100</option>
                        </select>
                    </div>
                </div>
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
                    <c:choose>
                        <c:when test="${empty listAccPayment}">
                            <tr>
                                <td colspan="11" class="text-center no-hover" style="padding: 50px 0;">No Data</td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="AccPayment" items="${listAccPayment}">
                                <tr class="edit-row" data-cardid="${AccPayment.cardid}" data-custcode="${AccPayment.custcode}" data-account="${AccPayment.account}" data-transtype="${AccPayment.transtype}" data-rptype="${AccPayment.rptype}"
                                    onclick="window.location.href = 'edit?cardid=' + this.getAttribute('data-cardid') + '&custcode=' + this.getAttribute('data-custcode') + '&account=' + this.getAttribute('data-account') + '&transtype=' + this.getAttribute('data-transtype') + '&rptype=' + this.getAttribute('data-rptype')">
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
                                        <a href="edit?cardid=${AccPayment.cardid}&custcode=${AccPayment.custcode}&account=${AccPayment.account}&transtype=${AccPayment.transtype}&rptype=${AccPayment.rptype}" class="btn btn-sm btn-primary mr-1">Edit</a>
                                        <a href="delete?cardid=${AccPayment.cardid}&custcode=${AccPayment.custcode}&account=${AccPayment.account}&transtype=${AccPayment.transtype}&rptype=${AccPayment.rptype}" class="btn btn-sm btn-danger">Delete</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>

            <c:if test="${not empty listAccPayment}">
                <!-- Displaying number of entries and Pagination Controls -->
			<div class="container">
			    <div class="row">
			        <!-- Showing entries on the left -->
			        <div class="col-md-6">
			            <p>
			                <c:set var="endItem" value="${currentPage * itemsPerPage}"/>
							<c:choose>
							    <c:when test="${endItem > totalItems}">
							        <c:set var="endItem" value="${totalItems}"/>
							    </c:when>
							</c:choose>
							<c:if test="${listAccPayment != null && !listAccPayment.isEmpty()}">
								Showing ${currentPage * itemsPerPage - itemsPerPage + 1}-${endItem} of ${totalItems} Items
							</c:if>
			            </p>
			        </div>
				        <!-- Pagination Controls on the right -->
				        <div class="col-md-6 d-flex justify-content-end">
				            <div class="ml-2">
				                <!-- Pagination -->
				                <nav aria-label="Page navigation">
				                    <ul class="pagination justify-content-center">
				                        <!-- Pagination Controls -->
				                        <!-- Previous Page Button -->
				                        <li class="page-item ${currentPage <= 1 ? 'disabled' : ''}">
				                            <a class="page-link" href="?currentPage=${currentPage - 1}&itemsPerPage=${itemsPerPage}" aria-label="Previous">
				                                <span aria-hidden="true">&laquo;</span>
				                                <span class="sr-only">Previous</span>
				                            </a>
				                        </li>
				
				                        <!-- Page Numbers -->
				                        <c:choose>
				                            <c:when test="${currentPage > 5}">
				                                <li class="page-item">
				                                    <a class="page-link" href="?currentPage=1&itemsPerPage=${itemsPerPage}">1</a>
				                                </li>
				                                <li class="page-item disabled">
				                                    <span class="page-link">...</span>
				                                </li>
				                            </c:when>
				                        </c:choose>
				
				                        <c:forEach var="i" begin="${currentPage - 4 > 0 ? currentPage - 4 : 1}" end="${currentPage + 5 < totalPages ? currentPage + 5 : totalPages}">
				                            <li class="page-item ${i == currentPage ? 'active' : ''}">
				                                <a class="page-link" href="?currentPage=${i}&itemsPerPage=${itemsPerPage}">${i}</a>
				                            </li>
				                        </c:forEach>
				
				                        <c:choose>
				                            <c:when test="${currentPage + 5 < totalPages}">
				                                <li class="page-item disabled">
				                                    <span class="page-link">...</span>
				                                </li>
				                                <li class="page-item">
				                                    <a class="page-link" href="?currentPage=${totalPages}&itemsPerPage=${itemsPerPage}">${totalPages}</a>
				                                </li>
				                            </c:when>
				                        </c:choose>
				
				                        <!-- Next Page Button -->
				                        <li class="page-item ${currentPage >= totalPages ? 'disabled' : ''}">
				                            <a class="page-link" href="?currentPage=${currentPage + 1}&itemsPerPage=${itemsPerPage}" aria-label="Next">
				                                <span aria-hidden="true">&raquo;</span>
				                                <span class="sr-only">Next</span>
				                            </a>
				                        </li>
				                    </ul>
				                </nav>
				            </div>
				        </div>
				    </div>
				</div>
            </c:if>
        </div>
    </div>

    <script>
        function changeItemsPerPage(value) {
            let form = document.getElementById('searchForm');
            let input = document.createElement('input');
            input.type = 'hidden';
            input.name = 'itemsPerPage';
            input.value = value;
            form.appendChild(input);
            form.submit();
        }
    </script>
</body>
</html>
