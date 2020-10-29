package servlets;

import dbService.DBException;
import dbService.DBService;
import dbService.dataSets.PicturesDataSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class GetPictureServlet extends HttpServlet {

    private final DBService dbService;

    public GetPictureServlet(DBService dbService) {
        this.dbService = dbService;
    }

    /**
     * Handles GET requests on /getPicture and "returns" random picture link from sql.
     * Used only for GETing link from DB.
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {


        dbService.printConnectInfo();

        response.setContentType("text/plain;charset=utf-8");
        response.addHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));

        String method = request.getParameter("method");
        long id = -1;

        if (method != null) {
            String id_str = request.getParameter("id");
            if (id_str == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            id = Long.parseLong(id_str);
        }


        String link = "";

        try {

            PicturesDataSet dataSet;

            if (method == null || method.equals("random")) {
                dataSet = dbService.getRandomPicture();
            } else if (id != -1) {
                dataSet = dbService.getPictureById(id);
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }


            if (dataSet == null) {

                link = "none";

            } else {

                link = dataSet.getLink();
            }


        } catch (DBException e) {

            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            e.printStackTrace();
        }


        response.getWriter().print(link);

        response.setStatus(HttpServletResponse.SC_OK);

    }

}
