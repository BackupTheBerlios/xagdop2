package MaV;

/**
* MaV/VotantHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Electeur.idl
* Tuesday, April 24, 2007 6:29:56 PM CEST
*/

public final class VotantHolder implements org.omg.CORBA.portable.Streamable
{
  public MaV.Votant value = null;

  public VotantHolder ()
  {
  }

  public VotantHolder (MaV.Votant initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = MaV.VotantHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    MaV.VotantHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return MaV.VotantHelper.type ();
  }

}
