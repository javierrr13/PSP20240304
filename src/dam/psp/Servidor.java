package dam.psp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ServerSocket ssc = new ServerSocket();
		ssc.setSoTimeout(5);
		if(ssc.getReceiveBufferSize()==0) {
			System.out.println("ERROR:Se esperaba una petición");
		}
	      while (true) {
	            try {
	                Socket clientSocket = ssc.accept();
	                new ServidorHilo(clientSocket).start();
	              
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	}

}
