/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpi.CasosAcad.Backing;

import java.io.Serializable;
import java.util.ArrayList;
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
import tpi.CasosAcad.Entidades.Paso;
import tpi.CasosAcad.Entidades.PasoRequisito;
import tpi.CasosAcad.Entidades.Requisito;


import tpi.CasosAcad.Sessions.PasoFacadeLocal;
import tpi.CasosAcad.Sessions.PasoRequisitoFacadeLocal;
import tpi.CasosAcad.Sessions.RequisitoFacadeLocal;

/**
 *
 * @author manuel
 */
@Named(value = "frmPasoRequisito")
@ViewScoped
public class FrmPasoRequisito implements Serializable {

    private LazyDataModel<PasoRequisito> modeloPasoRequisito;
    private LazyDataModel<Paso> modeloPaso;
    private LazyDataModel<Requisito> modeloRequisito;
    private PasoRequisito registroPasoRequisito;
    private Paso registroPaso;
    private Requisito registroRequisito;
    private List<Paso> listaPasos;
    private List<Requisito> listaRequisito;
    private boolean editar=false;

   

    public List<Paso> getListaPasos() {
        return listaPasos;
    }

    public void setListaPasos(List<Paso> listaPasos) {
        this.listaPasos = listaPasos;
    }

    public List<Requisito> getListaRequisito() {
        return listaRequisito;
    }

    public void setListaRequisito(List<Requisito> listaRequisito) {
        this.listaRequisito = listaRequisito;
    }
    
    @EJB
    private PasoRequisitoFacadeLocal prfl;
    @EJB
    private PasoFacadeLocal pfl;
    @EJB
    private RequisitoFacadeLocal rfl;
    
     @PostConstruct
    public void init(){
        
             //this.tipos= trfl.findAll();
               this.listaPasos= pfl.findAll();
               this.listaRequisito= rfl.findAll();
             
             
             setModeloPasoRequisito(new LazyDataModel<PasoRequisito>(){

            @Override
            public List<PasoRequisito> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
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
            public Object getRowKey(PasoRequisito object) {
                return object.getIdPasoRequisito();
            }

            @Override
            public PasoRequisito getRowData(String rowKey) {
                if(this.getWrappedData()!=null){
                    List<PasoRequisito> lista = (List<PasoRequisito>) this.getWrappedData();
                    if(!lista.isEmpty()) {
                        for(PasoRequisito get : lista) {
                            if(get.getIdPasoRequisito().compareTo(Integer.parseInt(rowKey))==0) {
                                return get;
                            }
                        }
                    }
                }
                return null;
            }       
        });

             
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
             
              setModeloRequisito(new LazyDataModel<Requisito>(){

            @Override
            public List<Requisito> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                List salida = new ArrayList();
                if(rfl != null){
                    this.setRowCount(rfl.count());
                    int[] rango = new int[2];
                    rango[0] = first;
                    rango[1] = pageSize;
                    salida = rfl.findRange(rango);
                }
                return salida;
            }

            @Override
            public Object getRowKey(Requisito object) {
                return object.getIdRequisito();
            }

            @Override
            public Requisito getRowData(String rowKey) {
                if(this.getWrappedData()!=null){
                    List<Requisito> lista = (List<Requisito>) this.getWrappedData();
                    if(!lista.isEmpty()) {
                        for(Requisito get : lista) {
                            if(get.getIdRequisito().compareTo(Integer.parseInt(rowKey))==0) {
                                return get;
                            }
                        }
                    }
                }
                return null;
            }       
        });
    }
    
    public Integer getPasoSeleccionado(){
     if(registroPasoRequisito!= null){
            if(registroPasoRequisito.getIdPaso()!= null){
                return this.registroPasoRequisito.getIdPaso().getIdPaso();
            } else {
                return null;
            }         
        } else {
            return null;
        }
    }
    
    public void setPasoSeleccionado(Integer idPaso){
        if(idPaso >= 0 && !this.listaPasos.isEmpty()){
            for(Paso pe : this.getListaPasos()) {
                if(Objects.equals(pe.getIdPaso(), idPaso)) {
                    if(this.registroPasoRequisito.getIdPaso() != null) {
                        this.registroPasoRequisito.getIdPaso().setIdPaso(idPaso);
                    } else {
                        this.registroPasoRequisito.setIdPaso(pe);
                    }
                }
            }
        }
    
    }
    
    public Integer getRequisitoSeleccionado(){
     if(registroPasoRequisito!= null){
            if(registroPasoRequisito.getIdRequisito()!= null){
                return this.registroPasoRequisito.getIdRequisito().getIdRequisito();
            } else {
                return null;
            }         
        } else {
            return null;
        }
    }
    
    public void setRequisitoSeleccionado(Integer idRequisito){
        if(idRequisito >= 0 && !this.listaRequisito.isEmpty()){
            for(Requisito re : this.getListaRequisito()) {
                if(Objects.equals(re.getIdRequisito(), idRequisito)) {
                    if(this.registroPasoRequisito.getIdRequisito() != null) {
                        this.registroPasoRequisito.getIdRequisito().setIdRequisito(idRequisito);
                    } else {
                        this.registroPasoRequisito.setIdRequisito(re);
                    }
                }
            }
        }
    
    }
    
    public void limpiar(){
      
        RequestContext.getCurrentInstance().reset(":tabViewPasoRequisito:pasoRequisitoForm");
        this.registroPasoRequisito= new PasoRequisito();
    }
    
    
    
    
    public void btnNuevoAction(ActionEvent ae){
    
    try{
    
    limpiar();
    }catch(Exception e){
    Logger.getLogger(getClass().getName()).log(Level.SEVERE,e.getMessage(),e);
      
    
    }
    
    
    
    }
    
    
    
    
    public void btnGuardarAction(ActionEvent ae){
           
    try{
       if(this.registroPasoRequisito != null && this.prfl != null){
                boolean resultado = this.prfl.create(registroPasoRequisito);
                FacesMessage msj = new FacesMessage(FacesMessage.SEVERITY_INFO, resultado?"Creado con exito":"Error", null);
                //this.agregar = !resultado;
                FacesContext.getCurrentInstance().addMessage(null, msj);}
                limpiar(); 
    
    }catch(Exception e){
      Logger.getLogger(getClass().getName()).log(Level.SEVERE,e.getMessage(),e);
            
            
    }    
    
    }   
    
    
        public void btnModificarAction(ActionEvent ae){
        try{
            boolean resultado = this.prfl.editar(registroPasoRequisito); 
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
            if(this.registroPasoRequisito != null && this.prfl != null){
                boolean resultado = this.prfl.remove(registroPasoRequisito);
                //editar=!resultado;
                FacesMessage msj = new FacesMessage(FacesMessage.SEVERITY_INFO, resultado?"Eliminado con exito":"Error", null);
                FacesContext.getCurrentInstance().addMessage(null, msj);
                limpiar();
                
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,e.getMessage(),e);
        }
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * Creates a new instance of FrmPasoRequisito
     */
    public FrmPasoRequisito() {
    
    this.registroPasoRequisito=new PasoRequisito();
    
    }

    public LazyDataModel<PasoRequisito> getModeloPasoRequisito() {
        return modeloPasoRequisito;
    }

    public void setModeloPasoRequisito(LazyDataModel<PasoRequisito> modeloPasoRequisito) {
        this.modeloPasoRequisito = modeloPasoRequisito;
    }

    public LazyDataModel<Paso> getModeloPaso() {
        return modeloPaso;
    }

    public void setModeloPaso(LazyDataModel<Paso> modeloPaso) {
        this.modeloPaso = modeloPaso;
    }

    public LazyDataModel<Requisito> getModeloRequisito() {
        return modeloRequisito;
    }

    public void setModeloRequisito(LazyDataModel<Requisito> modeloRequisito) {
        this.modeloRequisito = modeloRequisito;
    }

    public PasoRequisito getRegistroPasoRequisito() {
        return registroPasoRequisito;
    }

    public void setRegistroPasoRequisito(PasoRequisito registroPasoRequisito) {
        this.registroPasoRequisito = registroPasoRequisito;
    }

    public Paso getRegistroPaso() {
        return registroPaso;
    }

    public void setRegistroPaso(Paso registroPaso) {
        this.registroPaso = registroPaso;
    }

    public Requisito getRegistroRequisito() {
        return registroRequisito;
    }

    public void setRegistroRequisito(Requisito registroRequisito) {
        this.registroRequisito = registroRequisito;
    }

    /**
     * @return the editar
     */
    public boolean isEditar() {
        return editar;
    }

    /**
     * @param editar the editar to set
     */
    public void setEditar(boolean editar) {
        this.editar = editar;
    }

  
    
}
