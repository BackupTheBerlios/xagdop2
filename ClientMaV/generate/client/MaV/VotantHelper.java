package MaV;


/**
* MaV/VotantHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Electeur.idl
* Tuesday, April 17, 2007 1:46:42 PM CEST
*/

abstract public class VotantHelper
{
  private static String  _id = "IDL:MaV/Votant:1.0";

  public static void insert (org.omg.CORBA.Any a, MaV.Votant that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static MaV.Votant extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (MaV.VotantHelper.id (), "Votant");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static MaV.Votant read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_VotantStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, MaV.Votant value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static MaV.Votant narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof MaV.Votant)
      return (MaV.Votant)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      MaV._VotantStub stub = new MaV._VotantStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static MaV.Votant unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof MaV.Votant)
      return (MaV.Votant)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      MaV._VotantStub stub = new MaV._VotantStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
