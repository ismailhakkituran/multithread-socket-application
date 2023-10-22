import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.List;

public class Client {
    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("localhost", 9991);
        System.out.println("Connected!");

        Thread senderThread = new Thread(new Sender(socket));
        senderThread.start();

        List<Thread> customerList = Arrays.asList(new Thread[50]);
        customerList.forEach(d -> new Thread(new Sender(socket)).start() );

        System.out.println("thread sayisi: "+ customerList.size() );

        Thread receiverThread = new Thread(new Sender(socket));
        receiverThread.start();
    }
}

class Sender implements Runnable {
    private Socket socket;

    public Sender(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        while(true) {
            for (int i = 0; i < 10000000; i++) { }
            try {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                for (int i = 0; i < 10; i++) {
                    out.println("Thread_id: " + Thread.currentThread().getId() + " Message " + Long.toString(Thread.currentThread().getId()).repeat(1000));
                    System.out.println("Message sended");
                    //Thread.sleep(1000);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
