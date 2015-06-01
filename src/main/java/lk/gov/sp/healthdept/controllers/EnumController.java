/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.gov.sp.healthdept.controllers;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import lk.gov.sp.healthdept.enums.Designation;
import lk.gov.sp.healthdept.enums.Gender;
import lk.gov.sp.healthdept.enums.Tittle;


/**
 *
 * @author pdhs-sp
 */
@ManagedBean
@ApplicationScoped
public class EnumController {

    /**
     * Creates a new instance of EnumController
     */
    public EnumController() {
    }
    public Tittle[] getTittles(){
        return Tittle.values();
    }
    public Gender[] getGenders(){
        return Gender.values();
    }
    public Designation[] getDesignations(){
        return Designation.values();
    }
}
