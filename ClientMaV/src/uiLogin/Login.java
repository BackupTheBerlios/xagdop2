package uiLogin;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import src.UtilORB;
import MaV.Votant;

public class Login extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JTextField insee = null;
	private JLabel jLabel3 = null;
	private JButton valider = null;
	private JPasswordField code = null;
	public Login()
	{
		initialize();
	}
	
	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(347, 238));
        this.setLocation(new Point(500, 250));
        this.setResizable(false);
        this.setMaximumSize(new Dimension(11, 30));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setName("uiLogin");
        this.setContentPane(getJPanel());
        this.setTitle("Identification");
        this.setVisible(true);
			
	}

	
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(85, 119, 50, 24));
			jLabel3.setText("Code : ");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(30, 89, 105, 24));
			jLabel2.setText("Numéro INSEE :");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(84, 44, 169, 19));
			jLabel1.setText("Veuillez vous identifier : ");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(125, 13, 85, 23));
			jLabel.setText("Bienvenue!");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
			jPanel.add(getInsee(), null);
			jPanel.add(jLabel3, null);
			jPanel.add(getValider(), null);
			jPanel.add(getCode(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getInsee() {
		if (insee == null) {
			insee = new JTextField();
			insee.setBounds(new Rectangle(148, 89, 169, 24));
			insee.setName("insee");
		}
		return insee;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getValider() {
		if (valider == null) {
			valider = new JButton();
			valider.setBounds(new Rectangle(129, 165, 96, 31));
			valider.setName("valider");
			valider.setText("Valider");
			valider.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
				
					int i = (new Integer(getInsee().getText())).intValue();
					int c = (new Integer(new String(getCode().getPassword())).intValue());
					System.out.println("Numero Insee : " + i); 
					System.out.println("Code : " + c); 
					try {
						Votant v = UtilORB.getVotant();
						boolean el = v.exists(i, c);
						if(!el){
							JOptionPane.showMessageDialog(null, "Electeur inconnu! Vérifier le numéro INSEE et le code indiqué.", "Electeur inconnu.", 1);
						}
						else
							System.out.println("OK");
						
					} catch (NotFound e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (CannotProceed e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InvalidName e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
		return valider;
	}

	/**
	 * This method initializes code	
	 * 	
	 * @return javax.swing.JPasswordField	
	 */
	private JPasswordField getCode() {
		if (code == null) {
			code = new JPasswordField();
			code.setBounds(new Rectangle(148, 119, 169, 24));
			code.setName("code");
		}
		return code;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
