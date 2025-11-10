package controller;

import dal.PermissionDAO;
import dal.RoleDAO;
import dal.RolePermissionDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.Employee;
import model.Permission;
import model.RolePermission;

/**
 * Servlet for Role-Permission management (Dynamic Decentralization)
 *
 * @author hgduy
 */
public class DecentralizationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RoleDAO rDAO = new RoleDAO();
        PermissionDAO pDAO = new PermissionDAO();
        HttpSession ses = request.getSession();
        RolePermissionDAO rpDAO = new RolePermissionDAO();
        Employee user = (Employee) ses  .getAttribute("user");
        if (user == null) {
            response.sendRedirect("login");
            return;
        }
        if (user.getRole().getRoleId() != 1) {
            ses.setAttribute("logMessage", "You do not have permission to access this page.");
            response.sendRedirect("dashboard");
            return;
        }
        request.setAttribute("roles", rDAO.getAllRoles());
        request.setAttribute("permissions", pDAO.getAllPermission());
        request.setAttribute("rolepermission", getStringRolePermission(rpDAO.getAllRolePermission()));

        request.getRequestDispatcher("Views/Admin/decentralization.jsp").forward(request, response);
    }

    private List<String> getStringRolePermission(List<RolePermission> rpList) {
        List<String> result = new ArrayList<>();
        for (RolePermission rp : rpList) {
            String str = rp.getPermission().getPermissionId() + "-" + rp.getRole().getRoleId();
            result.add(str);
        }
        return result;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String[] selected = request.getParameterValues("rolepermission");
        RolePermissionDAO rpDAO = new RolePermissionDAO();

        rpDAO.deleteAll();

        if (selected != null) {
            for (String s : selected) {
                try {
                    String[] parts = s.split("-");
                    int permissionId = Integer.parseInt(parts[0]);
                    int roleId = Integer.parseInt(parts[1]);
                    rpDAO.insertRolePermission(roleId, permissionId);
                } catch (Exception e) {
                    System.out.println("Invalid rolepermission value: " + s);
                }
            }
        }

        response.sendRedirect(request.getContextPath() + "/decentralization");
    }
}
