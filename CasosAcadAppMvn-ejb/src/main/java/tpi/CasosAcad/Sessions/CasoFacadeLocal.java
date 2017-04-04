/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpi.CasosAcad.Sessions;

import java.util.List;
import javax.ejb.Local;
import tpi.CasosAcad.Entidades.Caso;

/**
 *
 * @author manuel
 */
@Local
public interface CasoFacadeLocal {

    boolean create(Caso caso);

    //void edit(Caso caso);
    
    boolean editar(Caso caso);
    
    boolean remove(Caso caso);

    Caso find(Object id);

    List<Caso> findAll();

    List<Caso> findRange(int[] range);

    int count();
    
}
