package MaV;

/**
* MaV/VoteCallBackHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/nephos/cours/iup/m1/cvs/CommonMaV/idl/Electeur.idl
* samedi 9 juin 2007 09 h 43 GMT+01:00
*/

public final class VoteCallBackHolder implements org.omg.CORBA.portable.Streamable
{
  public MaV.VoteCallBack value = null;

  public VoteCallBackHolder ()
  {
  }

  public VoteCallBackHolder (MaV.VoteCallBack initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = MaV.VoteCallBackHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    MaV.VoteCallBackHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return MaV.VoteCallBackHelper.type ();
  }

}
