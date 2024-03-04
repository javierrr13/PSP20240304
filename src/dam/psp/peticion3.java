package dam.psp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;


public class peticion3 {
	private static final String Host = "localhost";
	private static final int PORT = 4001;
	public static void main ( String [] args) throws UnknownHostException, IOException, KeyStoreException, NoSuchAlgorithmException, CertificateException {
		Socket socket = new Socket(Host, PORT);
        
        File file = new File("C:\\Users\\Tarde\\eclipse-workspace\\RiosecoRodriguezJavier\\res\\Cifrar.txt");
        byte[] buffer = new byte[4096];
        
        KeyStore ks = KeyStore.getInstance("pkcs12");
		char [] password = "practicas".toCharArray();
		ks.load(new FileInputStream("C:\\Users\\Tarde\\eclipse-workspace\\RiosecoRodriguezJavier\\res\\keystore.p12"), password);
	
		PublicKey pubKey = (PublicKey) ks.getCertificate("julio").getPublicKey();

        
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        ObjectOutputStream obs = new ObjectOutputStream(dos);
        
        
        dos.writeUTF(file.getName());
        dos.writeLong(file.length());
        dos.writeUTF(file.getPath());
        obs.writeObject(pubKey);
       
        
        while (bis.read(buffer) > 0) {
            dos.write(buffer);
        }

        bis.close();
        dos.close();
        socket.close();

	}
}
