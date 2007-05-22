package src;
public class CandidatImpl extends MaV._CandidatImplBase  {
	int id;
	String nom;
	String prenom;
	int age;
	String profession;

	public String _toString() {
		return nom()+" "+prenom();
	}

	public int age() {
		return age;
	}

	public void age(int newAge) {
		age= newAge;
	}

	public int id() {
		return id;
	}

	public void id(int newId) {
		id = newId;
	}

	public String nom() {
		return nom;
	}

	public void nom(String newNom) {
		nom = newNom;
	}

	public String prenom() {
		return prenom;
	}

	public void prenom(String newPrenom) {
		prenom = newPrenom;
	}

	public String profession() {
		return profession;
	}

	public void profession(String newProfession) {
		profession = newProfession;
	}

}
