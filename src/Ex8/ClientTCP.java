package Ex8;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.Socket;
import java.util.Scanner;

public class ClientTCP {

    private final String FILE_NAME = "teste";
    private static final String CLIENT_DIR = "/Users/manueljuliao/Desktop/test/CLientFiles/";
    private static final int MAX_DATA = 4000;

    public static void main(String[] args) throws IOException {

        String serverIP = args[0];
        String client_dir = args[1];
        String file_name = args[2];

        boolean keepGoing = true;
        Socket cliSocket = new Socket(serverIP, 9001);
        System.out.println("Connected to " + cliSocket.getInetAddress().getHostAddress() + ":" + cliSocket.getPort());

        InputStream is = cliSocket.getInputStream();
        OutputStream os = cliSocket.getOutputStream();

        byte[] msgBytes = file_name.getBytes();
        os.write(msgBytes);

        StringBuilder sb = new StringBuilder(client_dir);
        sb.append(file_name);
        FileOutputStream file = new FileOutputStream(String.valueOf(sb));

        do{
            byte[] msgBuffer = new byte[MAX_DATA];
            int nBytes = is.read(msgBuffer);
            if(nBytes > -1)
            {
                System.out.println("Received " + nBytes + " bytes");
                file.write(msgBuffer, 0, nBytes);
            }
            else keepGoing = false;




        }while(keepGoing);


        cliSocket.close();
    }
}
