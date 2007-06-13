/**
 * 
 */
package src;

import junit.framework.TestCase;
import MaV.Electeur;
import MaV.Votant;

/**
 * @author claire
 *
 */
public class VotantImplTest extends TestCase {

	/**
	 * Test method for {@link src.VotantImpl#aDejaVote(int)}.
	 */
	public void testADejaVote() {
		Votant v = new VotantImpl();
		v.votePour2(1, 123, 1);
		assertTrue(v.aDejaVote(123));
		
		assertFalse(v.aDejaVote(000));
		
		Electeur e = new Electeur(456,"toto","titi",1,789);
		v.saveElecteur(e);
		assertFalse(v.aDejaVote(456));
		v.deleteElecteur(456);
	}

	/**
	 * Test method for {@link src.VotantImpl#verifierElecteur(int, int)}.
	 */
	public void testVerifierElecteur() {
		Votant v = new VotantImpl();
		Electeur e = new Electeur(456,"toto","titi",1,789);
		v.saveElecteur(e);
		assertTrue(v.exists(456, 789));
		v.deleteElecteur(456);
		
		assertFalse(v.exists(000, 789));
	}

	/**
	 * Test method for {@link src.VotantImpl#deleteElecteur(int)}.
	 */
	public void testDeleteElecteur() {
		Votant v = new VotantImpl();
		Electeur e = new Electeur(456,"toto","titi",1,789);
		v.saveElecteur(e);
		assertTrue(v.exists(456, 789));
		v.deleteElecteur(456);
		assertFalse(v.exists(456, 789));
	}

	/**
	 * Test method for {@link src.VotantImpl#saveElecteur(MaV.Electeur)}.
	 */
	public void testSaveElecteur() {
		Votant v = new VotantImpl();
		Electeur e = new Electeur(456,"toto","titi",1,789);
		v.saveElecteur(e);
		assertTrue(v.exists(456, 789));
	}

	/**
	 * Test method for {@link src.VotantImpl#exists(int, int)}.
	 */
	public void testExists() {
		Votant v = new VotantImpl();
		Electeur e = new Electeur(456,"toto","titi",1,789);
		v.saveElecteur(e);
		assertTrue(v.exists(456, 789));
		
	}

	/**
	 * Test method for {@link src.VotantImpl#getNom(int)}.
	 */
	public void testGetNom() {
		Votant v = new VotantImpl();
		Electeur e = new Electeur(456,"toto","titi",1,789);
		v.saveElecteur(e);
		VotantImpl vi = new VotantImpl();
		assertTrue(vi.getNom(456).equals("toto"));
	}

	/**
	 * Test method for {@link src.VotantImpl#votePour2(int, int, int)}.
	 */
	public void testVotePour2() {
		Votant v = new VotantImpl();
		v.votePour2(1, 123, 1);
		assertTrue(v.aDejaVote(123));
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		Votant v = new VotantImpl();
		v.deleteElecteur(456);
	}

}
