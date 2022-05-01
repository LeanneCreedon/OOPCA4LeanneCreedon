package com.dkit.gd2.leannecreedon.server;
import com.dkit.gd2.leannecreedon.core.Packet;

public interface Command
{
    public Packet createResponse(Packet incomingMessage);
}
