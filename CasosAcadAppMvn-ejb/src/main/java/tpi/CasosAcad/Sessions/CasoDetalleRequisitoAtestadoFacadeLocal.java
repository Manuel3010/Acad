/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpi.CasosAcad.Sessions;

import java.util.List;
import javax.ejb.Local;
import tpi.CasosAcad.Entidades.CasoDetalleRequisitoAtestado;

/**
 *
 * @author manuel
 */
@Local
public interface CasoDetalleRequisitoAtestadoFacadeLocal {

    void create(CasoDetalleRequisitoAtestado casoDetalleRequisitoAtestado);

    void edit(CasoDetalleRequisitoAtestado casoDetalleRequisitoAtestado);

    void remove(CasoDetalleRequisitoAtestado casoDetalleRequisitoAtestado);

    CasoDetalleRequisitoAtestado find(Object id);

    List<CasoDetalleRequisitoAtestado> findAll();

    List<CasoDetalleRequisitoAtestado> findRange(int[] range);

    int count();
    
}
