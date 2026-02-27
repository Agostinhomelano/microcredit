package microcredit.model;

public  class Agent {

    private String nom;
    private String code;
    private String telephone;

    private int id;
    public Agent(String nom, String code) {
        this.nom = nom;
        this.code = code;
    }

    public Agent() {

    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
