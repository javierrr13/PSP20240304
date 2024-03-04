package dam.psp;


import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Base64;

public class peticion2 {
	private static final String Host = "localhost";
	private static final int PORT = 4001;
	public static void main ( String [] args) throws UnknownHostException, IOException, KeyStoreException, NoSuchAlgorithmException, CertificateException {
		 KeyStore ks = KeyStore.getInstance("pkcs12");
		char [] password = "practicas".toCharArray();
		ks.load(new FileInputStream("C:\\Users\\Tarde\\eclipse-workspace\\RiosecoRodriguezJavier\\res\\keystore.p12"), password);	
		
		Socket socket = new Socket(Host, PORT);
        
		String b64 = Base64.getEncoder().encodeToString(ks.getCertificate("psp").getEncoded());
        MessageDigest md = null;
        md = MessageDigest.getInstance("SHA-256");
        md.update(b64.getBytes());
        String b64hash = Base64.getEncoder().encodeToString(md.digest());
        
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		out.writeUTF("cert");
		out.writeUTF("psp");
		out.writeUTF(b64);
		socket.shutdownOutput();
        

   
        socket.close();

	}
}
