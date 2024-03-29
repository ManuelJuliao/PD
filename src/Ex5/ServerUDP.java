package Ex5;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerUDP {

    public static void main(String [] args) throws IOException
    {
        boolean keepGoing = true;
        DatagramSocket ds = new DatagramSocket(9001);

        while(keepGoing){
            DatagramPacket dpRec = new DatagramPacket(new byte[256], 256);
            ds.receive(dpRec);
            String msgRec = new String(dpRec.getData(), 0, dpRec.getLength());

            System.out.println("Received: " + msgRec + " from " +
                    dpRec.getAddress().getHostAddress() + ":" + dpRec.getPort());

            if(msgRec.equals("HORA"))
            {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                String curTime = sdf.format(new Date());

                byte[] curTimeBytes = curTime.getBytes();

                /*
                InetAddress ipClient = dpRec.getAddress();
                int portClient = dpRec.getPort();

                DatagramPacket dpSend = new DatagramPacket(
                        curTimeBytes,
                        curTimeBytes.length,
                        ipClient,
                        portClient);

                ds.send(dpSend);
                */

                dpRec.setData(curTimeBytes);
                dpRec.setLength(curTimeBytes.length);
                ds.send(dpRec);
            }
            else
                keepGoing = false;
        }

        ds.close();

    }
}
