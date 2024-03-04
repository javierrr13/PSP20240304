package dam.psp;

import java.io.*;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class ServidorHilo extends Thread {
    private Socket clientSocket;

    public ServidorHilo(Socket socket) {
        this.clientSocket = socket;
        System.out.println("Cliente conectado en hilo separado.");
    }

    public void run() {
        try {
            DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(dis);

            String fileName = dis.readUTF();
            long fileSize = dis.readLong();
            String path = dis.readUTF();
            Key pubkey = (Key) ois.readObject();
           
            
            FileOutputStream fos = new FileOutputStream(fileName);
            byte[] buffer = new byte[4096];

            int read;
            int totalRead = 0;
            int remaining = (int) fileSize;
            while((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
                totalRead += read;
                remaining -= read;
                fos.write(buffer, 0, read);
            }

            fos.close();

            cifrar(path, pubkey);
            System.out.println("[*]Archivo cifrado Correctamente");
           
           
     
            dos.close();
            ois.close();
            dis.close();
            
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static public void cifrar(String path, Key key) {
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(path));
                 BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(path + ".cifrado")))) {
                byte[] bloque = new byte[256];
                int n;
                while ((n = in.read(bloque)) != -1)
                	System.out.println("OK:bloque_cifrado_codificado_en_base64");
                    out.write(cipher.doFinal(bloque, 0, n));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

