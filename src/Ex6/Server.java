package Ex6;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;


public class Server {
    private static final String SERVER_DIR = "/Users/manueljuliao/Desktop/test/ServerFiles/";
    private static final int MAX_DATA = 4000;

    public static void main(String [] args) throws IOException
    {
        boolean keepGoing = true;
        DatagramSocket ds = new DatagramSocket(9001);

        while(keepGoing){
            DatagramPacket dpRec = new DatagramPacket(new byte[MAX_DATA], MAX_DATA);
            ds.receive(dpRec);
            String msgRec = new String(dpRec.getData(), 0, dpRec.getLength());

            System.out.println("Received: " + msgRec + " from " +
                    dpRec.getAddress().getHostAddress() + ":" + dpRec.getPort());


            StringBuilder sb = new StringBuilder(SERVER_DIR);
            sb.append(msgRec);

            System.out.println("Directory: " + sb);
            FileInputStream file = new FileInputStream(String.valueOf(sb));
            int i = 0, j=0;
            do{
                byte[] block = new byte[MAX_DATA];
                i = file.read(block);
                if(i>0){
                    dpRec.setData(block);
                    dpRec.setLength(i);
                    ds.send(dpRec);
                    System.out.println("Sending " + i + " bytes");
                    j++;
                }

            }while(i!=-1);
            dpRec.setLength(0);
            ds.send(dpRec);

            System.out.println(j + " blocks of data sent");
        }

        ds.close();

    }

}
