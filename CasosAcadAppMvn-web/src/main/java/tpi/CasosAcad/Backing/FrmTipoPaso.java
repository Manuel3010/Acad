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
import tpi.CasosAcad.Entidades.TipoPaso;
//import tpi.CasosAcad.Entidades.TipoRequisito;
import tpi.CasosAcad.Sessions.TipoPasoFacadeLocal;

/**
 *
 * @author manuel
 */
@Named(value = "frmTipoPaso")
@ViewScoped
public class FrmTipoPaso implements Serializable {
    
    private LazyDataModel<TipoPaso> modelo;
   
    @EJB
    private TipoPasoFacadeLocal tpfl;
    
    private TipoPaso registro;// = new TipoPaso();
    private boolean editar=false;
    
    
    @PostConstruct
    public void init(){
        //this.registro=new TipoPaso();
        
         setModelo(new LazyDataModel<TipoPaso>(){

            @Override
            public List<TipoPaso> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                List salida = new ArrayList();
                if(tpfl != null){
                    this.setRowCount(tpfl.count());
                    int[] rango = new int[2];
                    rango[0] = first;
                    rango[1] = pageSize;
                    salida = tpfl.findRange(rango);
                }
                return salida;
            }

            @Override
            public Object getRowKey(TipoPaso object) {
                return object.getIdTipoPaso();
            }

            @Override
            public TipoPaso getRowData(String rowKey) {
                if(this.getWrappedData()!=null){
                    List<TipoPaso> lista = (List<TipoPaso>) this.getWrappedData();
                    if(!lista.isEmpty()) {
                        for(TipoPaso get : lista) {
                            if(get.getIdTipoPaso().compareTo(Integer.parseInt(rowKey))==0) {
                                return get;
                            }
                        }
                    }
                }
                return null;
            }       
        });
    
    
    }
    public LazyDataModel<TipoPaso> getModelo() {
        return modelo;
    }

    public void setModelo(LazyDataModel<TipoPaso> modelo) {
        this.modelo = modelo;
    }

    public TipoPaso getRegistro() {
        return registro;
    }

    public void setRegistro(TipoPaso registro) {
        this.registro = registro;
    }
    
    public void limpiar(){
    
        RequestContext.getCurrentInstance().reset(":vistaPaso");
        this.registro= new TipoPaso();
        
    
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
        //    
            if(this.registro != null && this.tpfl != null){
                //this.tipo=new TipoRequisito();
                boolean resultado = this.tpfl.create(registro);
                //this.tipo=new TipoRequisito();
                FacesMessage msj = new FacesMessage(FacesMessage.SEVERITY_INFO, resultado?"Creado con exito":"Error", null);
              //  this.editar = !resultado;
                FacesContext.getCurrentInstance().addMessage(null, msj);
                limpiar();
            }
        } catch (Exception e) {
           Logger.getLogger(getClass().getName()).log(Level.SEVERE,e.getMessage(),e);
        }
     
      }
     
     
      public void btnModificarAction(ActionEvent ae){
        try{
            boolean resultado = this.tpfl.editar(registro); 
            FacesMessage msj = new FacesMessage(FacesMessage.SEVERITY_INFO, resultado?"Modificado con exito":"Error", null);
            //this.editar = !resultado;
            FacesContext.getCurrentInstance().addMessage(null, msj);
            limpiar();
        }catch(Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,e.getMessage(),e);
        }
    }
      
         public void btnEliminarAction(ActionEvent ae) {
        try {
            if(this.registro != null && this.tpfl != null){
                boolean resultado = this.tpfl.remove(registro);
                FacesMessage msj = new FacesMessage(FacesMessage.SEVERITY_INFO, resultado?"Eliminado con exito":"Error", null);
                FacesContext.getCurrentInstance().addMessage(null, msj);
                limpiar();
                editar=false;
            }
        } catch (Exception e) {
        Logger.getLogger(getClass().getName()).log(Level.SEVERE,e.getMessage(),e);
        
        }
    }
    

    /**
     * Creates a new instance of FrmTipoPaso
     */
    public FrmTipoPaso() {
    
    this.registro= new TipoPaso();
    
    }

    public boolean isEditar() {
        return editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
    }
    
}
