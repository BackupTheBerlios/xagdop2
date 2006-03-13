package xagdop.Interface.Configuration;




import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import xagdop.Interface.XAGDOP;
import xagdop.ressources.Bundle;


public class IAbout extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4083774665179672609L;
	private static IAbout IA = null;
	JButton closeButton = new JButton(Bundle.getText("iabout.button.close"));
	JPanel logoContainer=new JPanel();
	JLabel logo = new JLabel(new ImageIcon(XAGDOP.class.getResource("/xagdop/ressources/Icon/LogoXAGDOP.jpg"))) ;
	JButton but = new JButton();
	
	private IAbout(){
		init();
	}
	
	
	private void init(){
		setTitle(Bundle.getText("iabout.title"));
		setSize(536, 500);
		this.setLocation(200, 200);
		
		closeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		//logo.setPreferredSize(new Dimension(200 , 134)) ;
		logoContainer.add(logo);
		logoContainer.add(closeButton);
		this.getContentPane().add(logoContainer) ;
		
	}
	/**
	 * @return Returns the singleton.
	 */
	public static IAbout getIA() {
		if (IA==null){
			IA = new IAbout(); 
		}
		
		return IA;
	}
	
	
}