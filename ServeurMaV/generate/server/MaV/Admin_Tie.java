package MaV;


/**
* MaV/Admin_Tie.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/claire/workspace/CommonMaV/idl/Admin.idl
* Tuesday, May 22, 2007 7:17:55 PM CEST
*/

public class Admin_Tie extends _AdminImplBase
{

  // Constructors
  public Admin_Tie ()
  {
  }

  public Admin_Tie (MaV.AdminOperations impl)
  {
    super ();
    _impl = impl;
  }

  public void deleteAdmin (int id)
  {
    _impl.deleteAdmin(id);
  } // deleteAdmin

  public boolean verifLogin (String login, String pwd)
  {
    return _impl.verifLogin(login, pwd);
  } // verifLogin

  public boolean saveAdmin (String login, String pwd)
  {
    return _impl.saveAdmin(login, pwd);
  } // saveAdmin

  private MaV.AdminOperations _impl;

} // class Admin_Tie