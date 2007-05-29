package MaV;


/**
* MaV/VotantOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/claire/workspace/CommonMaV/idl/Electeur.idl
* Tuesday, May 22, 2007 7:17:57 PM CEST
*/

public interface VotantOperations 
{

  //Permet de tester son bureau de vote, et ses infos
  MaV.Electeur verifierElecteur (int insee, int code);
  boolean exists (int insee, int code);
  boolean aDejaVote (int insee);
  void votePour (int id, int insee);
  void deleteElecteur (int id);
  boolean saveElecteur (MaV.Electeur e);
  String getNom(int insee);
  
} // interface VotantOperations
