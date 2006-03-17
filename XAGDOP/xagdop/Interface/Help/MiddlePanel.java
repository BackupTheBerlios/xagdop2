package xagdop.Interface.Help;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import xagdop.Interface.XAGDOP;
import xagdop.Interface.Preferences.PanelDescription;
import xagdop.ressources.Bundle;

//import com.sun.java.util.jar.pack.Instruction.Switch;

public class MiddlePanel extends JPanel {

	private static final long serialVersionUID = 1L;
//	private static MiddlePanel mp= null;
	protected JPanel JMiddlePanel;
	private GridBagConstraints gridBagConstraints = new GridBagConstraints();
	
	public MiddlePanel(int value) {
		String title=null;
		ArrayList desc = new ArrayList();
		
		
		switch(value)
		{
			case 11: 
				title=Bundle.getText("ihelp.projects.create");
				desc.add(new String("ihelp.prerequis.addprojects"));
				desc.add(new String("ihelp.desc1.addprojects"));
				desc.add(new String("ihelp.desc1img.addprojects"));
				desc.add(new String("ihelp.desc2.addprojects"));
				desc.add(new String("ihelp.desc2img.addprojects"));
				desc.add(new String("ihelp.ok"));
				
				break;
				
			case 12:
				title=Bundle.getText("ihelp.projects.delete");
				desc.add(new String("ihelp.desc.deleteprojects"));
				desc.add(new String("ihelp.descimg.deleteprojects"));
				
				
				break;
			
			case 21:
				title=Bundle.getText("ihelp.file.add");
				desc.add(new String("ihelp.desc1.addfile"));
				desc.add(new String("ihelp.desc2.addfile"));
				desc.add(new String("ihelp.desc3.addfile"));
				
				break;
				
			case 22:
				title=Bundle.getText("ihelp.file.refresh");
				desc.add(new String("ihelp.desc1.refreshfile"));
				desc.add(new String("ihelp.desc2.refreshfile"));
				desc.add(new String("ihelp.desc3.refreshfile"));
				desc.add(new String("ihelp.desc4.refreshfile"));
				break;
				
			case 23:
				title=Bundle.getText("ihelp.file.get");
				desc.add(new String("ihelp.desc1.getfile"));
				desc.add(new String("ihelp.desc2.getfile"));
				desc.add(new String("ihelp.desc3.getfile"));
				desc.add(new String("ihelp.desc4.getfile"));
				break;
				
			case 24:
				title=Bundle.getText("ihelp.file.view");
				desc.add(new String("ihelp.desc1.viewfile"));
			
			case 31:
				title=Bundle.getText("ihelp.userinproject.add");
				desc.add(new String("ihelp.prerequis.adduserinproject"));
				desc.add(new String("ihelp.desc1.adduserinproject"));
				desc.add(new String("ihelp.desc1img.adduserinproject"));
				desc.add(new String("ihelp.desc2.adduserinproject"));
				desc.add(new String("ihelp.desc3.adduserinproject"));
				desc.add(new String("ihelp.desc2img.adduserinproject"));
				desc.add(new String("ihelp.ok"));
				break;
			
			case 32:
				title=Bundle.getText("ihelp.userinproject.modify");
				desc.add(new String("ihelp.prerequis.adduserinproject"));
				desc.add(new String("ihelp.desc1.adduserinproject"));
				desc.add(new String("ihelp.desc1img.adduserinproject"));
				desc.add(new String("ihelp.desc1.modifyuserinproject"));
				desc.add(new String("ihelp.desc1img.modifyuserinproject"));
				desc.add(new String("ihelp.ok.userinproject"));
				break;
				
			case 33:
				title=Bundle.getText("ihelp.userinproject.delete");
				desc.add(new String("ihelp.prerequis.adduserinproject"));
				desc.add(new String("ihelp.desc1.adduserinproject"));
				desc.add(new String("ihelp.desc1img.adduserinproject"));
				desc.add(new String("ihelp.desc1.deleteuserinproject"));
				desc.add(new String("ihelp.desc1img.modifyuserinproject"));
				desc.add(new String("ihelp.ok.userinproject"));
				break;
				
			case 41:
				title=Bundle.getText("ihelp.user.add");
				desc.add(new String("ihelp.prerequis.adduser"));
				desc.add(new String("ihelp.desc1.adduser"));
				desc.add(new String("ihelp.desc1img.adduser"));
				desc.add(new String("ihelp.desc2.adduser"));
				desc.add(new String("ihelp.desc3.adduser"));
				desc.add(new String("ihelp.desc2img.adduser"));
				desc.add(new String("ihelp.ok.userinproject"));
				break;
				
			case 42:
				title=Bundle.getText("ihelp.user.modify");
				desc.add(new String("ihelp.prerequis.adduser"));
				desc.add(new String("ihelp.desc1.adduser"));
				desc.add(new String("ihelp.desc1img.adduser"));
				desc.add(new String("ihelp.desc2.modifyuser"));
				desc.add(new String("ihelp.desc1img.modifyuser"));
				desc.add(new String("ihelp.ok"));
				break;
			case 43:
				title=Bundle.getText("ihelp.user.delete");
				desc.add(new String("ihelp.prerequis.adduser"));
				desc.add(new String("ihelp.desc1.adduser"));
				desc.add(new String("ihelp.desc1img.adduser"));
				desc.add(new String("ihelp.desc2.deleteuser"));
				desc.add(new String("ihelp.desc1img.modifyuser"));
				desc.add(new String("ihelp.ok"));
				break;
				
			default: 
				title=Bundle.getText("ihelp.title");
				desc.add(new String("ihelp.img")) ;
				
				break;
		}
		init(title,desc);
		desc.clear();
	}

	private void init(String title, ArrayList desc) {
		
		
		
		this.setLayout(new BorderLayout());
		
		this.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
		this.add(getPanelCentral(title, desc),BorderLayout.NORTH);
	
		this.setBorder(BorderFactory.createEmptyBorder(0,5,5,5));
		
		
		/*this.setLayout(new GridBagLayout());
		GridBagConstraints gb = new GridBagConstraints();
		this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		gb.anchor=GridBagConstraints.NORTH;
		gb.gridx=0;
		gb.gridy=0;
		this.add(getPanelDescription(title), gb);
		
		gb.gridx=0;
		gb.gridy=1;
		
		this.add(getPanelCentral(title,desc),gb);
		this.setBorder(BorderFactory.createEmptyBorder(5,5,20,5));
		
		*/
		
	}

	private JPanel getPanelCentral(String title,ArrayList desc) {
		
		JLabel JDescr = new JLabel();
		JLabel img;
		
		JDescr.setText(Bundle.getText((String)desc.get(0)));
		JDescr.setFont(new Font(null,0,12));
		JMiddlePanel = new JPanel();
	
		JMiddlePanel.setLayout(new GridBagLayout());
		Border cadre = BorderFactory.createEmptyBorder();
		TitledBorder titleStyle = BorderFactory.createTitledBorder(cadre,title);
		JMiddlePanel.setBorder(titleStyle);
		JDescr.setText(Bundle.getText((String)desc.get(0)));
		JDescr.setFont(new Font(null,0,12));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(20, 0, 20, 0);
        //gridBagConstraints.fill= GridBagConstraints.NONE;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
		JMiddlePanel.add(JDescr, gridBagConstraints);
		for (int i =1; i<desc.size();i++){
			if ((Bundle.getText((String)desc.get(i))).endsWith("jpg")){
				img = new  JLabel(new ImageIcon(XAGDOP.class.getResource(Bundle.getText((String)desc.get(i)))));
				gridBagConstraints.insets = new Insets(10, 0, 10, 0);
				gridBagConstraints.anchor = GridBagConstraints.CENTER;

			}
			else{
				img = new JLabel();
		        img.setText(Bundle.getText((String)desc.get(i)));
		        img.setFont(new Font(null,0,12));
		        gridBagConstraints.insets = new Insets(0, 0, 10, 0);
		        gridBagConstraints.anchor = GridBagConstraints.WEST;
				
			}
			gridBagConstraints.gridx = 0;
	        gridBagConstraints.gridy = i;
	        
	        
	        JMiddlePanel.add(img,gridBagConstraints); 
	        
		}
		//setSize(800, 400);
		//JScrollPane jsp =new JScrollPane(JMiddlePanel);
		return JMiddlePanel;

	}
	protected JPanel getPanelDescription(String title){
   		JPanel panelNorth = new PanelDescription(title);
   		return panelNorth;
    }

//	private JLabel JDescLabel(String desc) {
//		JLabel JDesc;
//		JDesc = new JLabel(desc);
//		return JDesc;
//	}

}
