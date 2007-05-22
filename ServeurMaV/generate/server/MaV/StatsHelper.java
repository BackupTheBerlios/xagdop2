package MaV;


/**
* MaV/StatsHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/claire/workspace/CommonMaV/idl/Stats.idl
* Tuesday, May 22, 2007 7:17:56 PM CEST
*/

abstract public class StatsHelper
{
  private static String  _id = "IDL:MaV/Stats:1.0";

  public static void insert (org.omg.CORBA.Any a, MaV.Stats that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static MaV.Stats extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (MaV.StatsHelper.id (), "Stats");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static MaV.Stats read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_StatsStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, MaV.Stats value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static MaV.Stats narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof MaV.Stats)
      return (MaV.Stats)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      MaV._StatsStub stub = new MaV._StatsStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static MaV.Stats unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof MaV.Stats)
      return (MaV.Stats)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      MaV._StatsStub stub = new MaV._StatsStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
