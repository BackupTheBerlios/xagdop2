package MaV;


/**
* MaV/VotantOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Electeur.idl
* Tuesday, April 17, 2007 1:47:32 PM CEST
*/

public interface VotantOperations 
{

  //Permet de tester son bureau de vote, et ses infos
  MaV.Electeur verifierElecteur (int insee, int code);
  boolean aDejaVote (int insee);
  void votePour (int id, int insee);
} // interface VotantOperations
