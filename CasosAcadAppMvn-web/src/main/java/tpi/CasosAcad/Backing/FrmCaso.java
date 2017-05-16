/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpi.CasosAcad.Backing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
import tpi.CasosAcad.Entidades.Proceso;
import tpi.CasosAcad.Entidades.Solicitud;
import tpi.CasosAcad.Entidades.Caso;
import tpi.CasosAcad.Sessions.CasoFacadeLocal;
import tpi.CasosAcad.Sessions.SolicitudFacadeLocal;
import tpi.CasosAcad.Sessions.ProcesoFacadeLocal;

/**
 *
 * @author manuel
 */
@Named(value = "frmCaso")
@ViewScoped
public class FrmCaso implements Serializable {

    private LazyDataModel<Caso> modeloCaso;
    private LazyDataModel<Proceso> modeloProceso;
    private LazyDataModel<Solicitud> modeloSolicitud;
    
    private Caso registro; //= new Requisito();
    private Solicitud solicitud; //tipo
    private Proceso proceso;
    private List<Solicitud> Solicituds; //tipos
    private List<Proceso> Procesos;
    private boolean editar=false;    
    
    @EJB
    private CasoFacadeLocal cfl;
    @EJB
    private SolicitudFacadeLocal sfl;
    @EJB
    private ProcesoFacadeLocal pfl;
    
    
    @PostConstruct
    public void init(){
        
             this.Solicituds= sfl.findAll();
             this.Procesos= pfl.findAll();
        
             setModeloCaso(new LazyDataModel<Caso>(){

            @Override
            public List<Caso> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                List salida = new ArrayList();
                if(cfl != null){
                    this.setRowCount(cfl.count());
                    int[] rango = new int[2];
                    rango[0] = first;
                    rango[1] = pageSize;
                    salida = cfl.findRange(rango);
                }
                return salida;
            }

            @Override
            public Object getRowKey(Caso object) {
                return object.getIdCaso();
            }

            @Override
            public Caso getRowData(String rowKey) {
                if(this.getWrappedData()!=null){
                    List<Caso> lista = (List<Caso>) this.getWrappedData();
                    if(!lista.isEmpty()) {
                        for(Caso get : lista) {
                            if(get.getIdCaso().compareTo(Integer.parseInt(rowKey))==0) {
                                return get;
                            }
                        }
                    }
                }
                return null;
            }       
        });

             
             setModeloSolicitud(new LazyDataModel<Solicitud>(){

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
             
                          setModeloProceso(new LazyDataModel<Proceso>(){

            @Override
            public List<Proceso> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                List salida = new ArrayList();
                if(pfl != null){
                    this.setRowCount(pfl.count());
                    int[] rango = new int[2];
                    rango[0] = first;
                    rango[1] = pageSize;
                    salida = pfl.findRange(rango);
                }
                return salida;
            }

            @Override
            public Object getRowKey(Proceso object) {
                return object.getIdProceso();
            }

            @Override
            public Proceso getRowData(String rowKey) {
                if(this.getWrappedData()!=null){
                    List<Proceso> lista = (List<Proceso>) this.getWrappedData();
                    if(!lista.isEmpty()) {
                        for(Proceso get : lista) {
                            if(get.getIdProceso().compareTo(Integer.parseInt(rowKey))==0) {
                                return get;
                            }
                        }
                    }
                }
                return null;
            }       
        });
             
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
    
    public void limpiar(){
        RequestContext.getCurrentInstance().reset(":tabViewCaso:edAddCaso");
        this.registro=new Caso();
        //this.solicitud.setPendiente(false);
    }
    
     public Integer getSolicitudSeleccionado(){
     if(registro!= null){
            if(registro.getIdSolicitud()!= null){
                return this.registro.getIdSolicitud().getIdSolicitud();
            } else {
                return null;
            }         
        } else {
            return null;
        }
    }
    
    public void setSolicitudSeleccionado(Integer idSolicitud){
        if(idSolicitud >= 0 && !this.Solicituds.isEmpty()){
            for(Solicitud tre : this.getSolicituds()) {
                if(Objects.equals(tre.getIdSolicitud(), idSolicitud)) {
                    if(this.registro.getIdSolicitud()!= null) {
                        this.registro.getIdSolicitud().setIdSolicitud(idSolicitud);
                    } else {
                        this.registro.setIdSolicitud(tre);
                    }
                }
            }
        }
    
    }
      
     public Integer getProcesoSeleccionado(){
     if(registro!= null){
            if(registro.getIdProceso()!= null){
                return this.registro.getIdProceso().getIdProceso();
            } else {
                return null;
            }         
        } else {
            return null;
        }
    }
    
    public void setProcesoSeleccionado(Integer idProceso){
        if(idProceso>= 0 && !this.Procesos.isEmpty()){
            for(Proceso tre : this.getProcesos()) {
                if(Objects.equals(tre.getIdProceso(), idProceso)) {
                    if(this.registro.getIdProceso() != null) {
                        this.registro.getIdProceso().setIdProceso(idProceso);
                    } else {
                        this.registro.setIdProceso(tre);
                    }
                }
            }
        }
    
    }
    
    
          public void btnGuardarAction(ActionEvent ae){
        try {    
            if(this.registro != null && this.cfl != null){
                //this.solicitud.setPendiente(false);
                //this.sfl.editar(solicitud);
                boolean resultado = this.cfl.create(registro);
                
                FacesMessage msj = new FacesMessage(FacesMessage.SEVERITY_INFO, resultado?"Creado con exito":"Error", null);
                //this.agregar = !resultado;
                FacesContext.getCurrentInstance().addMessage(null, msj);
                limpiar();
            }
        } catch (Exception e) {
           Logger.getLogger(getClass().getName()).log(Level.SEVERE,e.getMessage(),e);
        }
     
      }
     
     
      public void btnModificarAction(ActionEvent ae){
        try{
            boolean resultado = this.cfl.editar(registro); 
            FacesMessage msj = new FacesMessage(FacesMessage.SEVERITY_INFO, resultado?"Modificado con exito":"Error", null);
            //this.editar = resultado;
            FacesContext.getCurrentInstance().addMessage(null, msj);
            limpiar();
        }catch(Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,e.getMessage(),e);
        }
    }

            public void btnEliminarAction(ActionEvent ae) {
        try {
            if(this.registro != null && this.cfl != null){
                boolean resultado = this.cfl.remove(registro);
                editar=!resultado;
                FacesMessage msj = new FacesMessage(FacesMessage.SEVERITY_INFO, resultado?"Eliminado con exito":"Error", null);
                FacesContext.getCurrentInstance().addMessage(null, msj);
                 limpiar();
                 
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,e.getMessage(),e);
        }
    }

    
    
    /**
     * Creates a new instance of FrmCaso
     */
    public FrmCaso() {
        this.registro=new Caso();
        this.proceso=new Proceso();
        this.solicitud=new Solicitud();
        //this.solicitud.setPendiente(false);
    }

    public LazyDataModel<Caso> getModeloCaso() {
        return modeloCaso;
    }

    public LazyDataModel<Proceso> getModeloProceso() {
        return modeloProceso;
    }

    public LazyDataModel<Solicitud> getModeloSolicitud() {
        return modeloSolicitud;
    }
    public void setModeloCaso(LazyDataModel<Caso> modeloCaso) {
        this.modeloCaso = modeloCaso;
    }
    public void setModeloProceso(LazyDataModel<Proceso> modeloProceso) {
        this.modeloProceso = modeloProceso;
    }
    public void setModeloSolicitud(LazyDataModel<Solicitud> modeloSolicitud) {
        this.modeloSolicitud = modeloSolicitud;
    }


    public Caso getRegistro() {
        return registro;
    }

    public void setRegistro (Caso registro) {
        this.registro = registro;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
        this.solicitud.setPendiente(false);
    }

    public List<Solicitud> getSolicituds() {
        return Solicituds;
    }

    public void setSolicituds(List<Solicitud> solicituds) {
        this.Solicituds = solicituds;
    }
    public Proceso getProceso() {
        return proceso;
    }

    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
    }

    public List<Proceso> getProcesos() {
        return Procesos;
    }

    public void setProcesos(List<Proceso> procesos) {
        this.Procesos = procesos;
    }
    
    public boolean isEditar() {
        return editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
    }
    
}

