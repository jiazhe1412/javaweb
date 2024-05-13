<%-- 
    Document   : adminDashboard
    Created on : 16 Apr 2024, 11:41:29 pm
    Author     : Rong
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.List"%>
<%@page import="model.Payment"%>
<%@page import="model.PaymentService"%>
<%@page import="model.Discount"%>
<%@page import="model.DiscountService"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="../CSS/dashboard.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div>
            <%@ include file = "sideNavForStaff.jsp" %>
        </div>
        <div class="content">

            <div>   
                <div class="wrapper">
                    <h2>
                        Admin Dashboard
                    </h2>
                </div>

                

                <div class="discountData">
                <h3 class="discountTitle">
                    Sales Data
                </h3>
                <div class="discountTable">
                    <table>
                        <thead>
                            <tr>
                                <th>Payment ID</th>
                                <th>Date</th>
                                <th>Payment Method</th>
                                <th>Amount</th>
                                <th>Payment Status</th>
                                <th>Order ID</th>
                            </tr>
                        </thead>

                        <tbody>
                            
                            <c:forEach var="payment" items="${requestScope.paymentList}">
                            <tr>
                                <td>${payment.paymentid}</td>
                                <td>${payment.paymentdate}</td>
                                <td>${payment.paymentmethod}</td>
                                <td>${payment.totalamount}</td>
                                <td>${payment.paymentstatus}</td>
                                <td>${payment.orderid.orderid}</td>
                            </tr>
                            </c:forEach>
                            
                            
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="tableData">
                <h3 class="financeTitle">
                    Discount Data
                </h3>
                <div class="financeTable">
                    <table>
                        <thead>
                            <tr>
                                <th>Discount ID</th>
                                <th>Discount Type</th>
                                <th>Discount Rate</th>
                                <th>Discount Description</th>
                                <th>Prepared By</th>
                                <th>Starting Date</th>
                                <th>Expiry Date</th>
                            </tr>
                        </thead>

                        <tbody>
                            <c:forEach var="discount" items="${requestScope.discountList}">
                            <tr>
                                <td>${discount.discountid}</td>
                                <td>${discount.discounttype}</td>
                                <td>${discount.discountrate}</td>
                                <td>${discount.discountDescription}</td>
                                <td>${discount.staffCreated}</td>
                                <td>${discount.activateDate}</td>
                                <td>${discount.expireDate}</td>
                            </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
                
                

            </div>
    </body>
</html>
