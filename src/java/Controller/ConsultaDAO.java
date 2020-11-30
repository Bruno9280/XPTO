/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Cidades;
import Model.Consultas;
import Model.cidade;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bruno
 */
public class ConsultaDAO {

    private Connection con;
    private PreparedStatement cmd;

    public ConsultaDAO() {
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

    public List<Consultas> listar() {
        try {
            String SQL = "SELECT   uf, COUNT(uf) AS Qtd FROM  cidades GROUP BY uf ORDER BY COUNT(uf) DESC";
            cmd = con.prepareStatement(SQL);
            ResultSet rs = cmd.executeQuery();
            if (rs.next()) {
                List<Consultas> lista = new ArrayList<>();
                do {
                    lista.add(
                            new Consultas(
                                    rs.getString("uf"),
                                    rs.getInt("Qtd")
                            )
                    );
                } while (rs.next());
                return lista;
            }
            return null;
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
            return null;
        }
    }

    public List<Consultas> listarmaiormenor() {
        String estadomais = null, estadomenos = null;
        int qtdmais = 0, qtdmenos = 0;

        try {
            String SQL = "SELECT   uf, COUNT(uf) AS Qtd FROM  cidades GROUP BY uf ORDER BY COUNT(uf) DESC";
            cmd = con.prepareStatement(SQL);
            ResultSet rs = cmd.executeQuery();
            if (rs.next()) {
                List<Consultas> lista = new ArrayList<>();
                do {
                    if (rs.getInt("Qtd") > qtdmais) {

                        estadomais = rs.getString("uf");
                        qtdmais = rs.getInt("Qtd");
                        if (qtdmenos == 0) {
                            qtdmenos = rs.getInt("Qtd");
                        }
                    } else if (rs.getInt("Qtd") < qtdmenos) {
                        estadomenos = rs.getString("uf");
                        qtdmenos = rs.getInt("Qtd");

                    }
                } while (rs.next());
                lista.add(
                        new Consultas(
                                estadomais, qtdmais
                        )
                );
                lista.add(
                        new Consultas(
                                estadomenos, qtdmenos
                        )
                );
                return lista;
            }
            return null;
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
            return null;
        }
    }

    public List<cidade> listarcapitais() {
        try {
            String SQL = "Select name from cidades where capital=\"true\" order by (name)";
            cmd = con.prepareStatement(SQL);
            ResultSet rs = cmd.executeQuery();
            if (rs.next()) {
                List<cidade> lista = new ArrayList<>();
                do {
                    lista.add(new cidade(
                            rs.getString("name")
                    )
                    );
                } while (rs.next());
                return lista;
            }
            return null;
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
            return null;
        }
    }

    public List<cidade> listarCidadesporEstado(String uf) {
        try {
            String SQL = "Select name from cidades where uf=? order by (name)";
            cmd = con.prepareStatement(SQL);
            cmd.setString(1, uf);
            ResultSet rs = cmd.executeQuery();
            if (rs.next()) {
                List<cidade> lista = new ArrayList<>();
                do {
                    lista.add(new cidade(
                            rs.getString("name")
                    )
                    );
                } while (rs.next());
                return lista;
            }
            return null;
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
            return null;
        }
    }

    public int countColun(String colun) {
        try {
            int count = 0;
            String SQL = "SELECT  COUNT(DISTINCT " + colun + ") AS Qtd FROM  cidades";
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

}
