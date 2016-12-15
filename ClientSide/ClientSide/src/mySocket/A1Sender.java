package mySocket;

//import java.io.BufferedInputStream;
//import java.io.BufferedOutputStream;
//import java.io.DataInputStream;
import java.io.DataOutputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
import java.io.IOException;
//import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.io.FileReader;
import java.io.BufferedReader;

//Muhammad Faisal Akhtar - 998895771
//Sangdong Kim - 999636746

public class A1Sender {
	
	static private Socket client;
	private final static int port=7777;
	
	public static void main (String args[]){
		try{
			client = new Socket("localhost", port); 
			OutputStream toServer = client.getOutputStream();
			DataOutputStream out = new DataOutputStream(toServer); //Creates a new connection
			out.writeUTF("" + client.getLocalSocketAddress());
			sendFiles(3);
			
			client.close();
			
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public static void sendFiles(int k){
		
		String filename;
		String line = null;
		for (int i = 0; i < k; i++){
			filename = "this"+Integer.toString(i)+".txt";
			try{
				FileReader filereader = new FileReader(filename); //Gets the file from the current directory
				BufferedReader bfreader = new BufferedReader(filereader); //Reads each lines
				DataOutputStream out = new DataOutputStream(client.getOutputStream());
				
				while ((line = bfreader.readLine()) != null ){
					out.writeBytes(line+"\r\n"); //Send each line from the file to the OutputStream
				}
				
				bfreader.close();
			} catch (IOException e){
				e.printStackTrace();
			}
			
		}
		
	}

}
