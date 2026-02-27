package microcredit.Database;

import microcredit.model.Pret;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.time.LocalDate;
import java.util.Vector;

public class PretDB {



    public void creerpret(Pret p ) throws SQLException {
        String query = "INSERT INTO pret(montant,duree,taux_interet,id_clients,date_demande) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection co = connctionDB.getConnection();
             PreparedStatement ps = co.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDouble(1, p.calculerMontantTotal());
            ps.setString(2, p.getDuree());
            ps.setDouble(3, p.getTauxInteret());
            ps.setInt(4, p.getClient().getId());  //utiliser l'ID du client
            ps.setDate(5, Date.valueOf(LocalDate.now()));

            ps.executeUpdate();

            // récupérer l'ID auto-increment du prêt
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                p.setId(generatedId);
                System.out.println("ID du prêt généré : " + generatedId);
            }
        }
    }
    public void actualiserPret(Pret pret ) throws SQLException {

        String sql = " UPDATE pret  set montant = ? where id = ? ";
        try (Connection co = connctionDB.getConnection();
             PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setDouble(1,pret.calculMontantRestant());
            ps.setInt(2,pret.getId());

            ps.executeUpdate();
            ps.close();



        }

    }


    public void chargerpret(JTable matable) throws SQLException {
        Connection co = connctionDB.getConnection();

        // 1Calcul du montant restant à partir des remboursements
        String sql = "SELECT p.id, c.nom, c.telephone, p.montant, p.duree, " +
                "IFNULL(p.montant - SUM(r.montant), p.montant) AS montant_restant " +
                "FROM pret p " +
                "LEFT JOIN clients c ON p.id_clients = c.id " +
                "LEFT JOIN remboursement r ON r.id_pret = p.id " +
                "GROUP BY p.id";

        PreparedStatement pst = co.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        DefaultTableModel DF = (DefaultTableModel) matable.getModel();
        DF.setRowCount(0);  // vide les anciennes lignes


        String updateSql = "UPDATE pret SET montant_restant = ? WHERE id = ?";
        PreparedStatement psUpdate = co.prepareStatement(updateSql);

        while(rs.next()){
            int idPret = rs.getInt("id");
            double montant = rs.getDouble("montant");
            double montant_restant = rs.getDouble("montant_restant");

            //maj table pret
            psUpdate.setDouble(1, montant_restant);
            psUpdate.setInt(2, idPret);
            psUpdate.executeUpdate();

            // 3️⃣ Ajouter dans le JTable
            Vector v2 = new Vector();
            v2.add(rs.getString("nom"));
            v2.add(rs.getString("telephone"));
            v2.add(montant);
            v2.add(rs.getString("duree"));
            v2.add(montant_restant > 0 ? "En cours" : "Remboursé");

            DF.addRow(v2);
        }

        psUpdate.close();
        pst.close();
        co.close();
    }

    public int countPret() {
        int total = 0;
        String sql = "SELECT COUNT(*) AS total FROM pret where montant_restant >  0";

        try (Connection co = connctionDB.getConnection();
             PreparedStatement ps = co.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                total = rs.getInt("total");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();

        }

        return total;
    }

    public int totalpret() {
        int total = 0;
        String sql = "SELECT sum(montant) AS total FROM pret where montant_restant >  0";

        try (Connection co = connctionDB.getConnection();
             PreparedStatement ps = co.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                total = rs.getInt("total");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();

        }

        return total;
    }
}
