package MaV;


/**
* MaV/Votant_Tie.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Electeur.idl
* Tuesday, April 24, 2007 6:29:39 PM CEST
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

  public boolean aDejaVote (int insee)
  {
    return _impl.aDejaVote(insee);
  } // aDejaVote

  public void votePour (int id, int insee)
  {
    _impl.votePour(id, insee);
  } // votePour

  private MaV.VotantOperations _impl;

} // class Votant_Tie
