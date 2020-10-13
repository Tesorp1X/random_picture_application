package servlets;

import dbService.DBException;
import dbService.DBService;
import dbService.dataSets.PicturesDataSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



public class GetPictureServlet extends HttpServlet {

    /**
     * Handles GET requests and "returns" random picture link from sql.
     * Used only to GET link.
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DBService dbService = new DBService();
        System.out.println("THIS IS doGet@GetPictureServlet !!!");
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

        response.getWriter().print(link);
        response.setContentType("text/plain;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

    }

    


}
