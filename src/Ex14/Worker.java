package Ex14;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Worker {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        int port = Integer.parseInt(args[0]);
        ServerSocket ss = new ServerSocket(port);

        Socket cliSocket = ss.accept();
        System.out.println("Connected to " + cliSocket.getInetAddress().getHostAddress() + ":" + cliSocket.getPort());

        ObjectOutputStream oos = new ObjectOutputStream(cliSocket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(cliSocket.getInputStream());
        Data msgRec = (Data) ois.readObject();

        double pi = PiCalculator.getPartialPiValue(msgRec.id, msgRec.nr_workers, msgRec.n_intervalos);
        System.out.println(pi);
        oos.writeObject(pi);

        cliSocket.close();
        ss.close();

    }
}
