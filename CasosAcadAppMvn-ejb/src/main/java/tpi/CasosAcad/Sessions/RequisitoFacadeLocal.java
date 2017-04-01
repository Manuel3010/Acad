/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpi.CasosAcad.Sessions;

import java.util.List;
import javax.ejb.Local;
import tpi.CasosAcad.Entidades.Requisito;

/**
 *
 * @author manuel
 */
@Local
public interface RequisitoFacadeLocal {

    void create(Requisito requisito);

    void edit(Requisito requisito);

    void remove(Requisito requisito);

    Requisito find(Object id);

    List<Requisito> findAll();

    List<Requisito> findRange(int[] range);

    int count();
    
}
