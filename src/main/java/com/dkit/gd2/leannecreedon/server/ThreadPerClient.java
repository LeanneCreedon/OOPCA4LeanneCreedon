package com.dkit.gd2.leannecreedon.server;

import com.dkit.gd2.leannecreedon.core.Packet;
import com.dkit.gd2.leannecreedon.core.Protocol;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ThreadPerClient implements Runnable
{
    private Socket dataSocket;

    public ThreadPerClient(Socket dataSocket)
    {
        this.dataSocket = dataSocket;
    }

    @Override
    public void run()
    {
        try
        {
            OutputStream out = dataSocket.getOutputStream();
            PrintWriter output = new PrintWriter(new OutputStreamWriter(out));

            InputStream in = dataSocket.getInputStream();
            Scanner input = new Scanner(new InputStreamReader(in));

            Packet incomingPacket = new Packet(Protocol.NONE, null);
            Packet response;

            while (!incomingPacket.getMessageType().equals(Protocol.END))
            {
                response = null;

                //take information from the client
                incomingPacket.readFromJSON(new JSONObject(input.nextLine()));
                System.out.println("Received message " + incomingPacket);

                CommandFactory factory = new CommandFactory();
                //Figure out what command was sent by the client
                Command command = factory.createCommand(incomingPacket.getMessageType());

                if(command != null)
                {
                    response = command.createResponse(incomingPacket);
                }

                //Send the response to the client
                output.println(response.writeJSON());
                output.flush();
            }
            dataSocket.close();
        }
        catch (IOException ioe)
        {
            System.out.println(ioe.getMessage());
        }
    }
}
