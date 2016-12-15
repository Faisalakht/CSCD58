package mySocket;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.io.*;


//Muhammad Faisal Akhtar - 998895771
//Sangdong Kim - 999636746

public class A1SenderFiles {

	static private Socket client;
	private final static int port=7777;
	
	public static void main (String args[]){
		try{
			client = new Socket("localhost", port);
			OutputStream toServer = client.getOutputStream();//Creates a new connection
			DataOutputStream out = new DataOutputStream(toServer);
			out.writeUTF("" + client.getLocalSocketAddress());
			sendTheseFiles(3);
			
			client.close();
			
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public static void sendTheseFiles(int k){
		
		String filename;
		String line = null;
		DataOutputStream out;
		try{
			out = new DataOutputStream(client.getOutputStream()); //For each integer you make a new OutputStream
			out.writeInt(k);
		}catch (IOException e){
			e.printStackTrace();
		}


		
		for (int i = 0; i < k; i++){
			filename = "this"+Integer.toString(i)+".txt";//Filename from the current directory
			File sendfile = new File(filename); // Opens the file
			byte data[] = new byte[(int)sendfile.length()];
			try{
				
				FileInputStream fis = new FileInputStream(filename);
				out = new DataOutputStream(client.getOutputStream());
				int count;
				out.writeLong(sendfile.length());
				out.writeUTF(filename);
				while ((count = fis.read(data)) != -1){ //The file is not empty
					out.write(data,0,count);
					out.flush(); //Clears the output stream
				}
				fis.close();
			} catch (IOException e){
				e.printStackTrace();
			}
			
			
		}
		
	}	
	
	
}
