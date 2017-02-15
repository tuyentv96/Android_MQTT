package com.example.trantuyen.mqtt3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    static String HostMqtt="trantuyen.name.vn";
    static String Port="1883";

    EditText msgpub;
    Button pubbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        msgpub=(EditText) findViewById(R.id.msgpubedt);
        pubbtn=(Button) findViewById(R.id.publishbtn);
        String clientId = MqttClient.generateClientId();
        final MqttAndroidClient client =
                new MqttAndroidClient(this.getApplicationContext(), "tcp://trantuyen.name.vn:1883",
                        clientId);

        pubbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String topic = "foo/bar";
                String payload = msgpub.getText().toString();
                byte[] encodedPayload = new byte[0];
                try {
                    encodedPayload = payload.getBytes("UTF-8");
                    MqttMessage message = new MqttMessage(encodedPayload);
                    client.publish(topic, message);
                    Toast.makeText(MainActivity.this,"Publish messeage complete ",Toast.LENGTH_SHORT).show();
                } catch (UnsupportedEncodingException | MqttException e) {
                    e.printStackTrace();
                }
            }
        });


        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Toast.makeText(MainActivity.this, "fail", Toast.LENGTH_SHORT).show();

                }
            });
        } catch (MqttException e) {

        }
    }

}