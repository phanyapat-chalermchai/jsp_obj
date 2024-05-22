<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.jsp.fcte.modal.AccPayment" %>
<link rel="stylesheet" href="path/to/datepicker.css">
<script src="path/to/jquery.js"></script>
<script src="path/to/datepicker.js"></script>

<script type="text/javascript">
    $(document).ready(function() {
        $('.form-group.date').datepicker({
            todayBtn: "linked",
            autoclose: true,
            todayHighlight: true,
            format: 'dd/mm/yyyy'
        });
    });
    
    function isNumberOrDashKey(evt) {
        var charCode = (evt.which) ? evt.which : evt.keyCode;
        if (charCode >= 48 && charCode <= 57) {
            return true;
        } else if (charCode === 45) {
            if (evt.target.value.indexOf('-') === -1) {
                return true;
            } else {
                evt.preventDefault();
                return false;
            }
        }
        evt.preventDefault();
        return false;
    }

    function validateInput(input) {
        var value = input.value;
        value = value.replace(/[^\d-]/g, '');
        var hyphenCount = (value.match(/-/g) || []).length;
        if (hyphenCount > 1) {
            value = value.replace(/-+/, '-');
        }
        input.value = value;
    }

    function changeItemsPerPage(value) {
        let form = document.getElementById('searchForm');
        let input = document.createElement('input');
        input.type = 'hidden';
        input.name = 'itemsPerPage';
        input.value = value;
        form.appendChild(input);
        form.submit();
    }
    
    function redirectToDefaultInfo() {
    	var cardid = document.querySelector("input[name='cardid']").value;
        if (cardid.trim() === "") {
            // Card ID is empty, do not redirect
            alert("Can't use default Infomation, Please enter a Card ID.");
        } else {
            // Card ID is not empty, construct and redirect
            var defaultInfoURL = "<c:out value='${defaultInfoURL}' />";
            window.location.href = defaultInfoURL + "?cardid=" + cardid;
        }
    }
</script>

<% 
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    AccPayment accPayment = (AccPayment) request.getAttribute("accPayment");
    String formattedEffectDate = (accPayment != null && accPayment.getEffdate() != null) ? sdf.format(accPayment.getEffdate()) : sdf.format(new Date());
    String formattedEndDate = (accPayment != null && accPayment.getEnddate() != null) ? sdf.format(accPayment.getEnddate()) : "9999-12-31";
%>

<style>
    .btn:focus, .btn:active {
        outline: none !important;
        box-shadow: none !important;
    }
</style>

<html>
<head>
    <title>FCTE011</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>
    <jsp:include page="navbar.jsp" />
    <br>
    <div class="container col-md-8">
        <div class="card mb-3">
            <div class="card-body"> 
                <form action="<c:out value='${modeEdit ? "update" : "insert"}' />" method="post">
                    <div class="form-row mb-3">
                        <h2>
                            <c:if test="${modeEdit}">Edit FCTE011</c:if>
                            <c:if test="${!modeEdit}">Add New FCTE011</c:if>
                        </h2>
                    </div>

                    <c:if test="${modeEdit}">
                        <!-- Hidden fields for parameters for send data to back-end when default field disable -->
                        <input type="hidden" name="cardid" value="<c:out value='${accPayment.cardid}' />">
                        <input type="hidden" name="custacct" value="<c:out value='${accPayment.custacct}' />">
                        <input type="hidden" name="custcode" value="<c:out value='${accPayment.custcode}' />">
                        <input type="hidden" name="account" value="<c:out value='${accPayment.account}' />">
                        <input type="hidden" name="transtype" value="<c:out value='${accPayment.transtype}' />">
                        <input type="hidden" name="rptype" value="<c:out value='${accPayment.rptype}' />">
                    </c:if>

                    <h4>Customer Information</h4>

                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label>Card ID <span style="color: red">*</span></label> 
                            <div class="input-group">
                                <input required step="1" oninput="this.value = this.value.replace(/\D/g, '')" maxlength="20" type="text" class="form-control" name="cardid" value="<c:out value='${accPayment.cardid}' />" <c:if test="${modeEdit}">disabled</c:if>>
                                <div class="input-group-append">
                                    <button <c:if test="${modeEdit}">disabled</c:if> class="btn btn-primary" type="button" onclick="redirectToDefaultInfo()"> Default Information</button>
                                </div>
                            </div>
                        </div>
                        <div class="form-group col-md-6">
                            <label>Customer Account <span style="color: red">*</span></label> 
                            <input required step="1" oninput="this.value = this.value.replace(/\D/g, '')" maxlength="1" type="text" class="form-control" name="custacct" value="<c:out value='${accPayment.custacct}' />" <c:if test="${modeEdit}">disabled</c:if>>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label>Customer Code <span style="color: red">*</span></label> 
                            <input required step="1" oninput="this.value = this.value.replace(/\D/g, '')" maxlength="8" type="text" class="form-control" name="custcode" value="<c:out value='${accPayment.custcode}' />" <c:if test="${modeEdit}">disabled</c:if>>
                        </div>
                        <div class="form-group col-md-6">
                            <label>Account <span style="color: red">*</span></label> 
                            <input required oninput="validateInput(this)" onkeypress="return isNumberOrDashKey(event)" maxlength="15" type="text" class="form-control" name="account" value="<c:out value='${accPayment.account}' />" <c:if test="${modeEdit}">disabled</c:if>>
                        </div>
                    </div>

                    <div class="form-row mb-3">
                        <div class="form-group col-md-6">
                            <label>Transaction Type <span style="color: red">*</span></label> 
                            <input required step="1" oninput="this.value = this.value.replace(/\D/g, '')" maxlength="10" type="text" class="form-control" name="transtype" value="<c:out value='${accPayment.transtype}' />" <c:if test="${modeEdit}">disabled</c:if>>
                        </div>
                        <div class="form-group col-md-6">
                            <label>RP Type <span style="color: red">*</span></label> 
                            <select required class="form-control" name="rptype" <c:if test="${modeEdit}">disabled</c:if>> 
                                <option value="R" <c:if test="${accPayment.rptype == 'R'}">selected</c:if>>Receive</option>
                                <option value="P" <c:if test="${accPayment.rptype == 'P'}">selected</c:if>>Payment</option>
                            </select>
                        </div>
                    </div>

                    <hr class="mb-4">

                    <h4>Bank Information</h4>

                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label>Bank Cheque Code</label> 
                            <input step="1" oninput="this.value = this.value.replace(/\D/g, '')" maxlength="2" type="text" class="form-control" name="bankcheqcode" value="<c:out value='${accPayment.bankcheqcode}' />">
                        </div>
                        <div class="form-group col-md-6">
                            <label>Bank Code</label> 
                            <input step="1" oninput="this.value = this.value.replace(/\D/g, '')" maxlength="5" type="text" class="form-control" name="bankcode" value="<c:out value='${accPayment.bankcode}' />">
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label>Bank Branch Code</label> 
                            <input step="1" oninput="this.value = this.value.replace(/\D/g, '')" maxlength="5" type="text" class="form-control" name="bankbranchcode" value="<c:out value='${accPayment.bankbranchcode}' />">
                        </div>
                        <div class="form-group col-md-6">
                            <label>Bank Account Number</label> 
                            <input step="1" oninput="this.value = this.value.replace(/\D/g, '')" maxlength="20" type="text" class="form-control" name="bankaccno" value="<c:out value='${accPayment.bankaccno}' />">
                        </div>
                    </div>

                    <div class="form-row mb-3">
                        <div class="form-group col-md-6">
                            <label>Bank Account Type</label> 
                            <select class="form-control" name="bankacctype">
                                <option value="0" <c:if test="${accPayment.bankacctype == '0'}">selected</c:if>>บัญชีกระแสรายวัน</option>
                                <option value="1" <c:if test="${accPayment.bankacctype == '1'}">selected</c:if>>บัญชีออมทรัพย์</option>
                            </select>
                        </div>
                        <div class="form-group col-md-6">
                            <label>Bank Cheque Code Extra</label> 
                            <input step="1" oninput="this.value = this.value.replace(/\D/g, '')" maxlength="2" type="text" class="form-control" name="bankcheqcodeextra" value="<c:out value='${accPayment.bankcheqcodeextra}' />">
                        </div>
                    </div>

                    <hr class="mb-4">

                    <h4>Other Information</h4>

                    <div class="form-row mb-3">
                        <div class="form-group col-md-6">
                            <label>Payment Type</label> 
                            <input step="1" oninput="this.value = this.value.replace(/\D/g, '')" maxlength="2" type="text" class="form-control" name="paytype" value="<c:out value='${accPayment.paytype}' />">
                        </div>
                        <div class="form-group col-md-6">
                            <label>Cross Type</label> 
                            <input step="1" oninput="this.value = this.value.replace(/\D/g, '')" maxlength="1" type="text" class="form-control" name="crosstype" value="<c:out value='${accPayment.crosstype}' />">
                        </div>
                    </div>

                    <div class="form-row mb-3">
                        <div class="form-group col-md-6">
                            <label>Effect Date <span style="color: red">*</span></label> 
                            <input min="<%= java.time.LocalDate.now() %>" max="9999-12-31" maxlength="1" type="date" value="<%=formattedEffectDate%>" class="form-control" name="effectdate">
                        </div>
                        <div class="form-group col-md-6">
                            <label>End Date <span style="color: red">*</span></label> 
                            <input min="<%= java.time.LocalDate.now() %>" max="9999-12-31" maxlength="1" type="date" value="<%=formattedEndDate%>" class="form-control" name="enddate">
                        </div>
                    </div>

                    <div style="margin-top:30;margin-bottom:30">
                        <button type="submit" class="btn btn-success mr-3">Save</button>
                        <button type="button" onclick="history.back()" class="btn btn-danger">Back</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
