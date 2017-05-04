
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
import tpi.CasosAcad.Entidades.Requisito;
import tpi.CasosAcad.Entidades.TipoRequisito;
import tpi.CasosAcad.Sessions.RequisitoFacadeLocal;
import tpi.CasosAcad.Sessions.TipoRequisitoFacadeLocal;

/**
 *
 * @author manuel
 */
@Named(value = "frmRequisito")
@ViewScoped
public class FrmRequisito implements Serializable {

    private LazyDataModel<Requisito> modeloRequisito;
    private LazyDataModel<TipoRequisito> modeloTipo;
    private Requisito registro; //= new Requisito();
    private TipoRequisito tipo;
    private List<TipoRequisito> tipos;

    public TipoRequisito getTipo() {
        return tipo;
    }

    public void setTipo(TipoRequisito tipo) {
        this.tipo = tipo;
    }

    public List<TipoRequisito> getTipos() {
        return tipos;
    }

    public void setTipos(List<TipoRequisito> tipos) {
        this.tipos = tipos;
    }
    private boolean editar=false;    
    
    @EJB
    private RequisitoFacadeLocal rfl;
    @EJB
    private TipoRequisitoFacadeLocal trfl;
    
    
    @PostConstruct
    public void init(){
        
             this.tipos= trfl.findAll();
        
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

             
             setModeloTipo(new LazyDataModel<TipoRequisito>(){

            @Override
            public List<TipoRequisito> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                List salida = new ArrayList();
                if(trfl != null){
                    this.setRowCount(trfl.count());
                    int[] rango = new int[2];
                    rango[0] = first;
                    rango[1] = pageSize;
                    salida = trfl.findRange(rango);
                }
                return salida;
            }

            @Override
            public Object getRowKey(TipoRequisito object) {
                return object.getIdTipoRequisito();
            }

            @Override
            public TipoRequisito getRowData(String rowKey) {
                if(this.getWrappedData()!=null){
                    List<TipoRequisito> lista = (List<TipoRequisito>) this.getWrappedData();
                    if(!lista.isEmpty()) {
                        for(TipoRequisito get : lista) {
                            if(get.getIdTipoRequisito().compareTo(Integer.parseInt(rowKey))==0) {
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
        RequestContext.getCurrentInstance().reset(":tabViewRequisito:edAddRequisito");
        this.registro=new Requisito();
    }
    
     public Integer getTipoSeleccionado(){
     if(registro!= null){
            if(registro.getIdTipoRequisito()!= null){
                return this.registro.getIdTipoRequisito().getIdTipoRequisito();
            } else {
                return null;
            }         
        } else {
            return null;
        }
    }
    
    public void setTipoSeleccionado(Integer idTipo){
        if(idTipo >= 0 && !this.tipos.isEmpty()){
            for(TipoRequisito tre : this.getTipos()) {
                if(Objects.equals(tre.getIdTipoRequisito(), idTipo)) {
                    if(this.registro.getIdTipoRequisito() != null) {
                        this.registro.getIdTipoRequisito().setIdTipoRequisito(idTipo);
                    } else {
                        this.registro.setIdTipoRequisito(tre);
                    }
                }
            }
        }
    
    }
    
    
          public void btnGuardarAction(ActionEvent ae){
        try {    
            if(this.registro != null && this.rfl != null){
                boolean resultado = this.rfl.create(registro);
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
            boolean resultado = this.rfl.editar(registro); 
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
            if(this.registro != null && this.rfl != null){
                boolean resultado = this.rfl.remove(registro);
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
    public FrmRequisito() {
        this.registro=new Requisito();
    }

    public LazyDataModel<Requisito> getModeloRequisito() {
        return modeloRequisito;
    }

    public void setModeloRequisito(LazyDataModel<Requisito> modeloRequisito) {
        this.modeloRequisito = modeloRequisito;
    }

    public LazyDataModel<TipoRequisito> getModeloTipo() {
        return modeloTipo;
    }

    public void setModeloTipo(LazyDataModel<TipoRequisito> modeloTipo) {
        this.modeloTipo = modeloTipo;
    }

    public Requisito getRegistro() {
        return registro;
    }

    public void setRegistro(Requisito registro) {
        this.registro = registro;
    }

    
    
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
