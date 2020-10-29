package main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import servlets.GetPictureServlet;
import servlets.InsertNewPictureServlet;

import dbService.DBService;




public class Main {
    public static void main(String[] args) throws Exception {

        DBService dbService = new DBService();

        GetPictureServlet getPictureServlet = new GetPictureServlet(dbService);
        InsertNewPictureServlet insertNewPictureServlet = new InsertNewPictureServlet(dbService);


        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(getPictureServlet), "/getPicture");
        context.addServlet(new ServletHolder(insertNewPictureServlet), "/insertPicture");

        Server server = new Server(8080);
        server.setHandler(context);


        server.start();
    }


}
