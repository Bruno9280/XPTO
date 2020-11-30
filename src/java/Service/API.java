/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Controller.CidadeDAO;
import Controller.ConsultaDAO;
import Model.Cidades;
import Model.Consultas;
import Model.cidade;
import Respository.LerArquivoCSV;
import Respository.LerArquivoCSVFiltro;
import com.google.gson.Gson;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author bruno
 */
@Path("XPTO")
public class API {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public API() {

    }

    /**
     * Retrieves representation of an instance of Service.API
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {

        return new Gson().toJson(new LerArquivoCSV());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("Consulta/TotalCidadesUF")
    public String getUfcont() {
        List<Consultas> lista;
        ConsultaDAO uf = new ConsultaDAO();
        lista = uf.listar();
        Gson gs = new Gson();
        return gs.toJson(lista);

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("Consulta/TotalRegistros")
    public String getregcont() {
        CidadeDAO cid = new CidadeDAO();
        Gson gs = new Gson();
        return gs.toJson(cid.count());

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("Consulta/UFMaioreMenor")
    public String getUfcontMaiorMenor() {
        List<Consultas> lista;
        ConsultaDAO uf = new ConsultaDAO();
        lista = uf.listarmaiormenor();
        Gson gs = new Gson();
        return gs.toJson(lista);

    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("Consulta/csv/{coluna}/{filtro}")
    public String filtrar(@PathParam("coluna") String coluna, @PathParam("filtro") String filtro) {
        int colun;
        Gson gs = new Gson();
        switch (coluna){
            case "ibge_id": colun=0;
            break;
            case "uf": colun=1;
            break;
            case "name": colun=2;
            break;
            case "capital": colun=3;
            break;
            case "lon": colun=4;
            break;
            case "lat": colun=5;
            break;
            case "no_accents": colun=6;
            break;
            case "alternative_names": colun=7;
            break;
            case "microregion": colun=8;
            break;
            case "mesoregion": colun=9;
            break;
            default:
        return gs.toJson("Coluna invalida");
            
        }
          
        List lista;
        LerArquivoCSVFiltro csv = new LerArquivoCSVFiltro();
        lista = csv.LerArquivoComFiltroCSV(colun, filtro);
        return gs.toJson(lista);

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("Consulta/ID_IBGE/{id_ibge}")
    public String getID_ibge(@PathParam("id_ibge") int id_ibge) {
        Cidades a = new Cidades();
        CidadeDAO dao = new CidadeDAO();
        a = dao.get(id_ibge);
        Gson gs = new Gson();
        return gs.toJson(a);

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("Consulta/Capitais")
    public String getCidadesCapitais() {
        List<cidade> lista;
        ConsultaDAO uf = new ConsultaDAO();
        lista = uf.listarcapitais();
        Gson gs = new Gson();
        return gs.toJson(lista);

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("Consulta/CidadesPorEstado/{estado}")
    public String getCidadesPorEstado(@PathParam("estado") String estado) {
        List<cidade> lista;
        ConsultaDAO cid = new ConsultaDAO();
        lista = cid.listarCidadesporEstado(estado);
        Gson gs = new Gson();
        return gs.toJson(lista);

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("Consulta/RegistroPorColuna/{coluna}")
    public String Registroporcoluna(@PathParam("coluna") String coluna) {
        ConsultaDAO cid = new ConsultaDAO();
        Gson gs = new Gson();
        return gs.toJson(cid.countColun(coluna));

    }

    /**
     * PUT method for updating or creating an instance of API
     *
     * @param ibge_id
     * @param uf
     * @param name
     * @param capital
     * @param lon
     * @param lat
     * @param no_accents
     * @param alternative_names
     * @param microregion
     * @param mesoregion
     * @return 
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("Cadastro/Cidades/{ibge_id}/{uf}/{name}/{capital}/{lon}/{lat}/{no_accents}/{alternative_names}/{microregion}/{mesoregion}")
    public String NovaCidade(@PathParam("ibge_id") int ibge_id, @PathParam("uf") String uf, @PathParam("name") String name, @PathParam("capital") String capital, @PathParam("lon") double lon, @PathParam("lat") double lat, @PathParam("no_accents") String no_accents, @PathParam("alternative_names") String alternative_names, @PathParam("microregion") String microregion, @PathParam("mesoregion") String mesoregion) {
        Cidades cidade = new Cidades(ibge_id, uf, name, capital, lon, lat, no_accents, alternative_names, microregion, mesoregion);
        CidadeDAO dao = new CidadeDAO();
        Gson gs = new Gson();
        return gs.toJson(dao.inserir(cidade));
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("Deletar/Cidades/{cidade}")
    public String deleteCidade(@PathParam("cidade") String cidade) {
        CidadeDAO cid = new CidadeDAO();
        Gson gs = new Gson();
        return gs.toJson(cid.delet(cidade));
    }

}
