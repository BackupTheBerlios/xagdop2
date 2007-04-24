package src;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;

/**
 */
public class ServeurVote {

public static void main(String[] args) {

        try{
        	
        	//DBUtils.getConnection();
            // create and initialize the ORB
            ORB orb = ORB.init(args, null);
 
            // create servant and register it with the ORB
            ListeCImpl listeRef = new ListeCImpl();
            orb.connect(listeRef);
 
            VotantImpl voteRef = new VotantImpl();
            orb.connect(voteRef);
            
            StatsImpl statRef = new StatsImpl();
            orb.connect(statRef);
            
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContext ncRef = NamingContextHelper.narrow(objRef);
             
            NameComponent nc = new NameComponent("ListeC", "");
            NameComponent path[] = {nc};
            ncRef.rebind(path, listeRef);
           
            nc = new NameComponent("Votant", "");
            NameComponent pathv[] = {nc};
            ncRef.rebind(pathv, voteRef);
            
            nc = new NameComponent("Stats", "");
            NameComponent paths[] = {nc};
            ncRef.rebind(paths, voteRef);
            
//          wait for invocations from clients
            java.lang.Object sync = new java.lang.Object();
            synchronized (sync) {
                sync.wait();
            }
 
        } catch (Exception e) {
            System.err.println("ERROR: " + e);
            e.printStackTrace(System.out);
        }
    }


} 
