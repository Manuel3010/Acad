/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpi.CasosAcad.Sessions;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import tpi.CasosAcad.Entidades.CasoDetalle;

/**
 *
 * @author manuel
 */
@Stateless
public class CasoDetalleFacade extends AbstractFacade<CasoDetalle> implements CasoDetalleFacadeLocal {

    @PersistenceContext(unitName = "tpi2017.CasosAcad_CasosAcadAppMvn-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CasoDetalleFacade() {
        super(CasoDetalle.class);
    }
    
}
