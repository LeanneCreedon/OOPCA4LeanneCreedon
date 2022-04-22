package com.dkit.gd2.leannecreedon.server;

//We are using the Command Design pattern here

import com.dkit.gd2.leannecreedon.core.Packet;

public interface Command
{
    public Packet createResponse(Packet incomingMessage);
}
