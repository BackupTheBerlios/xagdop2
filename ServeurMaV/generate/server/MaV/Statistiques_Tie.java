package MaV;


/**
* MaV/Statistiques_Tie.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/nephos/cours/iup/m1/cvs/CommonMaV/idl/Statistiques.idl
* jeudi 14 juin 2007 20 h 41 GMT+01:00
*/

public class Statistiques_Tie extends _StatistiquesImplBase
{

  // Constructors
  public Statistiques_Tie ()
  {
  }

  public Statistiques_Tie (MaV.StatistiquesOperations impl)
  {
    super ();
    _impl = impl;
  }

  public void enregistrerClientsStats (MaV.StatsCallBack obj)
  {
    _impl.enregistrerClientsStats(obj);
  } // enregistrerClientsStats

  public void deleteClientsStats (MaV.StatsCallBack obj)
  {
    _impl.deleteClientsStats(obj);
  } // deleteClientsStats

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

  private MaV.StatistiquesOperations _impl;

} // class Statistiques_Tie
