package src;

import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import uiLogin.Login;
import uiManagement.CandidatManagement;
import MaV.ListeC;
import MaV.Votant;

public class ClientVote {


	public static void main(String args[])
	{
		//testClient();
		//new CandidatManagement();
		new Login();
	}

	public static void testClient()
	{
		ListeC listeCref;
		try {
			listeCref = UtilORB.getListeC();
			listeCref.getAllCandidats();
			listeCref.getMandats(1);
		

			Votant votantRef = UtilORB.getVotant();
			votantRef.aDejaVote(123);
			votantRef.verifierElecteur(123,456);
			votantRef.votePour(2, 123);
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
