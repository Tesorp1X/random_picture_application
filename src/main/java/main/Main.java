package main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.GetPictureServlet;
import dbService.DBException;
import dbService.DBService;
import dbService.dataSets.PicturesDataSet;


public class Main {
    public static void main(String[] args) throws Exception {

        GetPictureServlet getPictureServlet = new GetPictureServlet();

        DBService dbService = new DBService();
        dbService.printConnectInfo();


        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(getPictureServlet), "/getPicture");

        Server server = new Server(8080);
        server.setHandler(context);


        server.start();
    }


}
