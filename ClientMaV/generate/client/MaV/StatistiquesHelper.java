package MaV;


/**
* MaV/StatistiquesHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/nephos/cours/iup/m1/cvs/CommonMaV/idl/Statistiques.idl
* samedi 9 juin 2007 09 h 43 GMT+01:00
*/

abstract public class StatistiquesHelper
{
  private static String  _id = "IDL:MaV/Statistiques:1.0";

  public static void insert (org.omg.CORBA.Any a, MaV.Statistiques that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static MaV.Statistiques extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (MaV.StatistiquesHelper.id (), "Statistiques");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static MaV.Statistiques read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_StatistiquesStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, MaV.Statistiques value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static MaV.Statistiques narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof MaV.Statistiques)
      return (MaV.Statistiques)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      MaV._StatistiquesStub stub = new MaV._StatistiquesStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static MaV.Statistiques unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof MaV.Statistiques)
      return (MaV.Statistiques)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      MaV._StatistiquesStub stub = new MaV._StatistiquesStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
