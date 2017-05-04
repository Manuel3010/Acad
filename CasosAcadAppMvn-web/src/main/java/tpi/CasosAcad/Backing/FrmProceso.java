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
import tpi.CasosAcad.Entidades.Proceso;
import tpi.CasosAcad.Sessions.ProcesoFacadeLocal;

/**
 *
 * @author manuel/sigfrid
 */
@Named(value = "frmProceso")
@ViewScoped
public class FrmProceso implements Serializable {
    
    private LazyDataModel<Proceso> modelo;
   
    @EJB
    private ProcesoFacadeLocal pfl;
    
    private Proceso registro;
    private boolean editar=false;
    
    
    @PostConstruct
    public void init(){
    
        
         setModelo(new LazyDataModel<Proceso>(){

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
    public LazyDataModel<Proceso> getModelo() {
        return modelo;
    }

    public void setModelo(LazyDataModel<Proceso> modelo) {
        this.modelo = modelo;
    }

    public Proceso getRegistro() {
        return registro;
    }

    public void setRegistro(Proceso registro) {
        this.registro = registro;
        
    }
    
    public void limpiar(){
    
        RequestContext.getCurrentInstance().reset(":vistaProceso");
        this.registro= new Proceso();
        
    
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
          
            if(this.registro != null && this.pfl != null){
                
                boolean resultado = this.pfl.create(registro);
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
            boolean resultado = this.pfl.editar(registro); 
            FacesMessage msj = new FacesMessage(FacesMessage.SEVERITY_INFO, resultado?"Modificado con exito":"Error", null);
           
            FacesContext.getCurrentInstance().addMessage(null, msj);
            limpiar();
        }catch(Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,e.getMessage(),e);
        }
    }
      
         public void btnEliminarAction(ActionEvent ae) {
        try {
            if(this.registro != null && this.pfl != null){
                boolean resultado = this.pfl.remove(registro);
                FacesMessage msj = new FacesMessage(FacesMessage.SEVERITY_INFO, resultado?"Eliminado con exito":"Error", null);
                FacesContext.getCurrentInstance().addMessage(null, msj);
                limpiar();
                editar=false;
            }
        } catch (Exception e) {
        Logger.getLogger(getClass().getName()).log(Level.SEVERE,e.getMessage(),e);
        
        }
    }
    

    

    public FrmProceso() {
    
    this.registro= new Proceso();
    
    }

    public boolean isEditar() {
        return editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
    }
    
}