package src;

import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import src.util.ArrayListStorageContainer;
import src.util.UtilORB;
import ui.main.MainFrame;
import MaV.ListeC;
import MaV.Votant;

public class ClientVote {


	public static void main(String args[])
	{
		//uiStats.getInstance();
		//testClient();
		//new CandidatManagement();
		//new Login();
		//new ElecteurManagement();
		try {
			MainFrame myFrame = new MainFrame();
			myFrame.setVisible(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//testClient();

		/*test.add(new Candidat(1,"bla","blabla",21,"Escroc"));
		test.add(new Candidat(2,"Malcor","blabla",21,"maire"));
		test.add(new Candidat(3,"Ricard","blabla",21,"deputé"));
		test.add(new Candidat(4,"Metivier","blabla",21,"présidente"));
		test.add(new Candidat(6,"Dupuis","blabla",21,"Glandeur"));
		listeCref = UtilORB.getListeC();*/
		//new Login();
		/*try {
			MainFrame myFrame = new MainFrame();
			myFrame.setVisible(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	public static void testClient()
	{
		ListeC listeCref;
		try {
			listeCref = UtilORB.getListeC();
			listeCref.getAllCandidats();
			//listeCref.getMandats(1);
			ArrayListStorageContainer test = new ArrayListStorageContainer(listeCref.getAllCandidats());

		//	new ElecteurManagement(new ElecteurComparator(),new ElecteurSearchable(), test);
			Votant votantRef = UtilORB.getVotant();
			votantRef.aDejaVote(123);
			votantRef.verifierElecteur(123,456);
			//votantRef.votePour(2, 123);

			
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



}
