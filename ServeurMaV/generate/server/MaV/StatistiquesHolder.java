package MaV;

/**
* MaV/StatistiquesHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/nephos/cours/iup/m1/cvs/CommonMaV/idl/Statistiques.idl
* samedi 9 juin 2007 09 h 37 GMT+01:00
*/

public final class StatistiquesHolder implements org.omg.CORBA.portable.Streamable
{
  public MaV.Statistiques value = null;

  public StatistiquesHolder ()
  {
  }

  public StatistiquesHolder (MaV.Statistiques initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = MaV.StatistiquesHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    MaV.StatistiquesHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return MaV.StatistiquesHelper.type ();
  }

}
