package servlets;

import dbService.DBException;
import dbService.DBService;
import dbService.dataSets.PicturesDataSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;


public class GetPictureServlet extends HttpServlet {

    /**
     * Handles GET requests on /getPicture and "returns" random picture link from sql.
     * Used only for GETing link from DB.
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        DBService dbService = new DBService();
        dbService.printConnectInfo();

        String link = "";

        try {

            PicturesDataSet dataSet = dbService.getRandomPicture();


            if (dataSet == null) {

                link = "none";

            } else {

                link = dataSet.getLink();
            }


        } catch (DBException e) {

            e.printStackTrace();
        }


        response.setContentType("text/plain;charset=utf-8");
        response.getWriter().print(link);

        response.addHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setStatus(HttpServletResponse.SC_OK);

    }

}
