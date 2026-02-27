package microcredit.Database;

import microcredit.model.Agent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AgentDB {
    public Agent verifierConnexion(String nom, String numero, String code) throws  SQLException {
        String sql = "SELECT * FROM agent WHERE nom = ? AND telephone = ? AND code = ?";
        try (Connection co = connctionDB.getConnection();
             PreparedStatement ps = co.prepareStatement(sql)) {

            ps.setString(1, nom);
            ps.setString(2, numero);
            ps.setString(3, code);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // agent trouvé
                Agent agent = new Agent();
                agent.setId(rs.getInt("id"));
                agent.setNom(rs.getString("nom"));
                agent.setTelephone(rs.getString("numero"));
                agent.setCode(rs.getString("code"));
                return agent;
            } else {
                // agent non trouvé
                return null;
            }
        }
    }
}
