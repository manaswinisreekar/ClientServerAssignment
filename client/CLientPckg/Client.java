package CLientPckg;


import java.io.*;
import sun.misc.*;
import java.io.File;
import java.net.Socket;
import java.sql.Date;
import java.io.IOException;
import java.awt.*;  
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO; 
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.io.StringWriter;
import static java.lang.System.in;
import java.util.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
 
 
    /**
     * Trivial client for the date server.
     */
public class Client {

        /**
         * Runs the client as an application. Enter the IP address, port n.o, Resolution and
         * frequency in the GUI then click capture.
         */
			Socket s;
			//String key = "0123456789abcdef";
			
	
        public void Imagecapture(String serverAddress, int serverPort, JLabel jLabel, int fps, int n) throws IOException {
 
   	
			System.out.println("Connecting to " + serverAddress + " on port " + serverPort);
	
			s = new Socket(serverAddress, serverPort);
	
			System.out.println("Just connected to "+ s.getRemoteSocketAddress());
	
			//Sending data parameters to server in form of bytes
		
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
		
			out.write(serverAddress.getBytes());
			out.write(String.valueOf(n).getBytes());
	        out.write(String.valueOf(fps).getBytes());
	        
	        
	        System.out.println(n);
	        System.out.println(fps);
	       
	       //initiate new thread
           Thread t = new Thread(new MyThread(jLabel,s));
           t.start();       
	      
	    } 
        
        //function to close the socket
		public void Closesocketnow(Socket s) throws IOException {
			s.close();
			System.out.println("now really closing");
	   		System.exit(0);
			
		}
			
			}

		//start the thread
        
        class MyThread implements Runnable {
        		JLabel jLabel ;
        		Socket s ;
        		//int encrypt;
        	public  MyThread(JLabel jLabel, Socket s ) {

        	this.jLabel = jLabel;
        	this.s= s;
        	
			}
			@Override
			public void run() {
				
				
					//BufferedImage img = null;
					int i=0;
					while (true){
						if (s.isConnected()){						
							try {
								
								//System.out.println(encrypt);
								//read the size what we send first 
								BufferedInputStream in= new BufferedInputStream(new DataInputStream(s.getInputStream()));
								StringBuilder sb = new StringBuilder();
								char test;
								int  size=0;
								for (int i1=0; i1<=5; i1++){
									
									test = (char) in.read();
									sb.append(test);
								    System.out.println("char: "+test);
								
									
								}
								
								
								String something = (sb.toString());
								System.out.println("number "+something);
								size = Integer.parseInt((something).trim());
								System.out.println("size"+size);
								//DataOutputStream out1= new DataOutputStream(s.getOutputStream());
								//out1.write(String.valueOf(encrypt).getBytes());
								//// read from the client yes or no if he want to encrypt ..
								//read the image byte in a loop 
								// save the image in abuffer to convert it to icon then we can show it . 
								
								
								byte[] dataByte= new byte[size];
								
								//BufferedInputStream inS= new ByteArrayInputStream(s.getInputStream()); 
								in.read(dataByte);
										
								Image imgByte=Toolkit.getDefaultToolkit().createImage(dataByte); 
								//ImageIcon image = new ImageIcon(imgByte);
								
								if (imgByte == null){
									
									   continue;
								    			}
									
								
								ImageIcon image = new ImageIcon(imgByte);
									jLabel.setIcon(image);
									jLabel.validate();	
									jLabel.setText(String.valueOf(i));
								  
									 System.out.println(i);
								
								//img = ImageIO.read(s.getInputStream());
							} catch (IOException e) {

								e.printStackTrace();
							}
									}
						
					  
						 
			 //Saving images into files inside the client
				/*File imageOut = new File((i)+"imageOut.jpg");
				try {
					ImageIO.write(img, "jpeg" , imageOut);
				} catch (IOException e) {

					e.printStackTrace();
				}*/
				i++;
					  
	
					
			}
        	
                       }

      
	
 
			}//end of Client
     
    

