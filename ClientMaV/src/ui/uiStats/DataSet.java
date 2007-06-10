package ui.uiStats;

import org.jfree.data.category.DefaultCategoryDataset;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import src.util.UtilORB;
import MaV.Candidat;
import MaV.ListeC;
import MaV.Statistiques;

public class DataSet extends DefaultCategoryDataset {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static DataSet instance = null;
	private DefaultCategoryDataset dataset = new DefaultCategoryDataset();

	private DataSet()
	{
		try {
			ListeC lca = UtilORB.getListeC();
			Candidat[] ca = lca.getAllCandidats();
			Statistiques stat = UtilORB.getStats();

			for(int i = 0; i<ca.length; i++){
				int nb = stat.getNbVotes(ca[i].id());
				//System.out.println(nb + "  " + ca[i].nom());
				dataset.addValue(nb,  ca[i].nom() , ca[i].nom());	
			}


		} catch (NotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CannotProceed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidName e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static DataSet getInstance(){
		if(instance == null)
			instance = new DataSet();
		return instance;
	}

	public void updateDataSet(DefaultCategoryDataset newDataSet){
		
		dataset = newDataSet;
		//if(uiStats.getInstance() != null)
		uiStats.getInstance().createStats();
		
	}
	
	public DefaultCategoryDataset getDataSet(){
		return dataset;
	}


}
