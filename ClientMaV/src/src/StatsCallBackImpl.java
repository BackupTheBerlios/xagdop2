/**
 * 
 */
package src;

import org.jfree.data.category.DefaultCategoryDataset;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import src.util.UtilORB;
import ui.uiStats.DataSet;
import MaV.ListeC;
import MaV.Stats;
import MaV._StatsCallBackImplBase;

/**
 * @author claire
 *
 */
public class StatsCallBackImpl extends _StatsCallBackImplBase{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 2951748864844209358L;


	
	public void callback(Stats[] s) {
		// TODO Auto-generated method stub
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(int i = 0; i<s.length; i++){
			try {
				ListeC lc = UtilORB.getListeC();
			dataset.addValue(s[i].nbVotes, lc.getNomCandidat(s[i].idCandidat) , lc.getNomCandidat(s[i].idCandidat));
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
		DataSet.getInstance().updateDataSet(dataset);
		
	}
}
