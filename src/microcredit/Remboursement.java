package microcredit;

import java.text.Format;
import java.time.LocalDate;

public class Remboursement {
    private int id;
    private double montant;
    private LocalDate datePaiement;
    private Pret pret;

    public Remboursement(double montant, LocalDate datePaiement, Pret pret) {

        this.montant = montant;
        this.datePaiement = datePaiement;
        this.pret = pret;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pret getPret() {
        return pret;
    }

    public void setPret(Pret pret) {
        this.pret = pret;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }


    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }
}
