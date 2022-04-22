package com.dkit.gd2.leannecreedon.core;

import org.json.JSONObject;

public class Packet
{
    //Attributes
    private Protocol messageType;
    private String payload;

    //Constructor
    public Packet(Protocol messageType, String payload)
    {
        this.messageType = messageType;
        this.payload = payload;
    }

    //Getters
    public Protocol getMessageType()
    {
        return messageType;
    }

    public String getPayload()
    {
        return payload;
    }

    //toString()
    @Override
    public String toString()
    {
        return "Packet{" +
                "messageType=" + messageType +
                ", payload='" + payload + '\'' +
                '}';
    }

    //Convert NetworkMessage to JSONObject

    public JSONObject writeJSON()
    {
        JSONObject jo = new JSONObject();
        jo.put("messageType", this.messageType);
        jo.put("payload", this.payload);
        return jo;
    }

    //Convert JSONObject to NetworkMessage
    public void readFromJSON(JSONObject jo)
    {
        this.setMessageType(Protocol.valueOf(jo.get("messageType").toString()));
        this.setPayload(jo.get("payload").toString());
    }

    //Setters
    public void setPayload(String payload)
    {
        this.payload = payload;
    }

    public void setMessageType(Protocol messageType)
    {
        this.messageType = messageType;
    }
}
