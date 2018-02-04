package MQTT;

import org.fusesource.mqtt.client.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Created by Kalaman on 09.01.18.
 */
public class MQTTClient {
    private MQTT mqtt;
    private BlockingConnection connection;
    private Topic [] topic;
    private Thread sessionThread;

    private ArrayList<MQTTListener> mqttListener;
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 1883;
    private static final String TOPIC_NAME = "cart_unlock";

    public MQTTClient() {
        mqtt = new MQTT();
        mqttListener = new ArrayList<MQTTListener>();
        topic = new Topic[] {new Topic(TOPIC_NAME, QoS.EXACTLY_ONCE)};

        try {
            mqtt.setHost(SERVER_IP, SERVER_PORT);
            connection = mqtt.blockingConnection();
            connection.connect();
            connection.subscribe(topic);

            sessionThread = new Thread() {
                @Override
                public void run() {
                    try {
                        boolean locked = true;
                        while(locked) {
                            Message message = connection.receive();
                            String payload = new String(message.getPayload(), StandardCharsets.UTF_8);
                            message.ack();

                            if (payload.equalsIgnoreCase(Constants.MAC_ADRESSE)) {
                                for (MQTTListener listener : mqttListener)
                                    listener.onSessionStarted();
                                locked = false;
                            }
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            };
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public interface MQTTListener {
        public void onSessionStarted();
    }

    public boolean addMQTTListener(MQTTListener listener) {
        return mqttListener.add(listener);
    }

    public boolean removeMQTTListener(MQTTListener listener) {
        return mqttListener.remove(listener);
    }

    public void startSessionThread () {
        sessionThread.start();
    }
    public void stopSessionThread () {
        sessionThread.stop();
    }


}
