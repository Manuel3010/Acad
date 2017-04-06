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
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import tpi.CasosAcad.Entidades.Paso;
//import tpi.CasosAcad.Entidades.Requisito;
import tpi.CasosAcad.Entidades.TipoPaso;
//import tpi.CasosAcad.Entidades.TipoRequisito;
//import tpi.CasosAcad.Entidades.TipoRequisito;
import tpi.CasosAcad.Sessions.PasoFacadeLocal;
import tpi.CasosAcad.Sessions.TipoPasoFacadeLocal;

/**
 *
 * @author manuel
 */
@Named(value = "frmPaso")
@ViewScoped
public class FrmPaso implements Serializable{

    private LazyDataModel<Paso> modeloPaso;
     
    private Paso registroP =new Paso();
    private List<TipoPaso> tipos;
    
    @EJB
    private PasoFacadeLocal pfl;
    @EJB
    private TipoPasoFacadeLocal tpfl;
    
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
    
    
          public void btnGuardarAction(ActionEvent ae){
        try {    
            if(this.registroP != null && this.pfl != null){
                boolean resultado = this.pfl.create(registroP);
                FacesMessage msj = new FacesMessage(FacesMessage.SEVERITY_INFO, resultado?"Creado con exito":"Error", null);
                //this.agregar = !resultado;
                FacesContext.getCurrentInstance().addMessage(null, msj);
            }
        } catch (Exception e) {
           
        }
     
      }
     
     
      public void btnModificarAction(ActionEvent ae){
        try{
            boolean resultado = this.pfl.editar(registroP); 
            FacesMessage msj = new FacesMessage(FacesMessage.SEVERITY_INFO, resultado?"Modificado con exito":"Error", null);
            //this.editar = resultado;
            FacesContext.getCurrentInstance().addMessage(null, msj);
        }catch(Exception e){
            System.err.println(""+e);
        }
    }

            public void btnEliminarAction(ActionEvent ae) {
        try {
            if(this.registroP != null && this.pfl != null){
                boolean resultado = this.pfl.remove(registroP);
                FacesMessage msj = new FacesMessage(FacesMessage.SEVERITY_INFO, resultado?"Eliminado con exito":"Error", null);
                FacesContext.getCurrentInstance().addMessage(null, msj);
            }
        } catch (Exception e) {
        }
    }

    
    /**
     * Creates a new instance of FrmPaso
     */
    public FrmPaso() {
        
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
    
}
