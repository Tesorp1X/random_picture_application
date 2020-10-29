package servlets;

import dbService.DBException;
import dbService.DBService;
import dbService.dataSets.PicturesDataSet;
import org.apache.commons.validator.routines.UrlValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class InsertNewPictureServlet extends HttpServlet{

    private final DBService dbService;

    public InsertNewPictureServlet(DBService dbService) {
        this.dbService = dbService;
    }

    /**
     *Handles GET requests on /insertPicture with params link and tags.
     * Used only for PUTing new picture in DB.
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String link = request.getParameter("link");
        String tags = request.getParameter("tags");

        response.setContentType("text/plain;charset=utf-8");
        response.addHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));

        UrlValidator validator = new UrlValidator();

        if (link == null || validator.isValid(link)) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        dbService.printConnectInfo();
        long id_got = -1;

        try {

            id_got = dbService.addPicture(link, tags);

        } catch (DBException e) {

            e.printStackTrace();
        }


        if (id_got != -1) {

            response.getWriter().print(id_got);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {

            response.getWriter().print("Error occurred during SQL insertion. Couldn't insert these values.");
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
        }

    }
}
