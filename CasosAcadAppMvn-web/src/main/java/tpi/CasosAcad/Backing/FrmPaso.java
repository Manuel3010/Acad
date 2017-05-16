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
//import tpi.CasosAcad.Entidades.Requisito;
import tpi.CasosAcad.Entidades.TipoPaso;
//import tpi.CasosAcad.Entidades.TipoRequisito;
//import tpi.CasosAcad.Entidades.TipoRequisito;
import tpi.CasosAcad.Sessions.PasoFacadeLocal;
import tpi.CasosAcad.Sessions.PasoRequisitoFacadeLocal;
import tpi.CasosAcad.Sessions.TipoPasoFacadeLocal;

/**
 *
 * @author manuel
 */
@Named(value = "frmPaso")
@ViewScoped
public class FrmPaso implements Serializable{

    private LazyDataModel<Paso> modeloPaso;
     
    private Paso registroP;// =new Paso();
    private PasoRequisito Reg;
    private List<TipoPaso> tipos;
    private boolean editar= false;
    
    @EJB
    private PasoFacadeLocal pfl;
    @EJB
    private TipoPasoFacadeLocal tpfl;
    @EJB
    private PasoRequisitoFacadeLocal prfl;
    
    @PostConstruct
    public void init(){
        
    this.tipos= tpfl.findAll();
    
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
    }
    
    public Integer getTipoSeleccionado(){
     if(registroP!= null){
            if(registroP.getIdTipoPaso()!= null){
                return this.registroP.getIdTipoPaso().getIdTipoPaso();
            } else {
                return null;
            }         
        } else {
            return null;
        }
    }
    
    public void setTipoSeleccionado(Integer idTipo){
        if(idTipo >= 0 && !this.tipos.isEmpty()){
            for(TipoPaso tpe : this.getTipos()) {
                if(Objects.equals(tpe.getIdTipoPaso(), idTipo)) {
                    if(this.registroP.getIdTipoPaso() != null) {
                        this.registroP.getIdTipoPaso().setIdTipoPaso(idTipo);
                    } else {
                        this.registroP.setIdTipoPaso(tpe);
                    }
                }
            }
        }
    
    }
    
     
     
    
    
    
    
    public List<PasoRequisito> getObtenerRequisitos(){
      List salida=new ArrayList();
      try{
      
          
          salida= prfl.findByIdPaso(this.getRegistroP().getIdPaso());
          
          
          
      
      }catch(Exception e){
         Logger.getLogger(getClass().getName()).log(Level.SEVERE,e.getMessage(),e);
      }
      
      
    
      return salida;
    }
    
    
    
    
 
     public void cambioTabla(){
        this.editar = true;
    }
    
    public void limpiar(){
      
        RequestContext.getCurrentInstance().reset(":tabViewPaso:edAddPaso");
        this.registroP= new Paso();
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
            if(this.registroP != null && this.pfl != null){
                boolean resultado = this.pfl.create(registroP);
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
            boolean resultado = this.pfl.editar(registroP); 
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
            if(this.registroP != null && this.pfl != null){
                boolean resultado = this.pfl.remove(registroP);
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
     * Creates a new instance of FrmPaso
     */
    public FrmPaso() {
        this.registroP=new Paso();
    }

    public LazyDataModel<Paso> getModeloPaso() {
        return modeloPaso;
    }

    public void setModeloPaso(LazyDataModel<Paso> modeloPaso) {
        this.modeloPaso = modeloPaso;
    }
    

    public Paso getRegistro() {
        return registroP;
    }

    public void setRegistro(Paso registro) {
        this.registroP = registro;
    }

    public List<TipoPaso> getTipos() {
        return tipos;
    }

    public void setTipos(List<TipoPaso> tipos) {
        this.tipos = tipos;
    }

    public boolean isEditar() {
        return editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
    }

    public Paso getRegistroP() {
        return registroP;
    }

    public void setRegistroP(Paso registroP) {
        this.registroP = registroP;
    }

    public PasoRequisito getReg() {
        return Reg;
    }

    public void setReg(PasoRequisito Reg) {
        this.Reg = Reg;
    }
    
}
