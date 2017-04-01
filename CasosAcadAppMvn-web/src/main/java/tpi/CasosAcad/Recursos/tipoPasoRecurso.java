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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import tpi.CasosAcad.Entidades.TipoPaso;
import tpi.CasosAcad.Sessions.TipoPasoFacadeLocal;

/**
 *
 * @author manuel
 */

@RequestScoped
@Path("tipoPaso")

public class tipoPasoRecurso {
    
    
    @Inject
    private TipoPasoFacadeLocal tipoPasoFacade;
    
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<TipoPaso> findAll(){
    
    List salida= new ArrayList();
    try{
       if(tipoPasoFacade!=null){
       
       salida=tipoPasoFacade.findAll();
       
       }
                    
    }catch(Exception e){
    
    
    }
    return salida;
    
    }    
    
    
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public TipoPaso findById(@PathParam("id")final Integer id){
    
        TipoPaso salida= new TipoPaso();
        try{
        if(tipoPasoFacade!=null){
        salida= tipoPasoFacade.find(id);
        
        }
        
        }catch(Exception e){
        
        }
    
    return salida;    
    }    
    
    
    
    
    
}
