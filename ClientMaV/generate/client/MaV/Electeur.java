package MaV;


/**
* MaV/Electeur.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Electeur.idl
* Sunday, May 6, 2007 3:58:26 PM CEST
*/

public final class Electeur implements org.omg.CORBA.portable.IDLEntity
{
  public int insee = (int)0;
  public String nom = null;
  public String prenom = null;
  public String bureau = null;
  public String canton = null;
  public String circ = null;
  public String dept = null;

  public Electeur ()
  {
  } // ctor

  public Electeur (int _insee, String _nom, String _prenom, String _bureau, String _canton, String _circ, String _dept)
  {
    insee = _insee;
    nom = _nom;
    prenom = _prenom;
    bureau = _bureau;
    canton = _canton;
    circ = _circ;
    dept = _dept;
  } // ctor

} // class Electeur
