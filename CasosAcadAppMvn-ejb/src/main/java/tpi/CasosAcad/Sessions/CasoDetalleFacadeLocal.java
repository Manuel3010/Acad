/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpi.CasosAcad.Sessions;

import java.util.List;
import javax.ejb.Local;
import tpi.CasosAcad.Entidades.CasoDetalle;

/**
 *
 * @author manuel
 */
@Local
public interface CasoDetalleFacadeLocal {

    boolean create(CasoDetalle casoDetalle);

    //void edit(CasoDetalle casoDetalle);
    
    boolean editar(CasoDetalle casoDetalle);
    
    boolean remove(CasoDetalle casoDetalle);

    CasoDetalle find(Object id);

    List<CasoDetalle> findAll();

    List<CasoDetalle> findRange(int[] range);

    int count();
    
}
