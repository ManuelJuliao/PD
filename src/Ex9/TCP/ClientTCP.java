package Ex9.TCP;

import Ex9.ServerTime;

import java.io.*;
import java.net.Socket;


public class ClientTCP {

    private static final String GET_TIME = "HORA";

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Socket cliSocket = new Socket("127.0.0.1", 9001);
        System.out.println("Connected to " + cliSocket.getInetAddress().getHostAddress() + ":" + cliSocket.getPort());

        ObjectOutputStream oos = new ObjectOutputStream(cliSocket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(cliSocket.getInputStream());


        oos.writeObject(GET_TIME);

        try
        {

            //String msgRec = new String(msgBuffer, 0, nBytes);
            ServerTime msgRec = (ServerTime) ois.readObject();
            System.out.println("Current server time: " + msgRec);
        }
        catch(EOFException e) {
            System.out.println("Socket is closed");
        }
        cliSocket.close();
    }
}

