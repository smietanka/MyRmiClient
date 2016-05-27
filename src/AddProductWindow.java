import java.awt.GridLayout;
import java.awt.event.*;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class AddProductWindow extends JFrame {
	
	JTextField productNameInput = new JTextField();
	JTextField productPriceInput = new JTextField();
	JTextField productBrandInput = new JTextField();
	JTextField productInStockInput = new JTextField();
	
	public AddProductWindow(final Client myClient)
	{
		super("Dodaj produkt");
		
		this.setSize(200, 300);
		
		setLocationRelativeTo(null);
		setLayout(new GridLayout(6,2));
		
		JLabel productNameLabel = new JLabel("Nazwa produktu: "); 
		add(productNameLabel);
		add(productNameInput);
		
		JLabel productPriceLabel = new JLabel("Cena produktu: ");
		add(productPriceLabel);
		add(productPriceInput);
		
		JLabel productBrandLabel = new JLabel("Producent: ");
		add(productBrandLabel);
		add(productBrandInput);
		
		JLabel productInStockLabel = new JLabel("Sztuk w magazynie: ");
		add(productInStockLabel);
		add(productInStockInput);
						
		JButton buttonSave = new JButton("Dodaj");
		buttonSave.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	if(!productNameInput.getText().isEmpty() || !productPriceInput.getText().isEmpty() || !productBrandInput.getText().isEmpty() || !productInStockInput.getText().isEmpty())
		    	{
		    		myClient.addProductToServ(productNameInput.getText(), productPriceInput.getText(), productBrandInput.getText(), Integer.parseInt(productInStockInput.getText()));
		    		setVisible(false);
			        dispose();
		    	}
		    }
		});
		add(buttonSave);
		pack();
		setVisible(true);
		
	}
}
