package controller;

import model.DiscountService;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import model.*;

@WebServlet(name = "DiscountCreate", urlPatterns = {"/DiscountCreate"})
public class DiscountCreate extends HttpServlet {

private String getDiscountID() {
        Query query = em.createQuery("SELECT MAX(d.discountid) FROM Discount d");
        String maxDiscountCodeStr = (String) query.getSingleResult();

        if (maxDiscountCodeStr == null || maxDiscountCodeStr.isEmpty()) {
            return "D0001";
        } else {
            String numericPart = maxDiscountCodeStr.substring(1); 
            int numericValue = Integer.parseInt(numericPart);
            numericValue++; 
            return String.format("D%04d", numericValue);
        }
    }

    private boolean StaffErr(String staffCreate) {
        return staffCreate.matches("[a-zA-Z ]+");
    }

    private boolean discountRateErr(double discountRate) {
        return 0 < discountRate;
    }

    @PersistenceContext()
    EntityManager em;
    @Resource
    UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        String discountID = getDiscountID();
        String staffCreate = request.getParameter("discountAddStaffCreate");
        String category = request.getParameter("discountAddSelectCategory");
        String productid = request.getParameter("selectProduct");
        double discountRate = Double.parseDouble(request.getParameter("discountAddRate"));
        String description = request.getParameter("decriptionofAddDiscount");
        // Format the current date to a string
        String formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String expiredate = request.getParameter("discountAddExpireDate");

        // Convert String into LocaDate
        LocalDate parsedDate = LocalDate.parse(formattedDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate parsedDateofExpireDate = LocalDate.parse(expiredate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // Convert LocalDate to Date
        Date dateActivate = java.sql.Date.valueOf(parsedDate);
        Date dateExpire = java.sql.Date.valueOf(parsedDateofExpireDate);

        HttpSession session = request.getSession();
        boolean valid = true;
        String staffError = "";
        String discountRateError = "";

        if (!StaffErr(staffCreate)) {
            staffError = "Please only insert alphabet!";
            valid = false;
        }
        if (!discountRateErr(discountRate)) {
            discountRateError = "Discount Rate should not be 0 or less than 0";
            valid = false;
        }

        if (valid == true) {
            try {
                ProductService productService = new ProductService(em);
                Product product = productService.findProductId(productid);
                Discount discount = new Discount(discountID, staffCreate, category, discountRate, description, dateActivate, dateExpire, product);
                System.out.println(discount);
                DiscountService discountService = new DiscountService(em);

                utx.begin();
                boolean addStatus = discountService.addDiscount(discount);
                utx.commit();

                List<Discount> dislist = discountService.findAll();
                dislist.add(discount);
                discount.AddDiscount(dislist);
                request.setAttribute("DiscountList", discountService.findAll());
                RequestDispatcher rd = request.getRequestDispatcher("/DiscountView");
                rd.forward(request, response);

            } catch (Exception ex) {
                out.println("<p>" + "error: " + ex.getMessage() + "</p>");
            }
        } else {
            request.setAttribute("staffError", staffError);
            request.setAttribute("discountRateError", discountRateError);
            RequestDispatcher rd = request.getRequestDispatcher("/View/Admin/discountAdd.jsp");
            rd.forward(request, response);

        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
