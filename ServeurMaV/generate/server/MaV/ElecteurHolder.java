package MaV;

/**
* MaV/ElecteurHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/claire/workspace/CommonMaV/idl/Electeur.idl
* Tuesday, May 22, 2007 7:17:56 PM CEST
*/

public final class ElecteurHolder implements org.omg.CORBA.portable.Streamable
{
  public MaV.Electeur value = null;

  public ElecteurHolder ()
  {
  }

  public ElecteurHolder (MaV.Electeur initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = MaV.ElecteurHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    MaV.ElecteurHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return MaV.ElecteurHelper.type ();
  }

}
