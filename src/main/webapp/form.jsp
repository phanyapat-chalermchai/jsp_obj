<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.jsp.fcte.modal.AccPayment" %>
<link rel="stylesheet" href="path/to/datepicker.css">
<script src="path/to/jquery.js"></script>
<script src="path/to/datepicker.js"></script>
				
<script type="text/javascript">
	$('.form-group.date').datepicker({
	todayBtn: "linked",
	autoclose: true,
	todayHighlight: true,
	format: 'dd/mm/yyyy'
	});
</script>
				
<% 
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	AccPayment accPayment = (AccPayment) request.getAttribute("accPayment");
	String formattedEndDate = null;
	if (accPayment != null && accPayment.getEnddate() != null) {
		formattedEndDate = sdf.format(accPayment.getEnddate());
	}

    String formattedEffectDate = null;
    if (accPayment != null && accPayment.getEffdate() != null){
        formattedEffectDate = sdf.format(accPayment.getEffdate());
    }
	
%>

<html>
<head>
<title> Management Application</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>

    <jsp:include page="navbar.jsp" />
    
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
                    <label>Card ID</label> 
                    <input type="text" class="form-control" name="cardid" value="<c:out value='${accPayment.cardid}' />" <c:if test="${accPayment != null}">disabled</c:if>>
                </fieldset>
				
                <fieldset class="form-group">
                    <label>Customer Account</label> 
                    <input type="text" class="form-control" name="custacct" value="<c:out value='${accPayment.custacct}' />" <c:if test="${accPayment != null}">disabled</c:if>>
                </fieldset>
                
                <fieldset class="form-group">
                    <label>Customer Code</label> 
                    <input type="text" class="form-control" name="custcode" value="<c:out value='${accPayment.custcode}' />" <c:if test="${accPayment != null}">disabled</c:if>>
                </fieldset>

                <fieldset class="form-group">
                    <label>Account</label> 
                    <input type="text" class="form-control" name="account" value="<c:out value='${accPayment.account}' />" <c:if test="${accPayment != null}">disabled</c:if>>
                </fieldset>

                <fieldset class="form-group">
                    <label>Transaction Type</label> 
                    <input type="text" class="form-control" name="transtype" value="<c:out value='${accPayment.transtype}' />" <c:if test="${accPayment != null}">disabled</c:if>>
                </fieldset>
                
				<fieldset class="form-group">
				    <label>RP Type</label> 
				    <select class="form-control" name="rptype" <c:if test="${accPayment != null}">disabled</c:if>>
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
				    <select class="form-control" name="bankacctype">
				        <option value="0" <c:if test="${accPayment.bankacctype == '0'}">selected</c:if>>บัญชีกระแสรายวัน</option>
				        <option value="1" <c:if test="${accPayment.bankacctype == '1'}">selected</c:if>>บัญชีออมทรัพย์</option>
				    </select>
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
				    <fieldset class="form-group">
				        <label>Effect Date</label> 
				        <input type="date" value="<%=formattedEffectDate%>" class="form-control" name="effectdate">
				    </fieldset>
				</c:if>
				<c:if test="${accPayment == null || accPayment.getEffdate() == null}">
				    <fieldset class="form-group">
				        <label>Effect Date</label> 
				        <input type="date" class="form-control" name="effectdate" maxlength="4">
				    </fieldset>
				</c:if>
				
			
			
				<c:if test="${accPayment != null && accPayment.getEnddate() != null}">
				    <fieldset class="form-group date">
				        <label>Effect Date</label> 
				        <input type="date" value="<%=formattedEndDate%>" class="form-control" name="enddate">
				    </fieldset>
				</c:if>
				<c:if test="${accPayment == null || accPayment.getEnddate() == null}">
				    <fieldset class="form-group date">
				        <label>End Date</label> 
				        <input type="date" class="form-control" name="enddate" maxlength="4">
				    </fieldset>
				</c:if>
				
				
				
				<br/>
				<button type="submit" class="btn btn-success">Save</button>
				
				</form>
			</div>
		</div>
	</div>
</body>
</html>
