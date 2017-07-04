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
import tpi.CasosAcad.Entidades.CasoDetalle;
import tpi.CasosAcad.Entidades.Caso;
import tpi.CasosAcad.Sessions.CasoDetalleFacadeLocal;
import tpi.CasosAcad.Sessions.CasoFacadeLocal;

/**
 *
 * @author manuel
 */
@Named(value = "frmCasoDetalle")
@ViewScoped
public class FrmCasoDetalle implements Serializable {

    private LazyDataModel<CasoDetalle> modeloCasoDetalle;
    private LazyDataModel<Caso> modeloCaso;
    private CasoDetalle registro; //= new Requisito();
    private Caso caso;
    private List<Caso> casos;
    private boolean editar=false;    
    
    @EJB
    private CasoDetalleFacadeLocal cdfl;
    @EJB
    private CasoFacadeLocal cfl;
    
    
    @PostConstruct
    public void init(){
        
             this.casos= cfl.findAll();
        
             setModeloCasoDetalle(new LazyDataModel<CasoDetalle>(){

            @Override
            public List<CasoDetalle> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                List salida = new ArrayList();
                if(cdfl != null){
                    this.setRowCount(cdfl.count());
                    int[] rango = new int[2];
                    rango[0] = first;
                    rango[1] = pageSize;
                    salida = cdfl.findRange(rango);
                }
                return salida;
            }

            @Override
            public Object getRowKey(CasoDetalle object) {
                return object.getIdCasoDetalle();
            }

            @Override
            public CasoDetalle getRowData(String rowKey) {
                if(this.getWrappedData()!=null){
                    List<CasoDetalle> lista = (List<CasoDetalle>) this.getWrappedData();
                    if(!lista.isEmpty()) {
                        for(CasoDetalle get : lista) {
                            if(get.getIdCasoDetalle().compareTo(Integer.parseInt(rowKey))==0) {
                                return get;
                            }
                        }
                    }
                }
                return null;
            }       
        });

             
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
        RequestContext.getCurrentInstance().reset(":tabViewCasoDetalle:edAddCasoDetalle");
        this.registro=new CasoDetalle();
    }
    
     public Integer getCasoSeleccionado(){
     if(registro!= null){
            if(registro.getIdCaso()!= null){
                return this.registro.getIdCaso().getIdCaso();
            } else {
                return null;
            }         
        } else {
            return null;
        }
    }
    
    public void setCasoSeleccionado(Integer idCaso){
        if(idCaso >= 0 && !this.casos.isEmpty()){
            for(Caso tre : this.getCasos()) {
                if(Objects.equals(tre.getIdCaso(), idCaso)) {
                    if(this.registro.getIdCaso() != null) {
                        this.registro.getIdCaso().setIdCaso(idCaso);
                    } else {
                        this.registro.setIdCaso(tre);
                    }
                }
            }
        }
    
    }
    
    
          public void btnGuardarAction(ActionEvent ae){
        try {    
            if(this.registro != null && this.cdfl != null){
                boolean resultado = this.cdfl.create(registro);
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
            boolean resultado = this.cdfl.editar(registro); 
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
            if(this.registro != null && this.cdfl != null){
                boolean resultado = this.cdfl.remove(registro);
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
     * Creates a new instance of FrmRequisito
     */
    public FrmCasoDetalle() {
        this.registro=new CasoDetalle();
    }

    public LazyDataModel<CasoDetalle> getModeloCasoDetalle() {
        return modeloCasoDetalle;
    }

    public void setModeloCasoDetalle(LazyDataModel<CasoDetalle> modeloCasoDetalle) {
        this.modeloCasoDetalle = modeloCasoDetalle;
    }

    public LazyDataModel<Caso> getModeloCaso() {
        return modeloCaso;
    }

    public void setModeloCaso(LazyDataModel<Caso> caso) {
        this.modeloCaso = caso;
    }

    public CasoDetalle getCasoDetalle() {
        return registro;
    }

    public void setCasoDetalle(CasoDetalle registro) {
        this.registro = registro;
    }

    
    
    public Caso getCaso() {
        return caso;
    }

    public void setCaso(Caso tipo) {
        this.caso = tipo;
    }

    public List<Caso> getCasos() {
        return casos;
    }

    public void setCasos(List<Caso> tipos) {
        this.casos = tipos;
    }

    public boolean isEditar() {
        return editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
    }
    
}
