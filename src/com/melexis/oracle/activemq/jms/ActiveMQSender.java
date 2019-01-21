package com.melexis.oracle.activemq.jms;

import java.sql.Array;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.Struct;
import javax.jms.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class ActiveMQSender
{
    private String jmsurl = "tcp://localhost:61601?jms.useAsyncSend=true";
    private String topicName = "oracle.jms.store";

    public ActiveMQSender(String jmsurl, String topicName)
    {
        this.jmsurl = jmsurl;
        this.topicName = topicName;
    }

    public boolean sendMessage(Array eventParameters, Clob eventData) throws Exception {
        try {

            Struct obj;
            String name;
            String value;
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(this.jmsurl);

            Connection connection = connectionFactory.createConnection();
            ((ActiveMQConnection)connection).setUseAsyncSend(true);
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createTopic("VirtualTopic." + this.topicName);
            MessageProducer producer = session.createProducer(destination);

            StreamMessage msg = session.createStreamMessage();

            ResultSet rs = eventParameters.getResultSet();

            while (rs.next()) {
                obj = (Struct)rs.getObject(2);
                Object[] p = obj.getAttributes();
                name = (String) p[0];
                value = (String) p[1];

                msg.setStringProperty(name, value);
            }

            if(eventData != null) {
                long len = eventData.length();
                String eventDataString = eventData.getSubString(1, (int) len);
                msg.setStringProperty("EVENT_DATA", eventDataString);
            }

            producer.send(msg);

            session.close();
            connection.close();

            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
