package Ex9.Ex5;

import Ex9.ServerTime;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class ClientUDP {
    private static final String GET_TIME = "HORA";

    public static void main(String [] args) throws IOException, ClassNotFoundException {
        DatagramSocket ds = new DatagramSocket();
        ds.setSoTimeout(3000);

        byte[] msgBytes = "HORA".getBytes();
        InetAddress ipServer = InetAddress.getByName("127.0.0.1");
        int portServer = 9001;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(GET_TIME);
        byte[] TimeBytes = baos.toByteArray();

        DatagramPacket dpSend = new DatagramPacket(
                TimeBytes,
                TimeBytes.length,
                ipServer,
                portServer);

        ds.send(dpSend);

        DatagramPacket dpRec = new DatagramPacket(new byte[256], 256);
        ds.receive(dpRec);
        //String msgRec = new String(dpRec.getData(), 0, dpRec.getLength());
        ByteArrayInputStream bais = new ByteArrayInputStream(dpRec.getData());
        ObjectInputStream ois = new ObjectInputStream(bais);
        ServerTime cmd = (ServerTime) ois.readObject();


        System.out.println("[" + dpRec.getAddress().getHostAddress() + ":" +
                dpRec.getPort() + "] --> Server Time: " + cmd);

        ds.close();
    }
}
