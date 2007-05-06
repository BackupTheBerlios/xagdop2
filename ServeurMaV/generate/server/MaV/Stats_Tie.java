package MaV;


/**
* MaV/Stats_Tie.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Stats.idl
* Sunday, May 6, 2007 3:33:12 PM CEST
*/

public class Stats_Tie extends _StatsImplBase
{

  // Constructors
  public Stats_Tie ()
  {
  }

  public Stats_Tie (MaV.StatsOperations impl)
  {
    super ();
    _impl = impl;
  }

  public int getNbVotes (int id)
  {
    return _impl.getNbVotes(id);
  } // getNbVotes

  public int getNbVotesParBureau (int idB, int id)
  {
    return _impl.getNbVotesParBureau(idB, id);
  } // getNbVotesParBureau

  public int getNbVotesParCanton (int idCa, int id)
  {
    return _impl.getNbVotesParCanton(idCa, id);
  } // getNbVotesParCanton

  public int getNbVotesParCirc (int idCi, int id)
  {
    return _impl.getNbVotesParCirc(idCi, id);
  } // getNbVotesParCirc

  public int getNbVotesParDept (int idD, int id)
  {
    return _impl.getNbVotesParDept(idD, id);
  } // getNbVotesParDept

  private MaV.StatsOperations _impl;

} // class Stats_Tie
