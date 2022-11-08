package Ex9.Ex8;

import Ex9.DataBlock;

import java.io.*;
import java.net.DatagramPacket;
import java.net.Socket;
import java.util.Scanner;

public class ClientTCP {

    private static final String FILE_NAME = "teste";
    private static final String CLIENT_DIR = "/Users/manueljuliao/Desktop/test/CLientFiles/";
    private static final int MAX_DATA = 4000;

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        String serverIP = args[0];
        String client_dir = args[1];
        String file_name = args[2];

        boolean keepGoing = true;
        Socket cliSocket = new Socket(serverIP, 9001);
        System.out.println("Connected to " + cliSocket.getInetAddress().getHostAddress() + ":" + cliSocket.getPort());

        ObjectOutputStream oos = new ObjectOutputStream(cliSocket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(cliSocket.getInputStream());


        oos.writeObject(file_name);

        StringBuilder sb = new StringBuilder(client_dir);
        sb.append(file_name);
        FileOutputStream file = new FileOutputStream(String.valueOf(sb));

        do{
            DataBlock db = (DataBlock) ois.readObject();
            if(db.last){keepGoing = false;}
            else{
                file.write(db.block, 0, db.nBytes);
            }





        }while(keepGoing);


        cliSocket.close();
        file.close();
    }
}
