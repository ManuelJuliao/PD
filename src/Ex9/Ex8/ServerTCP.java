package Ex9.Ex8;

import Ex9.DataBlock;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Iterator;
// import org.apache.commons.io.FileUtils;

public class ServerTCP {

    private static final String SERVER_DIR = "/Users/manueljuliao/Desktop/test/ServerFiles/";
    private static final int MAX_DATA = 4000;

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        int port = Integer.parseInt(args[0]);
        String dir = args[1];
        boolean keepGoing = true;
        ServerSocket ss = new ServerSocket(port);

        while (keepGoing)
        {
            Socket cliSocket = ss.accept();
            System.out.println("Connected to " + cliSocket.getInetAddress().getHostAddress() + ":" + cliSocket.getPort());


            ObjectOutputStream oos = new ObjectOutputStream(cliSocket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(cliSocket.getInputStream());


            String msgRec = (String) ois.readObject();
            String new_dir = "";

            StringBuilder sb = new StringBuilder(dir);
            sb.append(msgRec);
            System.out.println("Directory: " + sb);

            /*Collection files = FileUtils.listFiles(dir, null, true);

            for (Iterator iterator = files.iterator(); iterator.hasNext();) {
                File file_aux = (File) iterator.next();
                if (file_aux.getName().equals(msgRec)){
                    System.out.println(file_aux.getAbsolutePath());
                    new_dir = file_aux.getAbsolutePath();
                }
            }
            */

            FileInputStream file = new FileInputStream(String.valueOf(sb));
            //FileInputStream file = new FileInputStream(String.valueOf(new_dir));
            int i = 0, j = 0;
            do{
                DataBlock db = new DataBlock();
                byte[] auxb = new byte[MAX_DATA];
                i = file.read(auxb);
                db.setBlock(auxb);
                db.setBytes(i);
                if(i>0){
                    oos.writeObject(db);
                    System.out.println("Sending " + i + " bytes");
                    j++;
                }
                else {
                    System.out.println("File ended!");
                    keepGoing = false;
                }

            }while(i!=-1);

            DataBlock db = new DataBlock(true);
            oos.writeObject(db);
            System.out.println(j + " blocks of data sent");


            cliSocket.close(); // fecha também is e os
        }

        ss.close();
    }
}

