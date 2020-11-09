package servlets;

import dbService.DBService;
import dbService.dataSets.PicturesDataSet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetListOfPicturesServlet extends HttpServlet {

    private final DBService dbService;

    public GetListOfPicturesServlet (DBService dbService) {

        this.dbService = dbService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/plain;charset=utf-8");
        response.addHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));

        String max_res = request.getParameter("max_res");
        String start_p = request.getParameter("start");

        List<PicturesDataSet> picturesList = null;

        if (max_res != null && start_p != null) {
            if (max_res.matches("\\d+") && start_p.matches("\\d+")) {

                int max = Integer.parseInt(max_res);
                int start = Integer.parseInt(start_p);

                picturesList = dbService.getListOfPictures(start, max);
            } else {
                response.getWriter().print("ERROR: values must be positive integers.");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }

        } else {
            picturesList = dbService.getListOfPictures();
        }

        if (picturesList != null) {
            for (var o : picturesList) {
                response.getWriter().println(o.toString());
            }
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }


    }
}
