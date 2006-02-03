package xagdop.JUnit;

import org.tmatesoft.svn.core.SVNException;

import xagdop.Controleur.CProject;
import xagdop.Parser.ProjectsParser;
import xagdop.Parser.UsersParser;
import junit.framework.TestCase;


public class CProjectTest extends TestCase {

	/*
	 * Test method for 'xagdop.Controleur.CProject.createProject(String, String)'
	 */
	public void testCreateProject()  {
		
		//Declarations
		CProject Cp = new CProject();
		ProjectsParser pp = ProjectsParser.getInstance();
		
	
		
		try {
			//Creer un nouveau projet
			Cp.createProject("projTest","projDresc");
			
			//Test
			assertTrue(pp.isProject("projTest"));
			
			//Supprimer le projet
			pp.removeProject("projTest");
		} catch (SVNException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		

	}

	/*
	 * Test method for 'xagdop.Controleur.CProject.deleteProject(CTreeNode)'
	 */
	public void testDeleteProject() {
//		//Declarations
//		CProject Cp = new CProject();
//		ProjectsParser pp = ProjectsParser.getInstance();
//		
//		try {
//			//Creer le projet
//			Cp.createProject("projTest","projDesc");
//			
//			
//		} catch (SVNException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

}
