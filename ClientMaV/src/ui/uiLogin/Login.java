package ui.uiLogin;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import src.util.UtilORB;
import ui.uiVote.EffectuerVote;
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
	private JFormattedTextField insee = null;
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
        this.setSize(new Dimension(362, 229));
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
	private JFormattedTextField getInsee() {
		if (insee == null) {
			NumberFormat nf = NumberFormat.getIntegerInstance();
			NumberFormatter intFormatter = new NumberFormatter(nf);
			nf.setParseIntegerOnly(true);
			nf.setGroupingUsed(false);
			intFormatter.setFormat(nf);
			insee = new JFormattedTextField();
			insee.setFormatterFactory(new DefaultFormatterFactory(intFormatter));
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
					int i = 0;
					int c = 0;
					
					if(!getInsee().getText().equals(""))
						i = (new Integer(getInsee().getText())).intValue();
					
					String sc = new String(getCode().getPassword());
					
					if(!sc.equals("") && sc.matches("[0-9]+"))
						c = new Integer(sc).intValue();
					
					System.out.println("Numero Insee : " + i); 
					System.out.println("Code : " + c); 
					try {
						Votant v = UtilORB.getVotant();
						
						boolean el = v.exists(i, c);
						
						if(!el){
							JOptionPane.showMessageDialog(null, "Electeur inconnu! Vérifier le numéro INSEE et le code indiqué.", "Electeur inconnu.", 1);
						}
						else{
							System.out.println("OK");
							setVisible(false);
							new EffectuerVote(i,c);
						}
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
