package main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.GetPictureServlet;
import dbService.DBException;
import dbService.DBService;
import dbService.dataSets.PicturesDataSet;
import servlets.InsertNewPictureServlet;


public class Main {
    public static void main(String[] args) throws Exception {

        GetPictureServlet getPictureServlet = new GetPictureServlet();
        InsertNewPictureServlet insertNewPictureServlet = new InsertNewPictureServlet();


        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(getPictureServlet), "/getPicture");
        context.addServlet(new ServletHolder(insertNewPictureServlet), "/insertPicture");

        Server server = new Server(8080);
        server.setHandler(context);


        server.start();
    }


}
