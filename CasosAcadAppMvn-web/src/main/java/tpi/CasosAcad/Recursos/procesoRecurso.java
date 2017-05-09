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
import tpi.CasosAcad.Entidades.Proceso;
import tpi.CasosAcad.Sessions.ProcesoFacadeLocal;

/**
 *
 * @author manuel
 */

@RequestScoped
@Path("Proceso")

public class procesoRecurso {   
    @Inject
    private ProcesoFacadeLocal ProcesoFacade;
 
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Proceso> findAll(){
    List salida= new ArrayList();
    try{
       if(ProcesoFacade!=null){
       salida=ProcesoFacade.findAll();
       }
    }catch(Exception e){
    }
    return salida;
    }    
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Proceso findById(@PathParam("id")final Integer id){
        Proceso salida= new Proceso();
        try{
        if(ProcesoFacade!=null){
        salida= ProcesoFacade.find(id);
        }
        }catch(Exception e){
        }
    return salida;    
    }        
}