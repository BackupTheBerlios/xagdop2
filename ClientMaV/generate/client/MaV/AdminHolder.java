package MaV;

/**
* MaV/AdminHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/nephos/cours/iup/m1/cvs/CommonMaV/idl/Admin.idl
* samedi 9 juin 2007 00 h 57 GMT+01:00
*/

public final class AdminHolder implements org.omg.CORBA.portable.Streamable
{
  public MaV.Admin value = null;

  public AdminHolder ()
  {
  }

  public AdminHolder (MaV.Admin initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = MaV.AdminHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    MaV.AdminHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return MaV.AdminHelper.type ();
  }

}
