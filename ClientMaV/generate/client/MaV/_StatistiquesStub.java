package MaV;


/**
* MaV/_StatistiquesStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/nephos/cours/iup/m1/cvs/CommonMaV/idl/Statistiques.idl
* jeudi 14 juin 2007 20 h 56 GMT+01:00
*/

public class _StatistiquesStub extends org.omg.CORBA.portable.ObjectImpl implements MaV.Statistiques
{

  public void enregistrerClientsStats (MaV.StatsCallBack obj)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("enregistrerClientsStats", true);
                MaV.StatsCallBackHelper.write ($out, obj);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                enregistrerClientsStats (obj        );
            } finally {
                _releaseReply ($in);
            }
  } // enregistrerClientsStats

  public void deleteClientsStats (MaV.StatsCallBack obj)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("deleteClientsStats", true);
                MaV.StatsCallBackHelper.write ($out, obj);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                deleteClientsStats (obj        );
            } finally {
                _releaseReply ($in);
            }
  } // deleteClientsStats

  public int getNbVotes (int id)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getNbVotes", true);
                $out.write_ulong (id);
                $in = _invoke ($out);
                int $result = $in.read_ulong ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getNbVotes (id        );
            } finally {
                _releaseReply ($in);
            }
  } // getNbVotes

  public int getNbVotesParBureau (int idB, int id)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getNbVotesParBureau", true);
                $out.write_ulong (idB);
                $out.write_ulong (id);
                $in = _invoke ($out);
                int $result = $in.read_ulong ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getNbVotesParBureau (idB, id        );
            } finally {
                _releaseReply ($in);
            }
  } // getNbVotesParBureau

  public int getNbVotesParCanton (int idCa, int id)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getNbVotesParCanton", true);
                $out.write_ulong (idCa);
                $out.write_ulong (id);
                $in = _invoke ($out);
                int $result = $in.read_ulong ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getNbVotesParCanton (idCa, id        );
            } finally {
                _releaseReply ($in);
            }
  } // getNbVotesParCanton

  public int getNbVotesParCirc (int idCi, int id)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getNbVotesParCirc", true);
                $out.write_ulong (idCi);
                $out.write_ulong (id);
                $in = _invoke ($out);
                int $result = $in.read_ulong ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getNbVotesParCirc (idCi, id        );
            } finally {
                _releaseReply ($in);
            }
  } // getNbVotesParCirc

  public int getNbVotesParDept (int idD, int id)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getNbVotesParDept", true);
                $out.write_ulong (idD);
                $out.write_ulong (id);
                $in = _invoke ($out);
                int $result = $in.read_ulong ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getNbVotesParDept (idD, id        );
            } finally {
                _releaseReply ($in);
            }
  } // getNbVotesParDept

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:MaV/Statistiques:1.0"};

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
} // class _StatistiquesStub
