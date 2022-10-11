package Ex8;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Iterator;
// import org.apache.commons.io.FileUtils;

public class ServerTCP {

    private static final String SERVER_DIR = "/Users/manueljuliao/Desktop/test/ServerFiles/";
    private static final int MAX_DATA = 4000;

    public static void main(String[] args) throws IOException {

        int port = Integer.parseInt(args[0]);
        String dir = args[1];
        boolean keepGoing = true;
        ServerSocket ss = new ServerSocket(port);

        while (keepGoing)
        {
            Socket cliSocket = ss.accept();
            System.out.println("Connected to " + cliSocket.getInetAddress().getHostAddress() + ":" + cliSocket.getPort());


            InputStream is = cliSocket.getInputStream();
            OutputStream os = cliSocket.getOutputStream();

            byte[] msgBuffer = new byte[MAX_DATA];
            int nBytes = is.read(msgBuffer);
            String msgRec =new String(msgBuffer, 0, nBytes);
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

            //FileInputStream file = new FileInputStream(String.valueOf(sb));
            FileInputStream file = new FileInputStream(String.valueOf(new_dir));
            int i = 0;
            do{
                byte[] block = new byte[MAX_DATA];
                i = file.read(block);
                if(i>-1){
                    os.write(block, 0, i);
                    System.out.println("Sending " + i + " bytes");
                }
                else {
                    System.out.println("File ended!");
                    keepGoing = false;
                }

            }while(i!=-1);


            cliSocket.close(); // fecha também is e os
        }

        ss.close();
    }
}
