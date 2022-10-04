package Ex7;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP {

    public static void main(String[] args) throws IOException {

        ServerSocket ss = new ServerSocket(9001);
        Socket cliSocket = ss.accept();

        InputStream is = cliSocket.getInputStream();
        OutputStream os = cliSocket.getOutputStream();





        ss.close();
    }
}
