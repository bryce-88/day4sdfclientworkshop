package sdf;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     * @throws IOException
     * @throws UnknownHostException
     */
    public static void main(String[] args) throws UnknownHostException, IOException {
        

        Socket socket = new Socket("localhost", 3000);
        System.out.println("Connected to server on localhost: 3000");

        try (OutputStream os = socket.getOutputStream()) {
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);
            
            Console cons = System.console();
            String readInput = "";
            String receivedMessaged = "";

            try(InputStream is = socket.getInputStream()) {
                BufferedInputStream bis = new BufferedInputStream(is);
                DataInputStream dis = new DataInputStream(bis);

                while (!readInput.equalsIgnoreCase("close")) {
                    readInput = cons.readLine("Enter a command\n");
                    dos.writeUTF(readInput);
                    dos.flush();

                    receivedMessaged = dis.readUTF();
                    System.out.println("Received Message from Server: " + receivedMessaged + "\n");
                }

                dis.close();
                bis.close();
                is.close();
            } catch (EOFException ex) {
                socket.close();
            }

        }
    }
}
