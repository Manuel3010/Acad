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

    
    
    @PostConstruct
    public void init(){
         
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
    
    
    
    public TipoRequisito getTipo() {
        return tipo;
    }

    public void setTipo(TipoRequisito tipo) {
        this.tipo = tipo;
    }
    

    public LazyDataModel<TipoRequisito> getModelo() {
        return modelo;
    }

    public void setModelo(LazyDataModel<TipoRequisito> modelo) {
        this.modelo = modelo;
    }
    
    /**
     * Creates a new instance of FrmTipoRequisito
     */
    public FrmTipoRequisito() {
    }
    
}
