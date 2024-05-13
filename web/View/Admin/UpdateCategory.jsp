<%-- 
    Document   : UpdateCategory
    Created on : 9 May 2024, 7:19:13 pm
    Author     : Lee
--%>

<%@page import="model.Category"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="https://cdn.tailwindcss.com"></script>
    </head>
    <body>
        <%
            Category categoryList = (Category) session.getAttribute("categoryList");
//            out.print(categoryList);
        %>
        <div>
            <%@ include file = "sideNavForStaff.jsp" %>
        </div>
        <div style="background-color:rgb(128, 128, 128); height: 100vh; ">
            <div class="w-4/5 ml-56 flex h-screen justify-center items-center">
                <div class="bg-white py-20 px-28 w-full rounded-xl">
                    <h1 class="text-2xl font-bold">Update Category</h1>
                    <div class=" rounded my-3">
                        <form class=" mx-auto" action="../../UpdateCategorySubmit" method="post">
                            <div class="grid grid-cols-2 gap-4">
                                <div class="text-gray-700 font-semibold">
                                    Category ID:
                                </div>
                                <div>
                                    <input type="text" name="categoryID" value="<%= categoryList.getCategoryid()%>" readonly class="border border-gray-300 rounded-md px-3 py-2 w-full bg-gray-100 focus:outline-none focus:ring focus:border-blue-500">
                                </div>
                                <div class="text-gray-700 font-semibold">
                                    Category Type:
                                </div>
                                <div>
                                    <input type="text" name="categoryType" value="<%= categoryList.getCategorytype()%>" class="border border-gray-300 rounded-md px-3 py-2 w-full bg-white focus:outline-none focus:ring focus:border-blue-500">
                                </div>
                                <div class="col-span-2 text-right">
                                    <input type="submit" value="Update" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:ring focus:border-blue-300">
                                </div> 
                            </div>

                        </form>
                    </div>
                </div>
            </div>
        </div> 
    </body>
</html>
