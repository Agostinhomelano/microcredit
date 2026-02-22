/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package microcredit;

/**
 *
 * @author Agostinho MELANO
 */
public class Microcredit {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         Client victoire = new Client("vic", "kongolo", "06", "bolingo");
         CompteEpargne numcompte = new CompteEpargne(victoire,0.3);
         System.out.println(numcompte.getNumeroCompte());
         System.out.println(numcompte.getSolde());
         System.out.println(numcompte.getDette());
         System.out.println(numcompte.getClient());
         System.out.println(numcompte);
        victoire.demanderPret(1400);
        System.out.println(victoire.getPret().calculMontantRestant());
        victoire.rembourserPret(400);
        System.out.println(victoire.getPret().calculMontantRestant());
        Accueil accueil = new Accueil() ;
        accueil.setVisible(true);
    }
    
}
