package microcredit;

public class Compte{
    private Client client ;
    private Pret pret ;
    private String numeroCompte ;
    protected Double solde ;
    private double dette ;

    public Compte(Client client) {
        this.client = client;
        this.numeroCompte = GenerateurCompte.genererNumeroCompte() ;
        this.solde= 0.0;


 }

    public Double getSolde() {
        return solde;
    }

    public double getDette() {
        return dette;
    }

    public void setDette(double dette) {
        this.dette = dette;
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }


    public Pret getPret() {
        return pret;
    }

    public void setPret(Pret pret) {
        this.pret = pret;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
