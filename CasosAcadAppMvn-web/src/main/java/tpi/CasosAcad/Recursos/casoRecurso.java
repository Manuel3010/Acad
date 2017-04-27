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
import tpi.CasosAcad.Entidades.Caso;
import tpi.CasosAcad.Sessions.CasoFacadeLocal;

/**
 *
 * @author manuel
 */
@RequestScoped
@Path("caso")
public class casoRecurso  {
    
    
    @Inject
    private CasoFacadeLocal CasoFacade;
    
    
     @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Caso> findAll(){
    
    List salida= new ArrayList();
    try{
       if(CasoFacade!=null){
       
       salida=CasoFacade.findAll();
       
       }
                    
    }catch(Exception e){
    Logger.getLogger(getClass().getName()).log(Level.SEVERE,e.getMessage(),e);
    
    }
    return salida;
    
    }    
    
    
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Caso findById(@PathParam("id")final Integer id){
    
        Caso salida= new Caso();
        try{
        if(CasoFacade!=null){
        salida= CasoFacade.find(id);
        
        }
        
        }catch(Exception e){
        Logger.getLogger(getClass().getName()).log(Level.SEVERE,e.getMessage(),e);
        }
    
    return salida;    
    }    
    
    
    
    
    
    
    
}

