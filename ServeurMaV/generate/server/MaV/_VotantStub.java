package MaV;


/**
* MaV/_VotantStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Electeur.idl
* Tuesday, April 17, 2007 1:47:32 PM CEST
*/

public class _VotantStub extends org.omg.CORBA.portable.ObjectImpl implements MaV.Votant
{


  //Permet de tester son bureau de vote, et ses infos
  public MaV.Electeur verifierElecteur (int insee, int code)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("verifierElecteur", true);
                $out.write_ulong (insee);
                $out.write_ulong (code);
                $in = _invoke ($out);
                MaV.Electeur $result = MaV.ElecteurHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return verifierElecteur (insee, code        );
            } finally {
                _releaseReply ($in);
            }
  } // verifierElecteur

  public boolean aDejaVote (int insee)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("aDejaVote", true);
                $out.write_ulong (insee);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return aDejaVote (insee        );
            } finally {
                _releaseReply ($in);
            }
  } // aDejaVote

  public void votePour (int id, int insee)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("votePour", true);
                $out.write_ulong (id);
                $out.write_ulong (insee);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                votePour (id, insee        );
            } finally {
                _releaseReply ($in);
            }
  } // votePour

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:MaV/Votant:1.0"};

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
} // class _VotantStub
