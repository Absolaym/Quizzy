package iut_orsay.quizzy.quizzy;

public class Question {
    private int id;
    private String intitulé;
    private int idQuizz;

    public Question() {}

    public Question(String intituléQ) { this.intitulé = intituléQ; }

    public int getId() { return id; }
    public void setId(int idQ) { this.id = idQ; }

    public String getIntitulé() { return intitulé; }
    public void setIntitulé(String intituléQ) { this.intitulé = intituléQ; }

    public int getIdQuizz() { return idQuizz; }
    public void setIdQuizz(int idQ) {this.idQuizz = idQ; }

    public String toString() { return "clé : " + id + ", intitulé : " + intitulé + ", idQuizz : " + idQuizz; }
}
