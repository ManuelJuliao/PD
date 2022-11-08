package Ex9.Ex6;

import Ex9.DataBlock;
import Ex9.ServerTime;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;



public class Client {
    private static final String FILE_NAME = "teste.png";
    private static final String CLIENT_DIR = "/Users/manueljuliao/Desktop/test/CLientFiles/";
    private static final int MAX_DATA = 4000;
    private static final int MAX_DATA_DP = 6000;




    public static void main(String [] args) throws IOException, ClassNotFoundException {
        boolean keepGoing = true;
        DatagramSocket ds = new DatagramSocket();
        //ds.setSoTimeout(3000);

        byte[] msgBytes = "teste.png".getBytes();
        InetAddress ipServer = InetAddress.getByName("127.0.0.1");
        int portServer = 9001;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(FILE_NAME);
        byte[] sendBytes = baos.toByteArray();

        DatagramPacket dpSend = new DatagramPacket(
                sendBytes,
                sendBytes.length,
                ipServer,
                portServer);

        ds.send(dpSend);
        int length = MAX_DATA;

        StringBuilder sb = new StringBuilder(CLIENT_DIR);
        sb.append("teste.png");
        FileOutputStream file = new FileOutputStream(String.valueOf(sb));

        do{
            DatagramPacket dpRec = new DatagramPacket(new byte[MAX_DATA_DP], MAX_DATA_DP);
            ds.receive(dpRec);
            //length = dpRec.getLength();
            //System.out.println("Received " + dpRec.getLength() + " bytes");
            ByteArrayInputStream bais = new ByteArrayInputStream(dpRec.getData());
            ObjectInputStream ois = new ObjectInputStream(bais);
            DataBlock db = (DataBlock) ois.readObject();
            if(db.last){ keepGoing = false;}
            else {
                file.write(db.block, 0, db.nBytes);
            }


        }while(keepGoing);

        file.close();





        //System.out.println("[" + dpRec.getAddress().getHostAddress() + ":" +
        //        dpRec.getPort() + "] --> Server Time: " + msgRec);

        ds.close();
    }

}
