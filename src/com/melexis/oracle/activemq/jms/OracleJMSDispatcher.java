package com.melexis.oracle.activemq.jms;

import java.sql.Clob;
import java.sql.Array;

public class OracleJMSDispatcher {

    public static boolean sendMessage(String activemqBrokerUrl, String eventName, Array eventParameters, Clob eventData) throws Exception {

        ActiveMQSender sender = new ActiveMQSender( activemqBrokerUrl , eventName);
        try {
            return sender.sendMessage( eventParameters, eventData);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}