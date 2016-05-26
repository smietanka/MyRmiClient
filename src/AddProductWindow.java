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
	
	public AddProductWindow(final Client myClient)
	{
		super("Dodaj produkt");
		setLocationRelativeTo(null);
		setLayout(new GridLayout(6,2));
		
		JLabel productNameLabel = new JLabel("Nazwa produktu: "); 
		add(productNameLabel);
		add(productNameInput);
		
		JLabel productPriceLabel = new JLabel("Cena produktu: ");
		add(productPriceLabel);
		add(productPriceInput);
						
		JButton buttonSave = new JButton("Dodaj");
		buttonSave.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	if(!productNameInput.getText().isEmpty() || !productPriceInput.getText().isEmpty())
		    	{
		    		myClient.addProductToServ(productNameInput.getText(), productPriceInput.getText());
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
