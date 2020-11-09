package servlets;

import dbService.DBService;
import dbService.NotFoundException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeletePictureServlet extends HttpServlet {

    private final DBService dbService;

    public DeletePictureServlet(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=utf-8");
        response.addHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));

        String id_str = request.getParameter("id");
        if (id_str == null || !id_str.matches("\\d+")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        } else {
            try {

                dbService.deletePictureById(Long.parseLong(id_str));
                response.getWriter().println("Deleted!");
                response.setStatus(HttpServletResponse.SC_OK);

            } catch (NotFoundException e) {
                response.getWriter().println(e.toString());
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }

    }
}
