package MaV;


/**
* MaV/AdminOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/nephos/cours/iup/m1/cvs/CommonMaV/idl/Admin.idl
* samedi 9 juin 2007 09 h 36 GMT+01:00
*/

public interface AdminOperations 
{
  void deleteAdmin (int id);
  boolean verifLogin (String login, String pwd);
  boolean saveAdmin (String login, String pwd);
} // interface AdminOperations
