
package tpi.CasosAcad.Recursos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import tpi.CasosAcad.Entidades.Solicitud;
import tpi.CasosAcad.Sessions.SolicitudFacadeLocal;

/**
 *
 * @author sigfrid
 */


@RequestScoped
@Path("Solicitud")
public class solicitudRecurso implements Serializable{
    @Inject
    private SolicitudFacadeLocal SolicitudFacade;
    
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Solicitud> findAll(){
    
    List salida= new ArrayList();
    try{
       if(SolicitudFacade!=null){
       
       salida=SolicitudFacade.findAll();
       
       }
                    
    }catch(Exception e){
    
    
    }
    return salida;
    
    }    
    
    
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Solicitud findById(@PathParam("id")final Integer id){
    
        Solicitud salida= new Solicitud();
        try{
        if(SolicitudFacade!=null){
        salida= SolicitudFacade.find(id);
        
        }
        
        }catch(Exception e){
        
        }
    
    return salida;    
    }    
    
    
    
    
}
