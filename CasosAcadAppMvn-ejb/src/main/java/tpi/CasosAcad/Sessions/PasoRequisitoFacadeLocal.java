/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpi.CasosAcad.Sessions;

import java.util.List;
import javax.ejb.Local;
import tpi.CasosAcad.Entidades.PasoRequisito;

/**
 *
 * @author manuel
 */
@Local
public interface PasoRequisitoFacadeLocal {

    boolean create(PasoRequisito pasoRequisito);

    //void edit(PasoRequisito pasoRequisito);

    boolean editar(PasoRequisito pasoRequisito);
    
    boolean remove(PasoRequisito pasoRequisito);

    PasoRequisito find(Object id);

    List<PasoRequisito> findAll();
    
  //  List<PasoRequisito> findByIdPaso(); ////////////

    List<PasoRequisito> findRange(int[] range);

    int count();
    
}
