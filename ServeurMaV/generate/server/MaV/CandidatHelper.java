package MaV;


/**
* MaV/CandidatHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/nephos/cours/iup/m1/cvs/CommonMaV/idl/Candidat.idl
* jeudi 14 juin 2007 20 h 41 GMT+01:00
*/

abstract public class CandidatHelper
{
  private static String  _id = "IDL:MaV/Candidat:1.0";

  public static void insert (org.omg.CORBA.Any a, MaV.Candidat that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static MaV.Candidat extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (MaV.CandidatHelper.id (), "Candidat");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static MaV.Candidat read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_CandidatStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, MaV.Candidat value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static MaV.Candidat narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof MaV.Candidat)
      return (MaV.Candidat)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      MaV._CandidatStub stub = new MaV._CandidatStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static MaV.Candidat unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof MaV.Candidat)
      return (MaV.Candidat)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      MaV._CandidatStub stub = new MaV._CandidatStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
