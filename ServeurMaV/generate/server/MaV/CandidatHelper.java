package MaV;


/**
* MaV/CandidatHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Candidat.idl
* Sunday, May 6, 2007 3:18:20 PM CEST
*/

abstract public class CandidatHelper
{
  private static String  _id = "IDL:MaV/Candidat/Candidat:1.0";

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
  private static boolean __active = false;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      synchronized (org.omg.CORBA.TypeCode.class)
      {
        if (__typeCode == null)
        {
          if (__active)
          {
            return org.omg.CORBA.ORB.init().create_recursive_tc ( _id );
          }
          __active = true;
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [5];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_ulong);
          _members0[0] = new org.omg.CORBA.StructMember (
            "id",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[1] = new org.omg.CORBA.StructMember (
            "nom",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[2] = new org.omg.CORBA.StructMember (
            "prenom",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_ulong);
          _members0[3] = new org.omg.CORBA.StructMember (
            "age",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[4] = new org.omg.CORBA.StructMember (
            "profession",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (MaV.CandidatHelper.id (), "Candidat", _members0);
          __active = false;
        }
      }
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static MaV.Candidat read (org.omg.CORBA.portable.InputStream istream)
  {
    MaV.Candidat value = new MaV.Candidat ();
    value.id = istream.read_ulong ();
    value.nom = istream.read_string ();
    value.prenom = istream.read_string ();
    value.age = istream.read_ulong ();
    value.profession = istream.read_string ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, MaV.Candidat value)
  {
    ostream.write_ulong (value.id);
    ostream.write_string (value.nom);
    ostream.write_string (value.prenom);
    ostream.write_ulong (value.age);
    ostream.write_string (value.profession);
  }

}
