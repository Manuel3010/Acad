/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpi.CasosAcad.Sessions;

import java.util.List;
import javax.ejb.Local;
import tpi.CasosAcad.Entidades.Proceso;

/**
 *
 * @author manuel
 */
@Local
public interface ProcesoFacadeLocal {

    boolean create(Proceso proceso);

    //void edit(Proceso proceso);

    boolean editar(Proceso proceso);
    
    boolean remove(Proceso proceso);

    Proceso find(Object id);

    List<Proceso> findAll();

    List<Proceso> findRange(int[] range);

    int count();
    
}
