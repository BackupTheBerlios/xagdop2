package MaV;


/**
* MaV/_VotantImplBase.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Electeur.idl
* Tuesday, April 17, 2007 1:47:32 PM CEST
*/

public abstract class _VotantImplBase extends org.omg.CORBA.portable.ObjectImpl
                implements MaV.Votant, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors
  public _VotantImplBase ()
  {
  }

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("verifierElecteur", new java.lang.Integer (0));
    _methods.put ("aDejaVote", new java.lang.Integer (1));
    _methods.put ("votePour", new java.lang.Integer (2));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {

  //Permet de tester son bureau de vote, et ses infos
       case 0:  // MaV/Votant/verifierElecteur
       {
         int insee = in.read_ulong ();
         int code = in.read_ulong ();
         MaV.Electeur $result = null;
         $result = this.verifierElecteur (insee, code);
         out = $rh.createReply();
         MaV.ElecteurHelper.write (out, $result);
         break;
       }

       case 1:  // MaV/Votant/aDejaVote
       {
         int insee = in.read_ulong ();
         boolean $result = false;
         $result = this.aDejaVote (insee);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 2:  // MaV/Votant/votePour
       {
         int id = in.read_ulong ();
         int insee = in.read_ulong ();
         this.votePour (id, insee);
         out = $rh.createReply();
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:MaV/Votant:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }


} // class _VotantImplBase
