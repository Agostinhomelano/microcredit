/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package microcredit.Database;

import com.mysql.cj.jdbc.result.ResultSetMetaData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Agostinho MELANO
 */
public class EpargneDB {
    public void chargerepargne(JTable matable) throws SQLException {
        Connection co = connctionDB.getConnection();
        String sql = "select c.nom,c.prenom,e.num_compte_clients,e.solde from epargne e left join clients c on c.num_compte = e.num_compte_clients ;";
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
                v2.add(rs.getString("nom"));
                v2.add(rs.getString("prenom"));
                v2.add(rs.getString("num_compte_clients"));
                v2.add(rs.getString("solde"));    
                DF.addRow(v2);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur" + e);
        }
    }  
     public void chargerepargneparid(JTable matable,int id) throws SQLException {
        Connection co = connctionDB.getConnection();
        String sql = "select c.nom,c.prenom,e.num_compte_clients,e.solde from epargne e left join clients c on c.num_compte = e.num_compte_clients where c.id= ? ;";
        try {
            PreparedStatement pst;
            pst = co.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            ResultSetMetaData rsm = (ResultSetMetaData) rs.getMetaData();
            int c = rsm.getColumnCount();
            DefaultTableModel DF = (DefaultTableModel) matable.getModel();
            DF.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();                
                v2.add(rs.getString("nom"));
                v2.add(rs.getString("prenom"));
                v2.add(rs.getString("num_compte_clients"));
                v2.add(rs.getString("solde"));    
                DF.addRow(v2);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur" + e);
        }
    }
}
