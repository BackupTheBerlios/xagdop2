package MaV;


/**
* MaV/AdminOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Admin.idl
* Sunday, May 6, 2007 3:30:49 PM CEST
*/

public interface AdminOperations 
{
  void deleteAdmin (int id);
  boolean verifLogin (String login, String pwd);
  boolean saveAdmin (String login, String pwd);
} // interface AdminOperations
