package microcredit.model;

import java.util.ArrayList;

public class Pret {
    private double montant;
    private String duree;
    private double tauxInteret;
    private Client client;
    private ArrayList<Remboursement> remboursements;
    private double montantRestant ;


    private int idClient;
    private String objectif;
    private int id;

    public Pret(Client client ,double montant) {

        this.montant = montant;
        this.tauxInteret = 30;
        this.remboursements = new ArrayList<>();
        this.client = client ;


    }

    public Pret(int idDuClient, String temp, int montant) {
        this.idClient = idDuClient;
        this.duree =temp;
        this.montant =montant;

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

    @Override
    public String toString() {
        return "le montant de votre pret est de "+ calculerMontantTotal();
    }

    public double getTauxInteret() {
        return 30;
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public int getid() {
        return idClient;
    }

    public String getobjectif() {
        return objectif ;
    }

    public void setId(int id) {
        this.id = id;
    }
}
