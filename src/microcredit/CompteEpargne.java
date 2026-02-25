package microcredit;
public class CompteEpargne extends Compte{

    private double tauxInteret ;
    public CompteEpargne(Client client, double tauxInteret) {
        super(client);
        this.tauxInteret = tauxInteret;

    }
    public void deposer(double montant) {
        solde += montant ;
    }
    public void retirer(double montant) {
        if (solde - montant > 0 ){
            solde -= montant;
        }
        else{
            System.out.println("solde insuffisant !");
        }
    }

    @Override
    public String toString() {
        return "votre solde epargne est de : "+getSolde();
    }
}
