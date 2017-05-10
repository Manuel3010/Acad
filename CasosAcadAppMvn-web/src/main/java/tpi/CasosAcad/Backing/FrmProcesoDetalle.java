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
import tpi.CasosAcad.Entidades.ProcesoDetalle;
import tpi.CasosAcad.Entidades.Paso;
import tpi.CasosAcad.Sessions.PasoFacadeLocal;
import tpi.CasosAcad.Sessions.ProcesoDetalleFacadeLocal;
import tpi.CasosAcad.Sessions.ProcesoFacadeLocal;

/**
 *
 * @author manuel
 */
@Named(value = "frmProcesoDetalle")
@ViewScoped
public class FrmProcesoDetalle implements Serializable {

    private LazyDataModel<Paso> modeloPaso;
    private LazyDataModel<Proceso> modeloProceso;
    private LazyDataModel<ProcesoDetalle> modeloProcesoDetalle;
    
    private Paso paso; //= new Requisito();
    private ProcesoDetalle registro; //proceso detalle tipo
    private Proceso proceso;
    private List<ProcesoDetalle> ProcesoDetalles; // proceso detalles tipos
    private List<Proceso> Procesos;
    private boolean editar=false;    
    
    @EJB
    private PasoFacadeLocal pfl;
    @EJB
    private ProcesoDetalleFacadeLocal pdfl; //sfl
    @EJB
    private ProcesoFacadeLocal prfl;
    
    
    @PostConstruct
    public void init(){
        
             this.ProcesoDetalles= pdfl.findAll();
             this.Procesos= prfl.findAll();
        
             setModeloPaso(new LazyDataModel<Paso>(){

            @Override
            public List<Paso> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
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
            public Object getRowKey(Paso object) {
                return object.getIdPaso();
            }

            @Override
            public Paso getRowData(String rowKey) {
                if(this.getWrappedData()!=null){
                    List<Paso> lista = (List<Paso>) this.getWrappedData();
                    if(!lista.isEmpty()) {
                        for(Paso get : lista) {
                            if(get.getIdPaso().compareTo(Integer.parseInt(rowKey))==0) {
                                return get;
                            }
                        }
                    }
                }
                return null;
            }       
        });

             
             setModeloSolicitud(new LazyDataModel<ProcesoDetalle>(){

            @Override
            public List<ProcesoDetalle> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                List salida = new ArrayList();
                if(pdfl != null){
                    this.setRowCount(pdfl.count());
                    int[] rango = new int[2];
                    rango[0] = first;
                    rango[1] = pageSize;
                    salida = pdfl.findRange(rango);
                }
                return salida;
            }

            @Override
            public Object getRowKey(ProcesoDetalle object) {
                return object.getIdProcesoDetalle();
            }

            @Override
            public ProcesoDetalle getRowData(String rowKey) {
                if(this.getWrappedData()!=null){
                    List<ProcesoDetalle> lista = (List<ProcesoDetalle>) this.getWrappedData();
                    if(!lista.isEmpty()) {
                        for(ProcesoDetalle get : lista) {
                            if(get.getIdProcesoDetalle().compareTo(Integer.parseInt(rowKey))==0) {
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
                if(prfl != null){
                    this.setRowCount(prfl.count());
                    int[] rango = new int[2];
                    rango[0] = first;
                    rango[1] = pageSize;
                    salida = prfl.findRange(rango);
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
        RequestContext.getCurrentInstance().reset(":tabViewPaso:edAddPaso");
        this.paso=new Paso();
    }
    
//     public Integer getProcesoDetalleSeleccionado(){
//     if(registro!= null){
//            if(registro.getIdProcesoDetalle()!= null){
//                return this.registro.getIdProcesoDetalle().getIdProcesoDetalle();
//            } else {
//                return null;
//            }         
//        } else {
//            return null;
//        }
//    }
//    
//    public void setProcesoDetalleSeleccionado(Integer idProcesoDetalle){
//        if(idProcesoDetalle >= 0 && !this.ProcesoDetalles.isEmpty()){
//            for(ProcesoDetalle tre : this.getProcesoDetalles()) {
//                if(Objects.equals(tre.getIdProcesoDetalle(), idProcesoDetalle)) {
//                    if(this.registro.getIdProcesoDetalle()!= null) {
//                        this.registro.getIdProcesoDetalle().setIdProcesoDetalle(idProcesoDetalle);
//                    } else {
//                        this.registro.setIdProcesoDetalle(tre);
//                    }
//                }
//            }
//        }
//    
//    }
      
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
            if(this.registro != null && this.pfl != null){
                boolean resultado = this.pfl.create(paso);
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
            boolean resultado = this.pfl.editar(paso); 
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
            if(this.paso != null && this.pfl != null){
                boolean resultado = this.pfl.remove(paso);
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
    public FrmProcesoDetalle() {
        this.paso=new Paso();
        this.proceso=new Proceso();
        this.registro=new ProcesoDetalle();
    }

    public LazyDataModel<Paso> getModeloPaso() {
        return modeloPaso;
    }

    public LazyDataModel<Proceso> getModeloProceso() {
        return modeloProceso;
    }

    public LazyDataModel<ProcesoDetalle> getModeloProcesoDetalle() {
        return modeloProcesoDetalle;
    }
    public void setModeloPaso(LazyDataModel<Paso> modeloPaso) {
        this.modeloPaso = modeloPaso;
    }
    public void setModeloProceso(LazyDataModel<Proceso> modeloProceso) {
        this.modeloProceso = modeloProceso;
    }
    public void setModeloSolicitud(LazyDataModel<ProcesoDetalle> modeloProcesoDetalle) {
        this.modeloProcesoDetalle = modeloProcesoDetalle;
    }


    public Paso getPaso() {
        return paso;
    }

    public void setPaso (Paso paso) {
        this.paso = paso;
    }

    public ProcesoDetalle getProcesoDetalle() {
        return registro;
    }

    public void setProcesoDetalle(ProcesoDetalle procesoDetalle) {
        this.registro = procesoDetalle;
    }

    public List<ProcesoDetalle> getProcesoDetalles() {
        return ProcesoDetalles;
    }

    public void setProcesoDetalles(List<ProcesoDetalle> solicituds) {
        this.ProcesoDetalles = solicituds;
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

