package microcredit.Database;

import com.mysql.cj.jdbc.result.ResultSetMetaData;
import microcredit.model.Client;
import microcredit.model.Compte;
import microcredit.model.Pret;
import microcredit.model.Remboursement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Vector;

public class ClientDB {

    public void chargerclient(JTable matable) throws SQLException {
        Connection co = connctionDB.getConnection();
        String sql = "SELECT * FROM clients";
        try {
            PreparedStatement pst;
            pst = co.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            ResultSetMetaData rsm = (ResultSetMetaData) rs.getMetaData();
            int c = rsm.getColumnCount();
            DefaultTableModel DF = (DefaultTableModel) matable.getModel();
            DF.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();
                v2.add(rs.getString("id"));
                v2.add(rs.getString("nom"));
                v2.add(rs.getString("prenom"));
                v2.add(rs.getString("telephone"));
                v2.add(rs.getString("adresse"));
                v2.add(rs.getString("num_compte"));
                DF.addRow(v2);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur" + e);
        }
    }


    public void inserclient(Client c) throws SQLException {

        String query = "insert into clients(nom,prenom,telephone, adresse, num_compte)\n" +
                "values(?,?,?,?,?)";
        String query2 = "insert into epargne(solde,taux_interet,num_compte_clients) values(?,?,?)";
        Compte compte = new Compte(c);
        String num_compte = compte.getNumeroCompte();
        try (Connection co = connctionDB.getConnection();
             PreparedStatement ps = co.prepareStatement(query);
             PreparedStatement ps2 = co.prepareStatement(query2)) {

            ps.setString(1, c.getNom());
            ps.setString(2, c.getPrenom());
            ps.setString(3, c.getTelephone());
            ps.setString(4, c.getAdresse());
            ps.setString(5, num_compte);
            ps2.setDouble(1, compte.getSolde());
            ps2.setDouble(2, 10);
            ps2.setString(3, num_compte);


            ps2.executeUpdate();
            ps.executeUpdate();
            ps.close();
            co.close();

        }

    }


    public void updateclient(Client c, String id) throws SQLException {
        String sql = "update clients set nom=?,prenom=?,telephone=?,adresse=? where id=?";
        try {
            Connection co = connctionDB.getConnection();
            PreparedStatement pst = co.prepareStatement(sql);
            pst.setString(1, c.getNom());
            pst.setString(2, c.getPrenom());
            pst.setString(3, c.getTelephone());
            pst.setString(4, c.getAdresse());
            pst.setString(5, id);

            int rs = JOptionPane.showConfirmDialog(null, "voulez-vous vraiment modifier ce champ ?", "Message de confirmation", JOptionPane.YES_NO_OPTION);
            if (rs == 0) {
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "vous venez de modifier une occurence ");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur " + e);

        }
    }


    public void deleteclient(int id) throws SQLException {
        String query = "DELETE FROM clients where id = ?";

        try (Connection co = connctionDB.getConnection();
             PreparedStatement ps = co.prepareStatement(query)) {
            ps.setInt(1, id);
            int rs = JOptionPane.showConfirmDialog(null, "voulez-vous vraiment modifier ce champ ?", "Message de confirmation", JOptionPane.YES_NO_OPTION);
            if (rs == 0) {
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "vous venez de modifier une occurence ");
            }
        }
    }

    public int totalclients() throws SQLException {
        String sql = "SELECT count(id) AS total FROM clients;";
        Connection co = connctionDB.getConnection();
        try {
            PreparedStatement pst;
            pst = co.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int total = rs.getInt("total");
                return total;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur " + e);
        }
        return 0;

    }


    public Integer chercheridclient(String nom, String telephone) throws SQLException {

        Connection co = connctionDB.getConnection();
        String sql = "SELECT id FROM clients WHERE nom = ? AND telephone = ?";

        try {
            PreparedStatement pst = co.prepareStatement(sql);
            pst.setString(1, nom);
            pst.setString(2, telephone);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur " + e);
        }

        return null;
    }





    public Client chargerinfosclient(String Nom, String tel) throws SQLException {

        Connection co = connctionDB.getConnection();

        // 1️⃣ Charger le client
        String sqlClient = "SELECT * FROM clients WHERE nom=? AND telephone=?";
        PreparedStatement pstClient = co.prepareStatement(sqlClient);
        pstClient.setString(1, Nom);
        pstClient.setString(2, tel);

        ResultSet rsClient = pstClient.executeQuery();

        if (!rsClient.next()) {
            return null; // client introuvable
        }

        int idClient = rsClient.getInt("id");
        String nom = rsClient.getString("nom");
        String prenom = rsClient.getString("prenom");
        String telephone = rsClient.getString("telephone");
        String adresse = rsClient.getString("adresse");
        String num_compte = rsClient.getString("num_compte");



        Client client = new Client(nom,prenom,tel,adresse);
        client.setId(idClient);
        client.getEpargne().setNumeroCompte(num_compte);




        // Charger le prêt du client
        String sqlPret = "SELECT * FROM pret WHERE id_clients=?";
        PreparedStatement pstPret = co.prepareStatement(sqlPret);
        pstPret.setInt(1, idClient);

        ResultSet rsPret = pstPret.executeQuery();

        if (rsPret.next()) {

            int idPret = rsPret.getInt("id");
            double montant = rsPret.getDouble("montant");

            Pret pret = new Pret(client, montant);
            pret.setId(idPret);   // ajoute setId dans Pret



            // Charger les remboursements
            String sqlRemb = "SELECT * FROM remboursement WHERE id_pret=?";
            PreparedStatement pstRemb = co.prepareStatement(sqlRemb);
            pstRemb.setInt(1, idPret);

            ResultSet rsRemb = pstRemb.executeQuery();

            while (rsRemb.next()) {

                double montantR = rsRemb.getDouble("montant");
                LocalDate dateR = rsRemb.getDate("dateRemboursement").toLocalDate();


                Remboursement remboursement =  new Remboursement(montantR, pret);

                pret.ajouterRemboursement(remboursement);
            }

            client.setPret(pret); // ajoute un setter
        }

        return client;
    }
}



