package MaV;


/**
* MaV/Votant_Tie.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Electeur.idl
* Sunday, May 6, 2007 3:58:23 PM CEST
*/

public class Votant_Tie extends _VotantImplBase
{

  // Constructors
  public Votant_Tie ()
  {
  }

  public Votant_Tie (MaV.VotantOperations impl)
  {
    super ();
    _impl = impl;
  }


  //Permet de tester son bureau de vote, et ses infos
  public MaV.Electeur verifierElecteur (int insee, int code)
  {
    return _impl.verifierElecteur(insee, code);
  } // verifierElecteur

  public boolean exists (int insee, int code)
  {
    return _impl.exists(insee, code);
  } // exists

  public boolean aDejaVote (int insee)
  {
    return _impl.aDejaVote(insee);
  } // aDejaVote

  public void votePour (int id, int insee)
  {
    _impl.votePour(id, insee);
  } // votePour

  public void deleteElecteur (int id)
  {
    _impl.deleteElecteur(id);
  } // deleteElecteur

  public boolean saveElecteur (MaV.Electeur e)
  {
    return _impl.saveElecteur(e);
  } // saveElecteur

  private MaV.VotantOperations _impl;

} // class Votant_Tie
