package microcredit.model;

import java.time.LocalDate;

public class Client{

    private String nom;
    private String prenom;
    private String telephone;
    private String adresse;


    private CompteEpargne epargne;

//    private Compte compte ;
    private Pret pret ;



    public Client (String nom,String prenom,String telephone, String adresse) {
        this.nom =nom ;
        this.prenom =prenom ;
        this.telephone = telephone ;
        this.adresse = adresse ;
        this.epargne = new CompteEpargne(this, 0.15);

    }


    public Pret demanderPret(double montant) {
        if (pret == null) {
            if (montant < 1500) {
                this.pret = new Pret(this, montant);

                System.out.println("demander reussi ");

            }
            else {
                System.out.println("montant superieur a la limite");

            }


        }
        else {

            System.out.println("pret en cours .....");
        }

        return this.pret;

    }

    public Pret rembourserPret(double montant ){
        if (pret != null) {
            this.pret.ajouterRemboursement(new Remboursement(montant, LocalDate.now(),this.pret));
            System.out.println("remoursement effectuer avec succes");
            System.out.println(" il vous reste "+
                    this.pret.calculerMontantTotal() +" a rembourser ! ");



        }

        return this.pret ;
    }

    public Pret getPret() {
        return pret;
    }

    public CompteEpargne getEpargne() {
        return epargne;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }



    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }


}
