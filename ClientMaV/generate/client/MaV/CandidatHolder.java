package MaV;

/**
* MaV/CandidatHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Candidat.idl
* Tuesday, April 17, 2007 1:46:47 PM CEST
*/

public final class CandidatHolder implements org.omg.CORBA.portable.Streamable
{
  public MaV.Candidat value = null;

  public CandidatHolder ()
  {
  }

  public CandidatHolder (MaV.Candidat initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = MaV.CandidatHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    MaV.CandidatHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return MaV.CandidatHelper.type ();
  }

}
