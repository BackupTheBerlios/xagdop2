package MaV;

/**
* MaV/ListeCHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Candidat.idl
* Tuesday, April 17, 2007 1:46:47 PM CEST
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
