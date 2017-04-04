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
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import tpi.CasosAcad.Entidades.TipoRequisito;
import tpi.CasosAcad.Sessions.TipoRequisitoFacadeLocal;

/**
 *
 * @author manuel
 */
@Named(value = "frmTipoRequisito")
@ViewScoped
public class FrmTipoRequisito implements Serializable {

    private LazyDataModel<TipoRequisito> modelo;
    @EJB
    private TipoRequisitoFacadeLocal trfl;
    private TipoRequisito tipo;
    private boolean editar;
    private boolean agregar;
    public TipoRequisito getTipo() {
        return tipo;
    }

    public void setTipo(TipoRequisito tipo) {
        this.tipo = tipo;
    }

    
    @PostConstruct
    public void init(){
        this.tipo=new TipoRequisito();
        
         setModelo(new LazyDataModel<TipoRequisito>(){

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
    
    
     public void btnNuevoAction(ActionEvent ae) {
       //  this.agregar= true;
         //this.editar = false;
        // this.tipo= new TipoRequisito();
        //this.agregar= true;
        try{
            this.tipo = new TipoRequisito();
        }catch(Exception e){
            
        }
    }
     
     
      public void btnGuardarAction(ActionEvent ae){
        try {
        //    
            if(this.tipo != null && this.trfl != null){
                //this.tipo=new TipoRequisito();
                boolean resultado = this.trfl.create(tipo);
                //this.tipo=new TipoRequisito();
                FacesMessage msj = new FacesMessage(FacesMessage.SEVERITY_INFO, resultado?"Creado con exito":"Error", null);
                this.agregar = !resultado;
                FacesContext.getCurrentInstance().addMessage(null, msj);
            }
        } catch (Exception e) {
           
        }
     
      }
     
     
      public void btnModificarAction(ActionEvent ae){
        try{
            boolean resultado = this.trfl.editar(tipo); 
            FacesMessage msj = new FacesMessage(FacesMessage.SEVERITY_INFO, resultado?"Modificado con exito":"Error", null);
            this.editar = resultado;
            FacesContext.getCurrentInstance().addMessage(null, msj);
        }catch(Exception e){
            System.err.println(""+e);
        }
    }
      
      public void guardar(){
      
      try {
        //    this.tipo=new TipoRequisito();
            if(this.tipo != null && this.trfl != null){
                boolean resultado = this.trfl.create(tipo);
                this.tipo=new TipoRequisito();
                FacesMessage msj = new FacesMessage(FacesMessage.SEVERITY_INFO, resultado?"Creado con exito":"Error", null);
                this.agregar = !resultado;
                FacesContext.getCurrentInstance().addMessage(null, msj);
            }
        } catch (Exception e) {
            FacesMessage msj = new FacesMessage(FacesMessage.SEVERITY_FATAL, e.getMessage(), null);
            FacesContext.getCurrentInstance().addMessage(null, msj);
        }
      }
       
    
    
        public void btnEliminarAction(ActionEvent ae) {
        try {
            if(this.tipo != null && this.trfl != null){
                boolean resultado = this.trfl.remove(tipo);
                FacesMessage msj = new FacesMessage(FacesMessage.SEVERITY_INFO, resultado?"Eliminado con exito":"Error", null);
                FacesContext.getCurrentInstance().addMessage(null, msj);
            }
        } catch (Exception e) {
        }
    }

    
     public void cambioTabla(){
        this.editar = true;
    }
     
     public void agregarNuevo(){
     //this.agregar= true;
     //this.editar= false;
      this.tipo= new TipoRequisito();
     
     }
    
    
    

    public LazyDataModel<TipoRequisito> getModelo() {
        return modelo;
    }

    public void setModelo(LazyDataModel<TipoRequisito> modelo) {
        this.modelo = modelo;
    }

public boolean isEditar() {
        return editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
    }

    public boolean isAgregar() {
        return agregar;
    }

    public void setAgregar(boolean agregar) {
        this.agregar = agregar;
    }    
    /**
     * Creates a new instance of FrmTipoRequisito
     */
    public FrmTipoRequisito() {
    }
    
}