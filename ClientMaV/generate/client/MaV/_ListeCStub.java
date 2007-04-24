package MaV;


/**
* MaV/_ListeCStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Candidat.idl
* Tuesday, April 24, 2007 6:29:49 PM CEST
*/

public class _ListeCStub extends org.omg.CORBA.portable.ObjectImpl implements MaV.ListeC
{

  public MaV.Candidat[] getAllCandidats ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getAllCandidats", true);
                $in = _invoke ($out);
                MaV.Candidat $result[] = MaV.lcHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getAllCandidats (        );
            } finally {
                _releaseReply ($in);
            }
  } // getAllCandidats

  public void deleteCandidat (int id)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("deleteCandidat", true);
                $out.write_ulong (id);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                deleteCandidat (id        );
            } finally {
                _releaseReply ($in);
            }
  } // deleteCandidat

  public MaV.Mandat[] getMandats (int id)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getMandats", true);
                $out.write_ulong (id);
                $in = _invoke ($out);
                MaV.Mandat $result[] = MaV.lmHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getMandats (id        );
            } finally {
                _releaseReply ($in);
            }
  } // getMandats

  public boolean saveCandidat (MaV.Candidat c)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("saveCandidat", true);
                MaV.CandidatHelper.write ($out, c);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return saveCandidat (c        );
            } finally {
                _releaseReply ($in);
            }
  } // saveCandidat

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:MaV/ListeC:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.Object obj = org.omg.CORBA.ORB.init (args, props).string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     String str = org.omg.CORBA.ORB.init (args, props).object_to_string (this);
     s.writeUTF (str);
  }
} // class _ListeCStub
