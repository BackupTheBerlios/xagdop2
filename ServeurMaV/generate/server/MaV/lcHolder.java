package MaV;


/**
* MaV/lcHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Candidat.idl
* Tuesday, April 24, 2007 6:29:33 PM CEST
*/

public final class lcHolder implements org.omg.CORBA.portable.Streamable
{
  public MaV.Candidat value[] = null;

  public lcHolder ()
  {
  }

  public lcHolder (MaV.Candidat[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = MaV.lcHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    MaV.lcHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return MaV.lcHelper.type ();
  }

}
