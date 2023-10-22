import java.io.*;
import java.net.*;
import java.util.concurrent.locks.ReentrantLock;

public class Server {
    public static void main(String[] args)  {

        try {
            // Create a server socket
            ServerSocket serverSocket = new ServerSocket(9991);
            System.out.println("Merhaba");
            while (true) {

                Socket clientSocket = serverSocket.accept();

                Thread serverThread = new Thread(new ClientHandler(clientSocket));
                serverThread.start();

                new ClientHandler(clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    ReentrantLock lock = new ReentrantLock();

    public void run() {
        try {
            // Get the input and output streams for the socket
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Communicate with the client
            while (true) {
                lock.lock();
                String request = in.readLine();
                System.out.println("Server, received this message from Socket: " + request);
                lock.unlock();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
