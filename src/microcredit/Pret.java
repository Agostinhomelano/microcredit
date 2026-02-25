package microcredit;

import java.util.ArrayList;

public class Pret {
    private double montant;
    private String objectif;
    private String duree;
    private double tauxInteret;
    private int id;
    private ArrayList<Remboursement> remboursements;
    private double montantRestant ;

    public Pret(int idclient,String duree ,double montant,String objectif) {

        this.montant = montant;
        this.objectif = objectif;
        this.tauxInteret = 30;
        this.remboursements = new ArrayList<>();
        this.id = idclient ;
        this.duree= duree;


    }

    public double calculerInteret() {

        return montant * (tauxInteret/100);
    }


    public double calculerMontantTotal() {

        return montant + calculerInteret();
    }


    public void ajouterRemboursement(Remboursement r) {

        remboursements.add(r);
        System.out.println("remboursement effectuer a la hauteur de "+r.getMontant());

    }


    public double calculMontantRestant() {
        double totalpaye = 0  ;
        for(Remboursement r : remboursements ){
            totalpaye += r.getMontant();

        }
        montantRestant = calculerMontantTotal() - totalpaye ;
        return montantRestant ;

    }
    public double getTauxInteret() {
        return tauxInteret;
    }

    public void setTauxInteret(double tauxInteret) {
        this.tauxInteret = tauxInteret;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getobjectif() {
        return objectif;
    }

    public void setobjectif(String objectif) {
        this.objectif = objectif;
    }
     public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }
}

