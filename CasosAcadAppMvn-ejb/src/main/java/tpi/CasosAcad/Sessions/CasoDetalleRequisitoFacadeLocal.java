/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpi.CasosAcad.Sessions;

import java.util.List;
import javax.ejb.Local;
import tpi.CasosAcad.Entidades.CasoDetalleRequisito;

/**
 *
 * @author manuel
 */
@Local
public interface CasoDetalleRequisitoFacadeLocal {

    boolean create(CasoDetalleRequisito casoDetalleRequisito);

    //void edit(CasoDetalleRequisito casoDetalleRequisito);

    boolean editar(CasoDetalleRequisito casoDetalleRequisito);
    
    boolean remove(CasoDetalleRequisito casoDetalleRequisito);

    CasoDetalleRequisito find(Object id);

    List<CasoDetalleRequisito> findAll();

    List<CasoDetalleRequisito> findRange(int[] range);

    int count();
    
}
