package MaV;


/**
* MaV/lstatsHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/nephos/cours/iup/m1/cvs/CommonMaV/idl/Electeur.idl
* samedi 9 juin 2007 09 h 43 GMT+01:00
*/

public final class lstatsHolder implements org.omg.CORBA.portable.Streamable
{
  public MaV.Stats value[] = null;

  public lstatsHolder ()
  {
  }

  public lstatsHolder (MaV.Stats[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = MaV.lstatsHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    MaV.lstatsHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return MaV.lstatsHelper.type ();
  }

}
