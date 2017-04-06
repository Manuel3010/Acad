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
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
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
    private Requisito registro;
    private TipoRequisito tipo;
    
    
    @EJB
    private RequisitoFacadeLocal rfl;
   /* @EJB
    private TipoRequisitoFacadeLocal trfl;
    */
    
    @PostConstruct
    public void init(){
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
    
    
    
    /**
     * Creates a new instance of FrmRequisito
     */
    public FrmRequisito() {
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

    public TipoRequisito getTipo() {
        return tipo;
    }

    public void setTipo(TipoRequisito tipo) {
        this.tipo = tipo;
    }
    
}
