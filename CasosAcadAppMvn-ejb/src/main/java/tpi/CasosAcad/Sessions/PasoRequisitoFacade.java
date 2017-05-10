/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpi.CasosAcad.Sessions;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import tpi.CasosAcad.Entidades.Paso;
import tpi.CasosAcad.Entidades.PasoRequisito;

/**
 *
 * @author manuel
 */
@Stateless
public class PasoRequisitoFacade extends AbstractFacade<PasoRequisito> implements PasoRequisitoFacadeLocal {

    @PersistenceContext(unitName = "tpi2017.CasosAcad_CasosAcadAppMvn-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PasoRequisitoFacade() {
        super(PasoRequisito.class);
    }


    
    public List<PasoRequisito> findByIdPaso(Object id) {
        
      List salida= new ArrayList();
     try {
            if(em != null) {
                Query q = em.createNamedQuery("PasoRequisito.findByIdPaso");
                q.setParameter("idPaso",id);
                salida=q.getResultList();
            }
        } catch(Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,e.getMessage(),e);
        }
        return salida;
    }

    
}
