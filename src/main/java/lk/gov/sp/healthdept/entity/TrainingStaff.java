/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.gov.sp.healthdept.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author pdhs-sp
 */
@Entity
public class TrainingStaff implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Staff staff;
    @ManyToOne
    private Training training;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date startOfTrainingPeriod;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date endOfTrainingPeriod;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TrainingStaff)) {
            return false;
        }
        TrainingStaff other = (TrainingStaff) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.gov.sp.healthdept.entity.TrainingStaff[ id=" + id + " ]";
    }

    public Staff getStaff() {
        if(staff == null){
            staff = new Staff();
        }
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Training getTraining() {
        if(training == null){
            training = new Training();
        }
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public Date getStartOfTrainingPeriod() {
        return startOfTrainingPeriod;
    }

    public void setStartOfTrainingPeriod(Date startOfTrainingPeriod) {
        this.startOfTrainingPeriod = startOfTrainingPeriod;
    }

    public Date getEndOfTrainingPeriod() {
        return endOfTrainingPeriod;
    }

    public void setEndOfTrainingPeriod(Date endOfTrainingPeriod) {
        this.endOfTrainingPeriod = endOfTrainingPeriod;
    }
    
}
