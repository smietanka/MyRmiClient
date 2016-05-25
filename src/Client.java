import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import java.awt.FlowLayout;
import javax.swing.border.TitledBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.BoxLayout;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.Box;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import java.awt.GridLayout;
import java.awt.Font;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JInternalFrame;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;

public class Client {

	private JFrame myWindow;
	private JTextArea myTextArea;
	private IMyServer myServer;
	private static Client myClient;
	public static boolean connected;
	private JTextField getStringVar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					myClient = new Client();
					myClient.myWindow.setVisible(true);
				} catch (Exception e) {
					myClient.WriteLog(e.getMessage());
					e.printStackTrace();
				}
			}
		});
	}
	
	public void doConnect()
	{
		WriteLog("£¹czymy z serwerem...");
		try
		{
			myServer = (IMyServer)Naming.lookup("rmi://127.0.0.1/myabc");
			WriteLog("Po³¹czono.");
			connected = true;
			myServer.registerClient("Kacper");
		}
		catch(Exception e)
		{
			WriteLog(e.getMessage());
			connected = false;
		}
	}
	public void WriteLog(String message)
	{
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");		
		String myString = "["+ sdf.format(cal.getTime()) +"] - " + message + "\n"; 
		System.out.print(myString);
		myTextArea.append(myString);
	}

	/**
	 * Create the application.
	 */
	public Client() {
		connected = false;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		myWindow = new JFrame();
		myWindow.setTitle("Moj klient");
		myWindow.setResizable(false);
		myWindow.setBounds(100, 100, 701, 411);
		myWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel sendMethodContainer = new JPanel();
		myWindow.getContentPane().add(sendMethodContainer, BorderLayout.CENTER);
		GridBagLayout gbl_sendMethodContainer = new GridBagLayout();
		gbl_sendMethodContainer.columnWidths = new int[]{150, 394, 61, 0};
		gbl_sendMethodContainer.rowHeights = new int[]{33, 0, 0, 0};
		gbl_sendMethodContainer.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_sendMethodContainer.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		sendMethodContainer.setLayout(gbl_sendMethodContainer);
		
		JLabel lblWykonajGethellowworld = new JLabel("Wykonaj getHelloWorld");
		GridBagConstraints gbc_lblWykonajGethellowworld = new GridBagConstraints();
		gbc_lblWykonajGethellowworld.fill = GridBagConstraints.VERTICAL;
		gbc_lblWykonajGethellowworld.insets = new Insets(0, 0, 5, 5);
		gbc_lblWykonajGethellowworld.gridx = 0;
		gbc_lblWykonajGethellowworld.gridy = 0;
		sendMethodContainer.add(lblWykonajGethellowworld, gbc_lblWykonajGethellowworld);
		
		JButton getHwButton = new JButton("Wyslij");
		getHwButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(connected)
				{
					try
					{
						WriteLog(myServer.getHelloWorld());
					}
					catch (Exception e)
					{
						myClient.WriteLog(e.getMessage());
					}	
				}
				else
				{
					myClient.WriteLog("Nie jestes polaczony z serwerem.");
				}
			}
		});
		GridBagConstraints gbc_getHwButton = new GridBagConstraints();
		gbc_getHwButton.insets = new Insets(0, 0, 5, 5);
		gbc_getHwButton.gridx = 1;
		gbc_getHwButton.gridy = 0;
		sendMethodContainer.add(getHwButton, gbc_getHwButton);
		
		JLabel lblNewLabel = new JLabel("Wykonaj getString");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		sendMethodContainer.add(lblNewLabel, gbc_lblNewLabel);
		
		getStringVar = new JTextField();
		GridBagConstraints gbc_getStringVar = new GridBagConstraints();
		gbc_getStringVar.insets = new Insets(0, 0, 5, 5);
		gbc_getStringVar.fill = GridBagConstraints.HORIZONTAL;
		gbc_getStringVar.gridx = 1;
		gbc_getStringVar.gridy = 1;
		sendMethodContainer.add(getStringVar, gbc_getStringVar);
		getStringVar.setColumns(10);
		
		JButton getStringButton = new JButton("Wyslij");
		getStringButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(connected)
				{
					try
					{
						WriteLog(myServer.getString(getStringVar.getText()));
					}
					catch (Exception e2)
					{
						myClient.WriteLog(e2.getMessage());
					}	
				}
				else
				{
					myClient.WriteLog("Nie jestes polaczony z serwerem.");
				}	
			}
		});
		GridBagConstraints gbc_getStringButton = new GridBagConstraints();
		gbc_getStringButton.insets = new Insets(0, 0, 5, 0);
		gbc_getStringButton.gridx = 2;
		gbc_getStringButton.gridy = 1;
		sendMethodContainer.add(getStringButton, gbc_getStringButton);
		
		JPanel doConnectContainer = new JPanel();
		myWindow.getContentPane().add(doConnectContainer, BorderLayout.NORTH);
		GridBagLayout gbl_doConnectContainer = new GridBagLayout();
		gbl_doConnectContainer.columnWidths = new int[]{287, 121, 0};
		gbl_doConnectContainer.rowHeights = new int[]{23, 0};
		gbl_doConnectContainer.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_doConnectContainer.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		doConnectContainer.setLayout(gbl_doConnectContainer);
		
		JButton doConnectButton = new JButton("Po\u0142\u0105cz z serwerem");
		doConnectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				doConnect();
				CheckConnection myChecker = new CheckConnection(myClient);
				Thread myCheckConnectThread = new Thread(myChecker);
				myCheckConnectThread.start();
			}
		});
		GridBagConstraints gbc_doConnectButton = new GridBagConstraints();
		gbc_doConnectButton.insets = new Insets(0, 0, 0, 5);
		gbc_doConnectButton.anchor = GridBagConstraints.NORTH;
		gbc_doConnectButton.gridx = 0;
		gbc_doConnectButton.gridy = 0;
		doConnectContainer.add(doConnectButton, gbc_doConnectButton);
		
		myTextArea = new JTextArea(8,0);
		myTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
		
		JScrollPane scrollPane = new JScrollPane(myTextArea);
		
		myWindow.getContentPane().add(scrollPane, BorderLayout.SOUTH);
	}
}
