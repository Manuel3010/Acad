/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpi.CasosAcad.Recursos;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import tpi.CasosAcad.Entidades.Paso;
//import tpi.CasosAcad.Entidades.Requisito;
import tpi.CasosAcad.Sessions.PasoFacadeLocal;

/**
 *
 * @author manuel
 */
@RequestScoped
@Path("paso")
public class pasoRecurso {
    
    @Inject
    private PasoFacadeLocal pasoFacade;
    
    
     @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Paso> findAll(){
    
    List salida= new ArrayList();
    try{
       if(pasoFacade!=null){
       
       salida=pasoFacade.findAll();
       
       }
                    
    }catch(Exception e){
    Logger.getLogger(getClass().getName()).log(Level.SEVERE,e.getMessage(),e);
    
    }
    return salida;
    
    }    
    
    
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Paso findById(@PathParam("id")final Integer id){
    
        Paso salida= new Paso();
        try{
        if(pasoFacade!=null){
        salida= pasoFacade.find(id);
        
        }
        
        }catch(Exception e){
        Logger.getLogger(getClass().getName()).log(Level.SEVERE,e.getMessage(),e);
        }
    
    return salida;    
    }   
    
    
    
    
    
}
