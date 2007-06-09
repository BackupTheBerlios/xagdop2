package MaV;


/**
* MaV/Candidat_Tie.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/nephos/cours/iup/m1/cvs/CommonMaV/idl/Candidat.idl
* samedi 9 juin 2007 09 h 37 GMT+01:00
*/

public class Candidat_Tie extends _CandidatImplBase
{

  // Constructors
  public Candidat_Tie ()
  {
  }

  public Candidat_Tie (MaV.CandidatOperations impl)
  {
    super ();
    _impl = impl;
  }

  public int id ()
  {
    return _impl.id();
  } // id
  public void id (int newId)
  {
    _impl.id(newId);
  } // id

  public String nom ()
  {
    return _impl.nom();
  } // nom
  public void nom (String newNom)
  {
    _impl.nom(newNom);
  } // nom

  public String prenom ()
  {
    return _impl.prenom();
  } // prenom
  public void prenom (String newPrenom)
  {
    _impl.prenom(newPrenom);
  } // prenom

  public int age ()
  {
    return _impl.age();
  } // age
  public void age (int newAge)
  {
    _impl.age(newAge);
  } // age

  public String profession ()
  {
    return _impl.profession();
  } // profession
  public void profession (String newProfession)
  {
    _impl.profession(newProfession);
  } // profession

  public MaV.Mandat[] getMandats ()
  {
    return _impl.getMandats();
  } // getMandats

  public MaV.Mandat createMandat (String titre, String anneeD, String anneeF)
  {
    return _impl.createMandat(titre, anneeD, anneeF);
  } // createMandat

  public void removeMandat (int idMandat)
  {
    _impl.removeMandat(idMandat);
  } // removeMandat

  private MaV.CandidatOperations _impl;

} // class Candidat_Tie
