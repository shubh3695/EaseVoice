package kushubham.com.easevoice;

/**
 * Created by admin on 3/4/2016.
 */

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
public class ClientSide extends AppCompatActivity
{
    TextView chatMsg,setName;

    EditText editTextSay;
    ImageButton buttonSend;
   // Button buttonDisconnect;
   MediaPlayer sendtone;
    String msgLog = "";
private  String username ,address;
    private int port;
    ChatClientThread chatClientThread = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client);

        sendtone=MediaPlayer.create(ClientSide.this,R.raw.send);

        setName = (TextView) findViewById(R.id.setname);

        chatMsg = (TextView) findViewById(R.id.chatmsg);


        editTextSay = (EditText)findViewById(R.id.say);
        buttonSend = (ImageButton)findViewById(R.id.send);
        Bundle bundle=getIntent().getExtras();
        username=bundle.getString("clientusername");
        address=bundle.getString("address");
        port=bundle.getInt("port");
        msgLog = "";
        setName.setText("Client : " +username   );
        chatMsg.setText(msgLog);

        chatClientThread = new ChatClientThread(
                username, address, port);
        chatClientThread.start();

        buttonSend.setOnClickListener(buttonSendOnClickListener);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

            getMenuInflater().inflate(R.menu.menu_disconnect, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement

        if (id == R.id.action_disconnect) {
            if(chatClientThread!=null){
                chatClientThread.disconnect();
            }

            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    OnClickListener buttonSendOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (editTextSay.getText().toString().equals("")) {
                return;
            }

            if(chatClientThread==null){
                return;
            }
            sendtone.start();
            chatClientThread.sendMsg(editTextSay.getText().toString() + "\n");
            editTextSay.setText("");
        }

    };


    private class ChatClientThread extends Thread {

        String name;
        String dstAddress;
        int dstPort;

        String msgToSend = "";
        boolean goOut = false;

        ChatClientThread(String name, String address, int port) {
            this.name = name;
            dstAddress = address;
            dstPort = port;
        }
        @Override
        public void run() {
            Socket socket = null;
            DataOutputStream dataOutputStream = null;
            DataInputStream dataInputStream = null;
            try {
                socket = new Socket(dstAddress, dstPort);
                dataOutputStream = new DataOutputStream(
                        socket.getOutputStream());
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream.writeUTF(name);
                dataOutputStream.flush();
                while (!goOut) {
                    if (dataInputStream.available() > 0) {
                        msgLog += dataInputStream.readUTF();
                        ClientSide.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(msgLog.contains("leaved"))
                                    msgLog.replace("leaved","left");
                                chatMsg.setText(msgLog+"\n");
                            }
                        });
                    }

                    if(!msgToSend.equals("")){
                        dataOutputStream.writeUTF(msgToSend);
                        dataOutputStream.flush();
                        msgToSend = "";
                    }
                }

            } catch (UnknownHostException e) {
                e.printStackTrace();
                final String eString = e.toString();
                ClientSide.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(ClientSide.this, eString, Toast.LENGTH_LONG).show();
                    }

                });
            } catch (IOException e) {
                e.printStackTrace();
                final String eString = e.toString();
                ClientSide.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(ClientSide.this, eString, Toast.LENGTH_LONG).show();
                    }

                });
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                if (dataOutputStream != null) {
                    try {
                        dataOutputStream.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                if (dataInputStream != null) {
                    try {
                        dataInputStream.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                ClientSide.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), ClientLogin.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                    }
                });
            }
        }
        private void sendMsg(String msg){
            msgToSend = msg;
        }

        private void disconnect(){
            goOut = true;
        }
    }
}