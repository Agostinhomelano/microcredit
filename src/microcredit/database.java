/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package microcredit;

import com.mysql.cj.jdbc.result.ResultSetMetaData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Agostinho MELANO
 */
public class database {
    public void chargerclient( JTable matable) throws SQLException{
    Connection co = connctionDB.getConnection();
        String sql = "SELECT * FROM clients";
        try{
            PreparedStatement pst ;
            pst= co.prepareStatement(sql);
            ResultSet rs =pst.executeQuery();
            ResultSetMetaData rsm = (ResultSetMetaData) rs.getMetaData();
            int c = rsm.getColumnCount();
            DefaultTableModel DF = (DefaultTableModel) matable.getModel();
            DF.setRowCount(0);
            
            while(rs.next()){
                Vector v2  = new Vector();
                v2.add(rs.getString("id"));
                v2.add(rs.getString("nom"));              
                v2.add(rs.getString("prenom"));
                v2.add(rs.getString("telephone"));
                v2.add(rs.getString("adresse"));                
                v2.add(rs.getString("num_compte"));                                                           
                DF.addRow(v2);
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Erreur" +e);
        }
     }
    public void inserclient(Client c ) throws SQLException {

        String query ="insert into clients(nom,prenom,telephone, adresse, num_compte)\n" +
                "values(?,?,?,?,?)";
        String query2 ="insert into epargne(solde,taux_interet,num_compte_clients) values(?,?,?)";
            Compte compte = new Compte(c);
            String num_compte= compte.getNumeroCompte();
        try (Connection co = connctionDB.getConnection();
             PreparedStatement ps = co.prepareStatement(query);
            PreparedStatement ps2 = co.prepareStatement(query2)){

            ps.setString(1, c.getNom());
            ps.setString(2, c.getPrenom());
            ps.setString(3, c.getTelephone());
            ps.setString(4, c.getAdresse());            
            ps.setString(5, num_compte);            
            ps2.setDouble(1,compte.getSolde());
            ps2.setDouble(2,10);
            ps2.setString(3,num_compte);


            ps2.executeUpdate();
            ps.executeUpdate();
            ps.close();
            co.close();

        }
        
    }
    public void updateclient(Client c,String id)throws SQLException{
        String sql = "update clients set nom=?,prenom=?,telephone=?,adresse=? where id=?";
        try{
             Connection co = connctionDB.getConnection();
            PreparedStatement pst = co.prepareStatement(sql);
            pst.setString(1, c.getNom());
            pst.setString(2, c.getPrenom());
            pst.setString(3, c.getTelephone());
            pst.setString(4, c.getAdresse());
            pst.setString(5,id);
            
            int rs =JOptionPane.showConfirmDialog(null,"voulez-vous vraiment modifier ce champ ?", "Message de confirmation",JOptionPane.YES_NO_OPTION);
            if (rs ==0){
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null,"vous venez de modifier une occurence ");               
            }
            
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur " +e);
            
        }
    }
    public void deleteclient(int id) throws SQLException{
        String query = "DELETE FROM clients where id = ?";

        try (Connection co  = connctionDB.getConnection();
             PreparedStatement ps = co.prepareStatement(query)) {
            ps.setInt(1,id);            
            int rs =JOptionPane.showConfirmDialog(null,"voulez-vous vraiment modifier ce champ ?", "Message de confirmation",JOptionPane.YES_NO_OPTION);
            if (rs ==0){
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null,"vous venez de modifier une occurence ");               
            }
        }
    }
    public int totalclients() throws SQLException{
        String sql ="SELECT count(id) AS total FROM clients;";
        Connection co = connctionDB.getConnection();
        try{
            PreparedStatement pst ;
            pst= co.prepareStatement(sql);
            ResultSet rs =pst.executeQuery();            
            if (rs.next()){
                int total=rs.getInt("total");            
            return total;
            }
            }catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur " +e);            
        }        
        return 0;
    }   
    public int chargerinfosclient(String nom,String num) throws SQLException{
    Connection co = connctionDB.getConnection();
        String sql = "SELECT nom, telephone FROM clients";
        try{
            PreparedStatement pst ;
            pst= co.prepareStatement(sql);
            ResultSet rs =pst.executeQuery();            
            
            while(rs.next()){                 
                String nomclient = rs.getString("nom");
                String telephoneclient = rs.getString("telephone");
                System.out.println(nomclient+telephoneclient);
                if(telephoneclient==num & nomclient == nom ){
                    System.out.println(1);
                    return 1;
                }
                else{
                        return 0;
                      }
                }
                
            
            co.close();       
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Erreur" +e);
        }
        return 0;
     }
}
