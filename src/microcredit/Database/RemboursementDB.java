package microcredit.Database;

import microcredit.model.Client;
import microcredit.model.Pret;
import microcredit.model.Remboursement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class RemboursementDB {

    public void ajouterRemboursementBD(Remboursement r,Pret p) throws SQLException, SQLException {

        Connection co = connctionDB.getConnection();

        String sql = "INSERT INTO remboursement (id_pret, montant, date_paiement) VALUES (?, ?, ?)";

        PreparedStatement pst = co.prepareStatement(sql);
        pst.setInt(1, r.getPret().getId());
        pst.setDouble(2, r.getMontant());
        pst.setDate(3, java.sql.Date.valueOf(r.getDatePaiement()));

        pst.executeUpdate();

        System.out.println("Remboursement enregistré en BD !");
    }

//    public double calculMontantRestantBD(int pretId) throws SQLException {
//
//        Connection con = connctionDB.getConnection();
//
//        String sql = "SELECT SUM(montant) as total FROM remboursement WHERE id_pret = ?";
//
//        PreparedStatement pst = con.prepareStatement(sql);
//        pst.setInt(1, pretId);
//
//        ResultSet rs = pst.executeQuery();
//
//        double totalPaye = 0;
//
//        if(rs.next()){
//            totalPaye = rs.getDouble("total");
//        }
//
//        return calculerMontantTotal() - totalPaye;
//    }

    public void chargerRemboursements(JTable table) throws SQLException {

        Connection con = connctionDB.getConnection();

        String sql = "select r.id , r.montant, r.date_paiement,c.nom from remboursement r\n" +
                "join pret p \n" +
                "on p.id = r.id_pret\n" +
                "join clients c\n" +
                "on c.id = p.id_clients\n";

        PreparedStatement pst = con.prepareStatement(sql);


        ResultSet rs = pst.executeQuery();

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // vider la table

        while (rs.next()) {

            Vector row = new Vector();

            row.add(rs.getInt("id"));
            row.add(rs.getString("nom"));
            row.add(rs.getDouble("montant"));
            row.add(rs.getDate("date_paiement"));


            model.addRow(row);
        }
    }

}
