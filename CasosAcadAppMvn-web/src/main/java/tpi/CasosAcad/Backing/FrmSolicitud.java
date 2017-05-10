package tpi.CasosAcad.Backing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import tpi.CasosAcad.Entidades.Solicitud;
import tpi.CasosAcad.Sessions.SolicitudFacadeLocal;

/**
 *
 * @author manuel/sigfrid
 */
@Named(value = "frmSolicitud")
@ViewScoped
public class FrmSolicitud implements Serializable {
    
    private LazyDataModel<Solicitud> modelo;
   
    @EJB
    private SolicitudFacadeLocal sfl;
    
    private Solicitud registro;
    private boolean editar=false;
    private String fecha;
    
    
    @PostConstruct
    public void init(){
    
        
         setModelo(new LazyDataModel<Solicitud>(){

            @Override
            public List<Solicitud> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                List salida = new ArrayList();
                if(sfl != null){
                    this.setRowCount(sfl.count());
                    int[] rango = new int[2];
                    rango[0] = first;
                    rango[1] = pageSize;
                    salida = sfl.findRange(rango);
                }
                return salida;
            }

            @Override
            public Object getRowKey(Solicitud object) {
                return object.getIdSolicitud();
            }

            @Override
            public Solicitud getRowData(String rowKey) {
                if(this.getWrappedData()!=null){
                    List<Solicitud> lista = (List<Solicitud>) this.getWrappedData();
                    if(!lista.isEmpty()) {
                        for(Solicitud get : lista) {
                            if(get.getIdSolicitud().compareTo(Integer.parseInt(rowKey))==0) {
                                return get;
                            }
                        }
                    }
                }
                return null;
            }       
        });
    
    
    }
    public LazyDataModel<Solicitud> getModelo() {
        return modelo;
    }

    public void setModelo(LazyDataModel<Solicitud> modelo) {
        this.modelo = modelo;
    }

    
    
    
    
    
    public Solicitud getRegistro() {
        return registro;
    }

    public void setRegistro(Solicitud registro) {
        this.registro = registro;
    }
    
    public void limpiar(){
    
        RequestContext.getCurrentInstance().reset(":vistaSolicitud");
        this.registro= new Solicitud();
        
    
    }
     public void cambioTabla(){
        this.editar = true;
    }
    
    public void btnNuevoAction(ActionEvent ae){
     editar=false;
        try{
           
           limpiar();
       }catch(Exception e){
       Logger.getLogger(getClass().getName()).log(Level.SEVERE,e.getMessage(),e);
       }
        
    
    }
    
    public void btnGuardarAction(ActionEvent ae){
        try {
          
            if(this.registro != null && this.sfl != null){
                
                boolean resultado = this.sfl.create(registro);
                FacesMessage msj = new FacesMessage(FacesMessage.SEVERITY_INFO, resultado?"Creado con exito":"Error", null);
              
                FacesContext.getCurrentInstance().addMessage(null, msj);
                limpiar();
            }
        } catch (Exception e) {
           Logger.getLogger(getClass().getName()).log(Level.SEVERE,e.getMessage(),e);
        }
     
      }
     
     
      public void btnModificarAction(ActionEvent ae){
        try{
            boolean resultado = this.sfl.editar(registro); 
            FacesMessage msj = new FacesMessage(FacesMessage.SEVERITY_INFO, resultado?"Modificado con exito":"Error", null);
           
            FacesContext.getCurrentInstance().addMessage(null, msj);
            limpiar();
        }catch(Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,e.getMessage(),e);
        }
    }
      
         public void btnEliminarAction(ActionEvent ae) {
        try {
            if(this.registro != null && this.sfl != null){
                boolean resultado = this.sfl.remove(registro);
                FacesMessage msj = new FacesMessage(FacesMessage.SEVERITY_INFO, resultado?"Eliminado con exito":"Error", null);
                FacesContext.getCurrentInstance().addMessage(null, msj);
                limpiar();
                editar=false;
            }
        } catch (Exception e) {
        Logger.getLogger(getClass().getName()).log(Level.SEVERE,e.getMessage(),e);
        
        }
    }
    

    

    public FrmSolicitud() {
    
    this.registro= new Solicitud();
    
    }

    public boolean isEditar() {
        return editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
    }

    /**
     * @return the fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    
}
