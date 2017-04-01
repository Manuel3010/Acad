/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpi.CasosAcad.Recursos;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import tpi.CasosAcad.Entidades.TipoRequisito;
import tpi.CasosAcad.Sessions.TipoRequisitoFacadeLocal;

/**
 *
 * @author manuel
 */
@RequestScoped
@Path("tipoRequisito")
public class tipoRequisitoRecurso {

    @Inject
    private TipoRequisitoFacadeLocal tipoRequisitoFacade;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<TipoRequisito> findAll() {

        List salida = new ArrayList();
        try {
            if (tipoRequisitoFacade != null) {

                salida = tipoRequisitoFacade.findAll();
            }
        } catch (Exception e) {

        }
        return salida;
    }
    
    @GET
    @Path("{estado}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<TipoRequisito> findByEstado(@PathParam("activo") boolean estado ) {

        List salida = new ArrayList();
        try {
            if (tipoRequisitoFacade != null) {

                salida = tipoRequisitoFacade.findByEstado(estado);
            }
        } catch (Exception e) {

        }
        return salida;
    }
    
    

  /*   @GET
     @Path("{id}")
     public TipoRequisito findById(@PathParam("id") Integer id){
       TipoRequisito salida= new TipoRequisito();
       
       try{
           
           if(tipoRequisitoFacade!=null){
           
           salida= tipoRequisitoFacade.find(id);
           }                      
       }catch(Exception e){
       }
       return salida;
    }
    
    
    */
    
    
    
    
    
}
