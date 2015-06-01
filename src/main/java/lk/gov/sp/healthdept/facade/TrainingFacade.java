/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.gov.sp.healthdept.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lk.gov.sp.healthdept.entity.Training;

/**
 *
 * @author pdhs-sp
 */
@Stateless
public class TrainingFacade extends AbstractFacade<Training> {
    @PersistenceContext(unitName = "tspPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TrainingFacade() {
        super(Training.class);
    }
    
}
