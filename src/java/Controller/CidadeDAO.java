/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Cidades;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author bruno
 */
public class CidadeDAO {

    private Connection con;
    private PreparedStatement cmd;

    public CidadeDAO() {
        /*emf = Persistence.createEntityManagerFactory("Cidade");
        em = emf.createEntityManager();*/
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            this.con = DriverManager.getConnection(
                    "jdbc:mariadb://127.0.0.1/cidade",
                    "root",
                    "password"
            );
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
            this.con = null;
        }

        /* public void salvar(Object obj){
        try {
            em.getTransaction().begin();
            em.persist(obj);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
        }finally{
            em.close();
            emf.close();
        }*/
    }

    public int inserir(Cidades c) {
        try {
            String SQL = "insert into cidades(ibge_id,uf,name,capital,lon,lat,no_accents,alternative_names,microregion,mesoregion) VALUES(?,?,?,?,?,?,?,?,?,?);";
            cmd = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            cmd.setInt(1, c.getIbge_id());
            cmd.setString(2, c.getUf());
            cmd.setString(3, c.getName());
            cmd.setString(4, c.getCapital());
            cmd.setDouble(5, c.getLon());
            cmd.setDouble(6, c.getLat());
            cmd.setString(7, c.getNo_accents());
            cmd.setString(8, c.getAlternative_names());
            cmd.setString(9, c.getMicroregion());
            cmd.setString(10, c.getMesoregion());

            if (cmd.executeUpdate() > 0) {
                ResultSet rs = cmd.getGeneratedKeys();
                return (rs.next()) ? rs.getInt(1) : 0;

            }
            cmd.close();
            return -1;

        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
            return -1;
        } finally {
            try {
                if (cmd != null) {
                    cmd.close();
                }
                con.close();
            } catch (SQLException e) {
                // LOGGING
                e.printStackTrace();
            }
        }
    }

    public Cidades get(int id) {
        try {
            String SQL = "SELECT ibge_id,uf,name,capital,lon,lat,no_accents,alternative_names,microregion,mesoregion from cidades where ibge_id= ?";
            cmd = con.prepareStatement(SQL);
            cmd.setInt(1, id);
            ResultSet rs = cmd.executeQuery();
            if (rs.next()) {
                return new Cidades(
                        rs.getInt("ibge_id"),
                        rs.getString("uf"),
                        rs.getString("name"),
                        rs.getString("capital"),
                        rs.getDouble("lon"),
                        rs.getDouble("lat"),
                        rs.getString("no_accents"),
                        rs.getString("alternative_names"),
                        rs.getString("microregion"),
                        rs.getString("mesoregion")
                );
            }
            return null;
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
            return null;
        }
    }

    public int count() {
        try {
            int count = 0;
            String SQL = "SELECT  COUNT(id) AS Qtd FROM  cidades";
            cmd = con.prepareStatement(SQL);
            ResultSet rs = cmd.executeQuery();
            if (rs.next()) {

                count = rs.getInt("Qtd");
            }

            return count;
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
            return -1;
        }
    }

    public int delet(String p) {
        try {
            String SQL = "DELETE FROM cidades WHERE name =?";
            cmd = con.prepareStatement(SQL);
            cmd.setString(1, p);
            return cmd.executeUpdate();
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
            return -1;
        }
    }

}
