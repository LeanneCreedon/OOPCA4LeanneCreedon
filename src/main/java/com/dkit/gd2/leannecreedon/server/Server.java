package com.dkit.gd2.leannecreedon.server;
import com.dkit.gd2.leannecreedon.core.Details;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;

public class Server
{
    public static void main(String[] args)
    {
        try
        {
            //Step 1: Set up a connection socket that listens for connection
            ServerSocket listeningSocket = new ServerSocket(Details.SERVER_PORT);

            boolean continueRunning = true;
            int countRequests = 0;

            while(continueRunning)
            {
                //Step 2: wait for an incoming connection and build the communications link
                // - dataSocket for communication
                System.out.println("Waiting for connections...");
                Socket dataSocket = listeningSocket.accept();

                //Create and start a new thread per client
                ThreadPerClient runnable = new ThreadPerClient(dataSocket);
                Thread clientThread = new Thread(runnable);
                clientThread.start();
            }
            listeningSocket.close();
        }
        catch (NoSuchElementException nse)
        {
            System.out.println(nse.getMessage());
        }
        catch(IOException ioe)
        {
            System.out.println(ioe.getMessage());
        }
    }
}
