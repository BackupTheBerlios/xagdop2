package MaV;


/**
* MaV/ListeC_Tie.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Candidat.idl
* Sunday, May 6, 2007 3:18:20 PM CEST
*/

public class ListeC_Tie extends _ListeCImplBase
{

  // Constructors
  public ListeC_Tie ()
  {
  }

  public ListeC_Tie (MaV.ListeCOperations impl)
  {
    super ();
    _impl = impl;
  }

  public MaV.Candidat[] getAllCandidats ()
  {
    return _impl.getAllCandidats();
  } // getAllCandidats

  public void deleteCandidat (int id)
  {
    _impl.deleteCandidat(id);
  } // deleteCandidat

  public MaV.Mandat[] getMandats (int id)
  {
    return _impl.getMandats(id);
  } // getMandats

  public boolean saveCandidat (MaV.Candidat c)
  {
    return _impl.saveCandidat(c);
  } // saveCandidat

  private MaV.ListeCOperations _impl;

} // class ListeC_Tie
