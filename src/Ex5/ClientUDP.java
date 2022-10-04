package Ex5;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientUDP {
    public static void main(String [] args) throws IOException
    {
        DatagramSocket ds = new DatagramSocket();
        ds.setSoTimeout(3000);

        byte[] msgBytes = "HORA".getBytes();
        InetAddress ipServer = InetAddress.getByName("127.0.0.1");
        int portServer = 9001;

        DatagramPacket dpSend = new DatagramPacket(
                msgBytes,
                msgBytes.length,
                ipServer,
                portServer);

        ds.send(dpSend);

        DatagramPacket dpRec = new DatagramPacket(new byte[256], 256);
        ds.receive(dpRec);
        String msgRec = new String(dpRec.getData(), 0, dpRec.getLength());

        System.out.println("[" + dpRec.getAddress().getHostAddress() + ":" +
                dpRec.getPort() + "] --> Server Time: " + msgRec);

        ds.close();
    }
}
