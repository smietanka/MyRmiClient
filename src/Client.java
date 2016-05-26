import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import java.awt.FlowLayout;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
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
import javax.swing.JDesktopPane;
import javax.swing.JTable;
import javax.swing.ImageIcon;
import javax.swing.JSplitPane;

public class Client {

	// g³ówne
	private JFrame myWindow;
	private IMyServer myServer;
	private static Client myClient;
	public static boolean connected;
	private Customer myCustomer;
	
	// buttony
	private JButton addProductButton;
	private JButton delSelectedProductsButton;
	private JButton delAllProductsButton;
	private JButton connectButton;
	
	// text fieldy
	private JTextField searchField;
	private JTextField clientNameField;
	private static JTextArea myTextArea;
	
	// tabelka
	private JTable table;
	private DefaultTableModel tableModel;
	
	

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
					Client.WriteLog(e.getMessage());
					e.printStackTrace();
				}
			}
		});
	}
	
	public void hideAllAdminButtons()
	{
		addProductButton.setVisible(false);
		delSelectedProductsButton.setVisible(false);
		delAllProductsButton.setVisible(false);
	}
	
	public void showAllAdminButtons()
	{
		addProductButton.setVisible(true);
		delSelectedProductsButton.setVisible(true);
		delAllProductsButton.setVisible(true);
	}
	
	public void addProductToServ(String name, String price)
	{
		Product myNewProduct = new Product(name, price);
		try {
			myServer.addProduct(myNewProduct);
			Client.WriteLog("Dodano produkt.");
		} catch (RemoteException e) {
			Client.WriteLog(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public void doConnect()
	{
		WriteLog("£¹czymy z serwerem...");
		try
		{
			if(!clientNameField.getText().isEmpty())
			{
				myServer = (IMyServer)Naming.lookup("rmi://127.0.0.1/myabc");
				if(!myServer.isClientExists(clientNameField.getText()))
				{
					myCustomer = new Customer(clientNameField.getText());
					if(myServer.registerClient(myCustomer))
					{
						showAllAdminButtons();
					}
					else
					{
						hideAllAdminButtons();
					}
					
					WriteLog("Po³¹czono.");
					connected = true;
					
					connectButton.setText("Roz³¹cz");
				}
				else
				{
					WriteLog("Taki u¿ytkownik ju¿ istnieje.");
				}
			}
			else
			{
				WriteLog("Nie wpisales nazwy uzytkownika");
			}
		}
		catch(Exception e)
		{
			WriteLog(e.getMessage());
			connected = false;
		}
	}
	
	public static void WriteLog(String message)
	{
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");		
		String myString = "["+ sdf.format(cal.getTime()) +"] - " + message + "\n"; 
		System.out.print(myString);
		myTextArea.append(myString);
	}
	
	public void changeConnectButtonText(String text)
	{
		connectButton.setText(text);
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
		myWindow.setBounds(100, 100, 801, 431);
		myWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel sendMethodContainer = new JPanel();
		myWindow.getContentPane().add(sendMethodContainer, BorderLayout.CENTER);
		GridBagLayout gbl_sendMethodContainer = new GridBagLayout();
		gbl_sendMethodContainer.columnWidths = new int[]{112, 394, 61, 0};
		gbl_sendMethodContainer.rowHeights = new int[]{0, 32, 44, 29, 0, 0, 0, 0};
		gbl_sendMethodContainer.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_sendMethodContainer.rowWeights = new double[]{0.0, 1.0, 0.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		sendMethodContainer.setLayout(gbl_sendMethodContainer);
		
		addProductButton = new JButton("Dodaj produkt");
		addProductButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(connected)
				{
					AddProductWindow setWindow = new AddProductWindow(myClient);
					setWindow.show();				
				}
				else
				{
					Client.WriteLog("Nie jestes polaczony z serwerem.");
				}
			}
		});
		
		JLabel lblNewLabel = new JLabel("Tw\u00F3j login");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		sendMethodContainer.add(lblNewLabel, gbc_lblNewLabel);
		
		clientNameField = new JTextField();
		GridBagConstraints gbc_clientNameField = new GridBagConstraints();
		gbc_clientNameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_clientNameField.insets = new Insets(0, 0, 5, 5);
		gbc_clientNameField.gridx = 1;
		gbc_clientNameField.gridy = 0;
		sendMethodContainer.add(clientNameField, gbc_clientNameField);
		clientNameField.setColumns(10);
		
		connectButton = new JButton("Po\u0142\u0105cz");
		connectButton.setBackground(Color.ORANGE);
		GridBagConstraints gbc_connectButton = new GridBagConstraints();
		gbc_connectButton.fill = GridBagConstraints.BOTH;
		gbc_connectButton.insets = new Insets(0, 0, 5, 5);
		gbc_connectButton.gridx = 0;
		gbc_connectButton.gridy = 1;
		sendMethodContainer.add(connectButton, gbc_connectButton);
		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!connected)
				{
					doConnect();
					CheckConnection myChecker = new CheckConnection(myClient);
					Thread myCheckConnectThread = new Thread(myChecker);
					myCheckConnectThread.start();	
				}
				else
				{
					try {
						myServer.logoutClient(myCustomer);
						connected = false;
						connectButton.setText("Po³¹cz");
						Client.WriteLog("Wylogowa³eœ siê.");
						hideAllAdminButtons();
						clientNameField.setText("");
						myTextArea.removeAll();
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
				
			}
		});
		
		JSplitPane splitPane = new JSplitPane();
		GridBagConstraints gbc_splitPane = new GridBagConstraints();
		gbc_splitPane.insets = new Insets(0, 0, 5, 5);
		gbc_splitPane.fill = GridBagConstraints.BOTH;
		gbc_splitPane.gridx = 1;
		gbc_splitPane.gridy = 1;
		sendMethodContainer.add(splitPane, gbc_splitPane);
		
		JButton searchButton = new JButton("Szukaj");
		splitPane.setLeftComponent(searchButton);
		
		searchField = new JTextField();
		searchField.setToolTipText("Mo\u017Cna szuka\u0107 po nazwie produktu lub po cenie.");
		splitPane.setRightComponent(searchField);
		searchField.setColumns(10);
		
		JButton showAllProductButton = new JButton("Pokaz wszystkie produkty");
		showAllProductButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(connected)
				{
					try {
						List<Product> myProducts = myServer.showAllProducts();
						if(!myProducts.isEmpty())
						{
							for(int j = tableModel.getRowCount() - 1; j >= 0 ; j--)
							{
								tableModel.removeRow(j);	
							}
							Client.WriteLog("Pobrano wszystkie produkty.");
							int i = 0 ;
							for(Product eachProduct : myProducts)
							{
								tableModel.addRow(new Object[] {eachProduct.getId(), eachProduct.getName(), eachProduct.getPrice()});
								Client.WriteLog("Produkt[" + i + "]" + eachProduct.getName() + ":" + eachProduct.getPrice());
								i++;
							}	
						}
						else
						{
							Client.WriteLog("Nie mamy zadnego produktu.");
						}
						
					} catch (RemoteException e1) {
						Client.WriteLog(e1.getMessage());
						e1.printStackTrace();
					}
				}
				else
				{
					Client.WriteLog("Nie jestes polaczony z serwerem.");
				}
			}
		});
		GridBagConstraints gbc_showAllProductButton = new GridBagConstraints();
		gbc_showAllProductButton.fill = GridBagConstraints.BOTH;
		gbc_showAllProductButton.insets = new Insets(0, 0, 5, 0);
		gbc_showAllProductButton.gridx = 2;
		gbc_showAllProductButton.gridy = 1;
		sendMethodContainer.add(showAllProductButton, gbc_showAllProductButton);
		GridBagConstraints gbc_addProductButton = new GridBagConstraints();
		gbc_addProductButton.fill = GridBagConstraints.BOTH;
		gbc_addProductButton.insets = new Insets(0, 0, 5, 5);
		gbc_addProductButton.gridx = 0;
		gbc_addProductButton.gridy = 2;
		sendMethodContainer.add(addProductButton, gbc_addProductButton);
		tableModel = new DefaultTableModel(0,0);
		String header[] = new String[] {"Id", "ProductName", "Price"};
		tableModel.setColumnIdentifiers(header);
		
		delSelectedProductsButton = new JButton("Usun zaznaczone produkty");
		delSelectedProductsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int[] rows = table.getSelectedRows();
				for(int i=0;i<rows.length;i++){
				     tableModel.removeRow(rows[i]-i);
				   }
			}
		});
		
		JButton addProductToBucket = new JButton("Dodaj produkt do koszyka");
		GridBagConstraints gbc_addProductToBucket = new GridBagConstraints();
		gbc_addProductToBucket.fill = GridBagConstraints.BOTH;
		gbc_addProductToBucket.insets = new Insets(0, 0, 5, 0);
		gbc_addProductToBucket.gridx = 2;
		gbc_addProductToBucket.gridy = 2;
		sendMethodContainer.add(addProductToBucket, gbc_addProductToBucket);
		GridBagConstraints gbc_delSelectedProductsButton = new GridBagConstraints();
		gbc_delSelectedProductsButton.fill = GridBagConstraints.BOTH;
		gbc_delSelectedProductsButton.insets = new Insets(0, 0, 5, 5);
		gbc_delSelectedProductsButton.gridx = 0;
		gbc_delSelectedProductsButton.gridy = 3;
		sendMethodContainer.add(delSelectedProductsButton, gbc_delSelectedProductsButton);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.gridheight = 5;
		gbc_scrollPane_1.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 1;
		gbc_scrollPane_1.gridy = 2;
		sendMethodContainer.add(scrollPane_1, gbc_scrollPane_1);
		
		table = new JTable();
		scrollPane_1.setViewportView(table);
		table.setModel(tableModel);
		
		JButton removeProductFromBucketButton = new JButton("Usu\u0144 produkt z koszyka");
		GridBagConstraints gbc_removeProductFromBucketButton = new GridBagConstraints();
		gbc_removeProductFromBucketButton.fill = GridBagConstraints.BOTH;
		gbc_removeProductFromBucketButton.insets = new Insets(0, 0, 5, 0);
		gbc_removeProductFromBucketButton.gridx = 2;
		gbc_removeProductFromBucketButton.gridy = 3;
		sendMethodContainer.add(removeProductFromBucketButton, gbc_removeProductFromBucketButton);
		
		delAllProductsButton = new JButton("Usu\u0144 wszystkie produkty");
		GridBagConstraints gbc_delAllProductsButton = new GridBagConstraints();
		gbc_delAllProductsButton.fill = GridBagConstraints.BOTH;
		gbc_delAllProductsButton.insets = new Insets(0, 0, 5, 5);
		gbc_delAllProductsButton.gridx = 0;
		gbc_delAllProductsButton.gridy = 4;
		sendMethodContainer.add(delAllProductsButton, gbc_delAllProductsButton);
		
		JSplitPane splitPane_1 = new JSplitPane();
		GridBagConstraints gbc_splitPane_1 = new GridBagConstraints();
		gbc_splitPane_1.insets = new Insets(0, 0, 5, 0);
		gbc_splitPane_1.fill = GridBagConstraints.BOTH;
		gbc_splitPane_1.gridx = 2;
		gbc_splitPane_1.gridy = 4;
		sendMethodContainer.add(splitPane_1, gbc_splitPane_1);
		
		JLabel lblIloWszystkichProduktow = new JLabel("Ilo\u015B\u0107 wszystkich produktow: ");
		splitPane_1.setLeftComponent(lblIloWszystkichProduktow);
		
		JLabel countAllProductLabel = new JLabel("0");
		splitPane_1.setRightComponent(countAllProductLabel);
		
		JSplitPane splitPane_2 = new JSplitPane();
		GridBagConstraints gbc_splitPane_2 = new GridBagConstraints();
		gbc_splitPane_2.insets = new Insets(0, 0, 5, 0);
		gbc_splitPane_2.fill = GridBagConstraints.BOTH;
		gbc_splitPane_2.gridx = 2;
		gbc_splitPane_2.gridy = 5;
		sendMethodContainer.add(splitPane_2, gbc_splitPane_2);
		
		JLabel lblCenaWszystkichProduktow = new JLabel("Cena wszystkich produktow: ");
		splitPane_2.setLeftComponent(lblCenaWszystkichProduktow);
		
		JLabel priceAllProductLabel = new JLabel("0 z\u0142");
		splitPane_2.setRightComponent(priceAllProductLabel);
		
		JButton buySelectedProducts = new JButton("Kup zaznaczone produkty");
		buySelectedProducts.setBackground(Color.GREEN);
		GridBagConstraints gbc_buySelectedProducts = new GridBagConstraints();
		gbc_buySelectedProducts.fill = GridBagConstraints.BOTH;
		gbc_buySelectedProducts.gridx = 2;
		gbc_buySelectedProducts.gridy = 6;
		sendMethodContainer.add(buySelectedProducts, gbc_buySelectedProducts);
		
		myTextArea = new JTextArea(8,0);
		myTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
		
		JScrollPane scrollPane = new JScrollPane(myTextArea);
		
		myWindow.getContentPane().add(scrollPane, BorderLayout.SOUTH);
		hideAllAdminButtons();
	}
}
