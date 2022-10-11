package Ex9.TCP;

import Ex9.ServerTime;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;

public class ServerTCP {

    private static final String GET_TIME = "HORA";

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        boolean keepGoing = true;
        ServerSocket ss = new ServerSocket(9001);

        while (keepGoing)
        {
            Socket cliSocket = ss.accept();
            System.out.println("Connected to " + cliSocket.getInetAddress().getHostAddress() + ":" + cliSocket.getPort());


            //InputStream is = cliSocket.getInputStream();
            //OutputStream os = cliSocket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(cliSocket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(cliSocket.getInputStream());


            String msgRec = (String) ois.readObject();
            System.out.println("Received message " + msgRec + "...");


            if(msgRec.equals(GET_TIME))
            {
                Calendar cal = Calendar.getInstance();
                ServerTime st = new ServerTime(
                        cal.get(Calendar.HOUR_OF_DAY),
                        cal.get(Calendar.MINUTE),
                        cal.get(Calendar.SECOND)
                );

                oos.writeObject(st);
            }
            else
                keepGoing = false;

            cliSocket.close(); // fecha também is e os
        }

        ss.close();
    }
}