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
import tpi.CasosAcad.Entidades.TipoPaso;
import tpi.CasosAcad.Entidades.TipoRequisito;
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
    private TipoPaso registro;
    
    
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
    
    
    

    /**
     * Creates a new instance of FrmTipoPaso
     */
    public FrmTipoPaso() {
    }
    
}
