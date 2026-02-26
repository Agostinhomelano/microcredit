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
            ps.setDouble(4, p.getid());
            ps.setDate(5, Date.valueOf(LocalDate.now()));


            ps.executeUpdate();
            ps.close();
            co.close();

        }


    }
    public void actualiserPret(Pret pret ) throws SQLException {

        String sql = " UPDATE pret  set montant = ? where id = ? ";
        try (Connection co = connctionDB.getConnection();
             PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setDouble(1,pret.calculMontantRestant());
            ps.setInt(2,pret.getid());

            ps.executeUpdate();
            ps.close();



        }

    }


    public void chargerpret(JTable matable) throws SQLException {
        Connection co = connctionDB.getConnection();
        String sql = "SELECT c.nom, c.telephone, p.montant, p.duree " +
                "FROM pret p LEFT JOIN clients c ON p.id_clients = c.id";

        PreparedStatement pst = co.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        DefaultTableModel DF = (DefaultTableModel) matable.getModel();
        DF.setRowCount(0);  // vide les anciennes lignes

        while(rs.next()){
            Vector v2 = new Vector();
            v2.add(rs.getString("nom"));
            v2.add(rs.getString("telephone"));
            v2.add(rs.getDouble("montant"));
            v2.add(rs.getString("duree"));
            DF.addRow(v2);
        }
    }
}
