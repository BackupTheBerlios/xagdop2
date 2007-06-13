package src.util;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import src.StatsCallBackImpl;
import MaV.ListeC;
import MaV.ListeCHelper;
import MaV.Statistiques;
import MaV.StatistiquesHelper;
import MaV.Votant;
import MaV.VotantHelper;

public class UtilORB {

	private static NamingContext ncRef = null;
	private static StatsCallBackImpl statImpl = null;
	private static int nbInstances = 0;
	
	public static Votant getVotant() throws NotFound, CannotProceed, InvalidName{
//      bind the Object Reference in Naming
        NameComponent nce = new NameComponent("Votant", "");
        NameComponent pathe[] = {nce};
        return VotantHelper.narrow(getNamingContext().resolve(pathe));
    	
	}
	
	public static ListeC getListeC() throws NotFound, CannotProceed, InvalidName{
//   	 bind the Object Reference in Naming
        NameComponent nc = new NameComponent("ListeC", "");
        NameComponent path[] = {nc};
        return ListeCHelper.narrow(getNamingContext().resolve(path));
		
	}
	
	public static Statistiques getStats() throws NotFound, CannotProceed, InvalidName{
//  	 bind the Object Reference in Naming
       NameComponent nc = new NameComponent("Stats", "");
       NameComponent path[] = {nc};
       return StatistiquesHelper.narrow(getNamingContext().resolve(path));
		
	}
	
	public static void registerStats(){
		String[] arg = {"-ORBInitialPort","2000"};
		ORB orb = ORB.init(arg, null);
		
		if(statImpl == null){
			statImpl = new StatsCallBackImpl();
			orb.connect(statImpl);
			try {
				
				UtilORB.getStats().enregistrerClientsStats(statImpl);
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
		
		nbInstances ++;
	}
	
	public static void unregisterStats(){
		
		nbInstances--;
		if(nbInstances == 0)
			try {
				
				getStats().deleteClientsStats(statImpl);
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
	
	private static NamingContext getNamingContext()
	{
		if(ncRef==null)
		{ 
			String[] args = {"-ORBInitialPort","2000"};
			 ORB orb = ORB.init(args, null);
            // get the root naming context
            org.omg.CORBA.Object objRef;
			try {
				objRef = orb.resolve_initial_references("NameService");
				ncRef = NamingContextHelper.narrow(objRef);
			} catch (org.omg.CORBA.ORBPackage.InvalidName e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
		}
		return ncRef;
	}
	
}
