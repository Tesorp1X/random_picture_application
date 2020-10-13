package servlets;

import dbService.DBException;
import dbService.DBService;
import dbService.dataSets.PicturesDataSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class InsertNewPictureServlet extends HttpServlet{

    /**
     *Handles PUT requests on /insertPicture with params link and tags.
     * Used only for PUTing new picture in DB.
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String link = request.getParameter("link");
        String tags = request.getParameter("tags");

        if (link == null) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        DBService dbService = new DBService();
        dbService.printConnectInfo();
        long id_got = -1;

        try {

            id_got = dbService.addPicture(link, tags);

        } catch (DBException e) {

            e.printStackTrace();
        }


        response.setContentType("text/html;charset=utf-8");

        if (id_got != -1) {

            response.getWriter().print(id_got);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.getWriter().print("Error occurred during SQL insertion. Couldn't insert these values.");
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
        }

    }
}
