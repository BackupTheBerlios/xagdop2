package MaV;

/**
* MaV/ListeCHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/nephos/cours/iup/m1/cvs/CommonMaV/idl/Candidat.idl
* jeudi 14 juin 2007 20 h 56 GMT+01:00
*/

public final class ListeCHolder implements org.omg.CORBA.portable.Streamable
{
  public MaV.ListeC value = null;

  public ListeCHolder ()
  {
  }

  public ListeCHolder (MaV.ListeC initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = MaV.ListeCHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    MaV.ListeCHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return MaV.ListeCHelper.type ();
  }

}
