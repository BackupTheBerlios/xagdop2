package src.util;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import MaV.ListeC;
import MaV.ListeCHelper;
import MaV.Votant;
import MaV.VotantHelper;

public class UtilORB {

	private static NamingContext ncRef = null;
	
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
