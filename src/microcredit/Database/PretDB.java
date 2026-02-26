package microcredit.Database;

import microcredit.model.Pret;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.time.LocalDate;
import java.util.Vector;

public class PretDB {



    public void creerpret(Pret p ) throws SQLException {

        String query ="insert into pret(montant,duree,taux_interet,id_clients,date_demande)\n" +
                "values(?,?,?,?,?)";
        try (Connection co = connctionDB.getConnection();
             PreparedStatement ps = co.prepareStatement(query)){

            ps.setDouble(1, p.calculerMontantTotal());
            ps.setString(2, p.getDuree());
            ps.setDouble(3, p.getTauxInteret());
            ps.setDouble(4, p.getId());
            ps.setDate(5, Date.valueOf(LocalDate.now()));


            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                int generatedId = rs.getInt(1);
                p.setId(generatedId);
                System.out.println("ID généré : " + generatedId);
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
        String sql = "SELECT c.nom, c.telephone, p.montant, p.duree,p.montant_restant " +
                "FROM pret p LEFT JOIN clients c ON p.id_clients = c.id";

        PreparedStatement pst = co.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        DefaultTableModel DF = (DefaultTableModel) matable.getModel();
        DF.setRowCount(0);  // vide les anciennes lignes

        while(rs.next()){
            double montant = rs.getDouble("montant");
            double montant_restant = rs.getDouble("montant_restant"); // ou autre champ existant

            Vector v2 = new Vector();
            v2.add(rs.getString("nom"));
            v2.add(rs.getString("telephone"));
            v2.add(rs.getDouble("montant"));
            v2.add(rs.getString("duree"));

            if (montant_restant >0) {
                v2.add("en cours..");
            } else {
                v2.add("Remboursé");
            }
            System.out.println(montant_restant);

            DF.addRow(v2);
        }
    }
}
