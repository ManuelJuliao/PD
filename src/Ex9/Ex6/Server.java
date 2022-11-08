package Ex9.Ex6;

import Ex9.DataBlock;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;


public class Server {
    private static final String SERVER_DIR = "/Users/manueljuliao/Desktop/test/ServerFiles/";
    private static final int MAX_DATA = 4000;

    public static void main(String [] args) throws IOException, ClassNotFoundException {
        boolean keepGoing = true;
        DatagramSocket ds = new DatagramSocket(9001);

        while(keepGoing){
            DatagramPacket dpRec = new DatagramPacket(new byte[MAX_DATA], MAX_DATA);
            ds.receive(dpRec);
            ByteArrayInputStream bais = new ByteArrayInputStream(dpRec.getData());
            ObjectInputStream ois = new ObjectInputStream(bais);
            String cmd = (String) ois.readObject();

            System.out.println("Received: " + cmd + " from " +
                    dpRec.getAddress().getHostAddress() + ":" + dpRec.getPort());


            StringBuilder sb = new StringBuilder(SERVER_DIR);
            sb.append(cmd);

            System.out.println("Directory: " + sb);
            FileInputStream file = new FileInputStream(String.valueOf(sb));
            int i = 0, j=0;
            do{
                //byte[] block = new byte[MAX_DATA];
                DataBlock db = new DataBlock();
                //i = file.read(db.block);
                byte[] auxb = new byte[MAX_DATA];
                i = file.read(auxb);
                db.setBlock(auxb);
                db.setBytes(i);
                if(i>0){
                    //dpRec.setData(db.block);
                    //dpRec.setLength(i);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    oos.writeObject(db);
                    byte[] blockBytes = baos.toByteArray();
                    DatagramPacket dpSend = new DatagramPacket(blockBytes, blockBytes.length, dpRec.getAddress(), dpRec.getPort());
                    ds.send(dpSend);
                    System.out.println("Sending " + i + " bytes");
                    j++;
                }

            }while(i!=-1);
            //dpRec.setLength(0);
            DataBlock db = new DataBlock(true);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(db);
            byte[] blockBytes = baos.toByteArray();
            DatagramPacket dpSend = new DatagramPacket(blockBytes, blockBytes.length, dpRec.getAddress(), dpRec.getPort());
            ds.send(dpSend);

            System.out.println(j + " blocks of data sent");
        }

        ds.close();

    }

}
