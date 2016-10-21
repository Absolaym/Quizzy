package esasy.quizzy.squizzy;

public class Quizz {
    private int id;
    private String nom;

    public Quizz() {}

    public Quizz(String nomQ) {
        this.nom = nomQ;
    }

    public int getId() {
        return id;
    }

    public void setId(int idQ) {
        this.id = idQ;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nomQ) {
        this.nom = nomQ;
    }

    public String toString() {
        return "clé : " + id + ", nom : "+nom;
    }

}