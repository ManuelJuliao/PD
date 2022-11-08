package Ex9.Ex5;

import Ex9.ServerTime;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Calendar;

public class ServerUDP {

    private static final String GET_TIME = "HORA";

    public static void main(String [] args) throws IOException, ClassNotFoundException {
        boolean keepGoing = true;
        DatagramSocket ds = new DatagramSocket(9001);

        while(keepGoing){
            DatagramPacket dpRec = new DatagramPacket(new byte[256], 256);
            ds.receive(dpRec);
            //String msgRec = new String(dpRec.getData(), 0, dpRec.getLength());
            ByteArrayInputStream bais = new ByteArrayInputStream(dpRec.getData());
            ObjectInputStream ois = new ObjectInputStream(bais);
            String cmd = (String) ois.readObject();

            System.out.println("Received: " + cmd + " from " +
                    dpRec.getAddress().getHostAddress() + ":" + dpRec.getPort());

            if(cmd.equals(GET_TIME))
            {
                Calendar cal = Calendar.getInstance();
                ServerTime st = new ServerTime(
                        cal.get(Calendar.HOUR_OF_DAY),
                        cal.get(Calendar.MINUTE),
                        cal.get(Calendar.SECOND)
                );

                //byte[] curTimeBytes = curTime.getBytes();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(st);
                byte[] curTimeBytes = baos.toByteArray();

                DatagramPacket dpSend = new DatagramPacket(curTimeBytes, curTimeBytes.length, dpRec.getAddress(), dpRec.getPort());
                ds.send(dpSend);
            }
            else
                keepGoing = false;
        }

        ds.close();

    }
}