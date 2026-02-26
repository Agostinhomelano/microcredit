package microcredit.Database;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class AcueilDB {

    public void chargerAcuiel(JTable table)  {
      String sql = "SELECT c.nom, " +
                "p.montant AS montant_pret, " +
                "e.solde AS solde_epargne, " +
                "IFNULL(p.montant - SUM(r.montant), p.montant) AS montant_restant " +
                "FROM clients c " +
                "LEFT JOIN pret p ON p.id_clients = c.id " +
                "LEFT JOIN remboursement r ON r.id_pret = p.id " +
                "LEFT JOIN epargne e ON e.id_clients = c.id " +
                "GROUP BY c.nom, p.montant, e.solde";

        try (Connection co = connctionDB.getConnection();
             PreparedStatement pst = co.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); // vider la table

            while (rs.next()) {
                Vector row = new Vector();
                row.add(rs.getString("nom"));
                row.add(rs.getDouble("montant_pret"));
                row.add(rs.getDouble("montant_restant"));
                row.add(rs.getDouble("solde_epargne"));

                model.addRow(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors du chargement du tableau !");
        }
    }
}
