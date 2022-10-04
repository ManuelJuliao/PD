package Ex6;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;



public class Client {
    private final String FILE_NAME = "teste";
    private static final String CLIENT_DIR = "/Users/manueljuliao/Desktop/test/CLientFiles/";
    private static final int MAX_DATA = 4000;


    public static void main(String [] args) throws IOException
    {
        DatagramSocket ds = new DatagramSocket();
        //ds.setSoTimeout(3000);

        byte[] msgBytes = "teste.png".getBytes();
        InetAddress ipServer = InetAddress.getByName("127.0.0.1");
        int portServer = 9001;

        DatagramPacket dpSend = new DatagramPacket(
                msgBytes,
                msgBytes.length,
                ipServer,
                portServer);

        ds.send(dpSend);
        int length = 4000;

        StringBuilder sb = new StringBuilder(CLIENT_DIR);
        sb.append("teste.png");
        FileOutputStream file = new FileOutputStream(String.valueOf(sb));

        do{
            DatagramPacket dpRec = new DatagramPacket(new byte[MAX_DATA], MAX_DATA);
            ds.receive(dpRec);
            length = dpRec.getLength();
            System.out.println("Received " + dpRec.getLength() + " bytes");
            file.write(dpRec.getData(), 0, length);


        }while(length>0);

        file.close();





        //System.out.println("[" + dpRec.getAddress().getHostAddress() + ":" +
        //        dpRec.getPort() + "] --> Server Time: " + msgRec);

        ds.close();
    }

}
