package ClientPckg;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;

public class ClientGui{

	private JFrame frame;
	private JTextField ipAdress;
	private JTextField portNr;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGui window = new ClientGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 722, 650);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblIpAddress = new JLabel("Ip Address");
		lblIpAddress.setBounds(10, 22, 75, 14);
		frame.getContentPane().add(lblIpAddress);
		
		ipAdress = new JTextField();
		ipAdress.setText("192.168.20.249");
		ipAdress.setBounds(105, 19, 104, 20);
		frame.getContentPane().add(ipAdress);
		ipAdress.setColumns(10);
		
		
		
		
		JLabel lblPort = new JLabel("Port");
		lblPort.setBounds(10, 40, 64, 14);
		frame.getContentPane().add(lblPort);
		
		portNr = new JTextField();
		portNr.setText("5005");
		portNr.setBounds(105, 38, 104, 20);
		frame.getContentPane().add(portNr);
		portNr.setColumns(10);
		
		
		
		final JComboBox res = new JComboBox();
		res.setModel(new DefaultComboBoxModel(new String[] {"160x90", "320x240", "480x270", "640x480"}));
		res.setBounds(104, 58, 104, 20);
		frame.getContentPane().add(res);
		
		final JComboBox fps = new JComboBox();
		fps.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16"}));
		fps.setBounds(105, 80, 104, 20);
		frame.getContentPane().add(fps);
		
		JLabel lblResolution = new JLabel("Resolution");
		lblResolution.setBounds(10, 64, 75, 14);
		frame.getContentPane().add(lblResolution);
		
		JLabel lblFps = new JLabel("Delay (1 .. 16)");
		lblFps.setBounds(10, 83, 75, 14);
		frame.getContentPane().add(lblFps);
		
			
		JLabel imageFrame = new JLabel("");
		
		imageFrame.setBounds(5, 114, 692, 489);
		frame.getContentPane().add(imageFrame);
		
		
		
		JButton btnCapture = new JButton("Capture");
		btnCapture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int portNum = Integer.parseInt(portNr.getText());
				//int freq = Integer.parseInt(fps.getText());
				String serverAdd = ipAdress.getText().toString();
				int resn = res.getSelectedIndex();
				int freq= fps.getSelectedIndex(); 
				
				
				System.out.println(resn);
				System.out.println(freq);
				

				
				JLabel imageFrame = new JLabel("");
				
				imageFrame.setBounds(5, 114, 692, 489);
				frame.getContentPane().add(imageFrame);
				
				
				//call the client 
				try {
					Client client= new Client(); 
					client.Imagecapture(serverAdd, portNum,imageFrame, freq, resn);
					} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}
					}
			
		});
		btnCapture.setBounds(408, 36, 89, 23);
		frame.getContentPane().add(btnCapture);
		
		
		

	}
}
