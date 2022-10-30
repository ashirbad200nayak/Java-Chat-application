import java.io.BufferedReader;
import java.io.*;
import java.net.*;
public class Server {
    ServerSocket server;
    Socket socket;
    BufferedReader br;
    BufferedReader br2;
    PrintWriter out;

    //Constructor
    Server() {
        try{
            server= new ServerSocket(7777);
            System.out.println("Server is ready to accept connection");
            System.out.println("Waiting...");
            socket =server.accept();

            br= new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out= new PrintWriter(socket.getOutputStream());

            startReading();
            startWritting();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void startWritting() {
        //Thread  data read karke deta rahega
        Runnable r1= ()->{

            System.out.println("Writer started....");
            try{
            while ( !socket.isClosed()){
                String msg= br.readLine();
                if(msg.equals("exit")){
                    System.out.println("Client Terminated the chat...");
                break;
                }
                    System.out.println("Client :" + msg);
            }
                } catch (Exception e){
                    //e.printStackTrace();
                    System.out.println("Connection closed...");
            }
        };
        new Thread(r1).start();
    }

    private void startReading() {
        //Thread user se data read karke send karega client kosendkarega

        Runnable r2= ()->{

            System.out.println("Reader started....");
            try{
            while (true){

                    br2= new BufferedReader(new InputStreamReader(System.in));
                    String content= br2.readLine();
                    out.println(content);
                    out.flush();
                    if(content.equals("exit")){
                        System.out.println("Server Terminated the chat...");
                        break;
                    } 
            }
            }
                catch (Exception e){
                    //e.printStackTrace();
                    System.out.println("Connection closed...");

            }
        };
        new Thread(r2).start();
    }

    public static void main(String args[]) {
        System.out.println("The is Server...going to  start Server...");
        new Server();
    }
}  



