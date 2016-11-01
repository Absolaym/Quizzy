package iut_orsay.quizzy.quizzy;

public class Réponse {
    private int id;
    private String intitulé;
    private boolean OK;
    private int idQuestion;

    public Réponse() {}

    public Réponse(String intituléR, boolean okR) {
        this.intitulé = intituléR;
        this.OK = okR;
    }

    public int getId() { return id; }
    public void setId(int idR) { this.id = idR; }

    public String getIntitulé() { return intitulé; }
    public void setIntitulé(String intituléR) { this.intitulé = intituléR; }

    public boolean getBool() { return OK; }
    public void setBool(boolean okR) { this.OK = okR; }

    public int getIdQuestion() { return this.idQuestion; }
    public void setIdQuestion(int idQ) { this.idQuestion = idQ; }

    public String toString() { return "clé : " + id + ", intitulé : " + intitulé + ", idQuestion : " + idQuestion; }
}
