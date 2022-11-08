package Ex14;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Master {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        String filename = args[0];
        int n = Integer.parseInt(args[1]);

        FileInputStream fis = new FileInputStream(filename);
        Scanner sc = new Scanner(fis);    //file to be scanned
        int i = 0;
        String[] addresses = new String[3];
        int[] ports = new int[3];

        while (sc.hasNextLine()) {

            String line = sc.nextLine();
            if(line.length()==0){
                continue;
            }
            String[] lineParts = line.split(" ");
            if(!lineParts[0].equals("localhost")){
                if(!lineParts[0].equals("127.0.0.1")){
                    continue;
                }
            }
            if(lineParts[1]==null){
                continue;
            }
            else if(lineParts[1].length()>4){
                continue;
            }

            addresses[i] = lineParts[0];
            ports[i] = Integer.parseInt(lineParts[1]);
            System.out.println(addresses[i] + " " + ports[i]);
            i++;

        }
        sc.close();

        ObjectOutputStream[] oos = new ObjectOutputStream[3];
        ObjectInputStream[] ois = new ObjectInputStream[3];

        for(int j=0; j<3; j++){
            Socket[] cliSocket = new Socket[3];

            cliSocket[j] = new Socket(addresses[j], ports[j]);
            System.out.println("Connected to " + cliSocket[j].getInetAddress().getHostAddress() + ":" + cliSocket[j].getPort());

            oos[j] = new ObjectOutputStream(cliSocket[j].getOutputStream());
            ois[j] = new ObjectInputStream(cliSocket[j].getInputStream());

            Data data = new Data(3,100000, j+1);
            oos[j].writeObject(data);
        }

        double[] pi = new double[3];

        for(int k=0; k<3; k++){

            pi[k] = (double) ois[k].readObject();
            System.out.println(pi[k]);
        }

        System.out.println("PI (3 workers) = " + pi[0] + " + "
                + pi[1] + " + " + pi[2] + " = " + (pi[0] + pi[1] + pi[2]));


    }
}
