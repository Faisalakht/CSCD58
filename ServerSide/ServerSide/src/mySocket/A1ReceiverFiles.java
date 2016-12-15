package mySocket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.io.*;

//Muhammad Faisal Akhtar - 998895771
//Sangdong Kim - 999636746

public class A1ReceiverFiles extends Thread {
	private Socket socket;

	public A1ReceiverFiles (Socket socket)  {
	   this.socket = socket; 
	}   

	@Override
	public void run() {
	    
	    DataInputStream din=null;
	    DataOutputStream dout=null;
	    String inMsg=null;
	    FileOutputStream fileOut = null;
	    byte data[]= new byte[65536];
	    long filesize=0, bytesRead=0;
	 
	    try {
	        din=new DataInputStream(new BufferedInputStream(socket.getInputStream()));//Setup the connection
	        dout=new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));        
	        inMsg=din.readUTF();  // hear INITIAL_GREETING. This should be "/<IP_address>:<port#>" of the sender.
	        dout.writeUTF("A1ReceiverFiles at your service: ");         
	    } catch (IOException ex) {
	        System.out.println("A1ReceiverFiles: error in connection setup "+ex);
	        try {
				socket.close();
			} catch (IOException e) { }
	        System.exit(1);
	    }
	    String filename;
	    int numFiles = 0;
	    System.out.println("A1ReceiverFiles: Set the connection successfully with "+inMsg);
	    
	    try{
	    	numFiles = din.readInt();
	    } catch (IOException e){
	    	e.printStackTrace();
	    }
	    for (int i = 0; i < numFiles; i ++){
	    	try{
	    		filesize = din.readLong();
	    		filename = din.readUTF();
	    		fileOut = new FileOutputStream("Test/"+filename);
	    		for (int x = 0; x < filesize; x ++){ //While file is not fully read
	    			fileOut.write(din.read());
	    			fileOut.flush();
	    		}

	    		
	    		try {dout.write(0);}  
	    		catch (IOException e) { 
	    			System.out.println("A1ReceiverFiles: End of stream");
	    			fileOut.close(); 
	    			break; 
	    		}	
	    	} catch (IOException e){
	    		e.printStackTrace();
	    	}

	    	
	    }

		System.out.println("A1ReceiverFiles: End of stream");


	    try { socket.close(); System.out.println("A1ReceiverFiles: socket closed.");} 
	    	catch (IOException ex) { System.out.println("A1ReceiverFiles: error while closing the socket."); }
	}  
}
