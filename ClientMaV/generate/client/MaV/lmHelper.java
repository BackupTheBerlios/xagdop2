package MaV;


/**
* MaV/lmHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/nephos/cours/iup/m1/cvs/CommonMaV/idl/Candidat.idl
* jeudi 14 juin 2007 20 h 56 GMT+01:00
*/

abstract public class lmHelper
{
  private static String  _id = "IDL:MaV/lm:1.0";

  public static void insert (org.omg.CORBA.Any a, MaV.Mandat[] that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static MaV.Mandat[] extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = MaV.MandatHelper.type ();
      __typeCode = org.omg.CORBA.ORB.init ().create_sequence_tc (0, __typeCode);
      __typeCode = org.omg.CORBA.ORB.init ().create_alias_tc (MaV.lmHelper.id (), "lm", __typeCode);
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static MaV.Mandat[] read (org.omg.CORBA.portable.InputStream istream)
  {
    MaV.Mandat value[] = null;
    int _len0 = istream.read_long ();
    value = new MaV.Mandat[_len0];
    for (int _o1 = 0;_o1 < value.length; ++_o1)
      value[_o1] = MaV.MandatHelper.read (istream);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, MaV.Mandat[] value)
  {
    ostream.write_long (value.length);
    for (int _i0 = 0;_i0 < value.length; ++_i0)
      MaV.MandatHelper.write (ostream, value[_i0]);
  }

}
