package Ex7;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientTCP {

    private static final String GET_TIME = "HORA";

    public static void main(String[] args) throws IOException {

        Socket cliSocket = new Socket("127.0.0.1", 9001);
        System.out.println("Connected to " + cliSocket.getInetAddress().getHostAddress() + ":" + cliSocket.getPort());

        InputStream is = cliSocket.getInputStream();
        OutputStream os = cliSocket.getOutputStream();

        os.write("HORA".getBytes());

        byte[] msgBuffer = new byte[256];
        int nBytes = is.read(msgBuffer);

        if(nBytes > -1)
        {
            String msgRec = new String(msgBuffer, 0, nBytes);
            System.out.println("Current server time: " + msgRec);
        }
        else
            System.out.println("Socket is closed");

        cliSocket.close();
    }
}
