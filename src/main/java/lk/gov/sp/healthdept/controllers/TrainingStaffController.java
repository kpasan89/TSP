package lk.gov.sp.healthdept.controllers;

import lk.gov.sp.healthdept.entity.TrainingStaff;
import lk.gov.sp.healthdept.controllers.util.JsfUtil;
import lk.gov.sp.healthdept.controllers.util.JsfUtil.PersistAction;
import lk.gov.sp.healthdept.facade.TrainingStaffFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.Temporal;
import lk.gov.sp.healthdept.entity.Staff;
import lk.gov.sp.healthdept.entity.Training;
import lk.gov.sp.healthdept.facade.StaffFacade;

@ManagedBean(name = "trainingStaffController")
@SessionScoped
public class TrainingStaffController implements Serializable {

    @EJB
    private lk.gov.sp.healthdept.facade.TrainingStaffFacade ejbFacade;
    private List<TrainingStaff> items = null;
    private TrainingStaff selected;
    private List<Staff> staffs;
    private Training training;
    private Training trainingSelect;
    private Staff staffSelect;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date selectTSD;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date startOfTrainingPeriod;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date endOfTrainingPeriod;
    @EJB
    TrainingStaffFacade trainingStaffFacade;
    @EJB
    private StaffFacade staffFacade;

    public TrainingStaffFacade getTrainingStaffFacade() {
        return trainingStaffFacade;
    }

    public void setTrainingStaffFacade(TrainingStaffFacade trainingStaffFacade) {
        this.trainingStaffFacade = trainingStaffFacade;
    }

    public TrainingStaffFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(TrainingStaffFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public List<Staff> getStaffs() {
        if (staffs == null) {
            staffs = new ArrayList<Staff>();
        }
        return staffs;
    }

    public void setStaffs(List<Staff> staffs) {
        this.staffs = staffs;
    }

    public TrainingStaffController() {
    }

    public TrainingStaff getSelected() {
        if (selected == null) {
            selected = new TrainingStaff();
        }
        return selected;
    }

    public void setSelected(TrainingStaff selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TrainingStaffFacade getFacade() {
        return ejbFacade;
    }

    public TrainingStaff prepareCreate() {
        selected = new TrainingStaff();
        initializeEmbeddableKey();
        return selected;
    }

    public void staffBulkTraining() {

        if (getStaffs() == null) {
            JsfUtil.addErrorMessage("Select Staff to Train");
        }
        if (getTraining() == null) {
            JsfUtil.addErrorMessage("Select Training to Continue");
        }
        if (getStartOfTrainingPeriod() == null) {
            JsfUtil.addErrorMessage("Select Start of Training Date");
        }
        if (getEndOfTrainingPeriod() == null) {
            JsfUtil.addErrorMessage("Select End of Training Date");
        }

        System.out.println("************");

        System.out.println("getStaffs() = " + getStaffs());

        System.out.println("************");

        if (getStaffs() == null || getTraining() == null) {
            JsfUtil.addErrorMessage("Nothing To Add");
            System.out.println("Nothing To Add");
        } else {

            for (Staff staff : getStaffs()) {

                Staff stf = staff;
                Training trning = getTraining();

                System.out.println("Added Succesfully 1");

                System.out.println("getSelected().getId() = " + getSelected().getId());

                System.out.println("staff = " + staff);

                System.out.println("getTraining() = " + getTraining());

                System.out.println("getStartOfTrainingPeriod() = " + getStartOfTrainingPeriod());

                System.out.println("getEndOfTrainingPeriod() = " + getEndOfTrainingPeriod());

                System.out.println("stf = " + stf);

                System.out.println("trning = " + trning);

                getSelected().setStaff(stf);

                System.out.println("Added Succesfully 2");

                getSelected().setTraining(trning);

                System.out.println("Added Succesfully 3");

                getSelected().setStartOfTrainingPeriod(getStartOfTrainingPeriod());

                System.out.println("Added Succesfully 4");

                getSelected().setEndOfTrainingPeriod(getEndOfTrainingPeriod());

                System.out.println("Added Succesfully 5");

                getTrainingStaffFacade().create(selected);

                System.out.println("Added Succesfully 6");

                System.out.println("Added Succesfully 7");

            }
            JsfUtil.addSuccessMessage("Added Succesfully");
            prepareCreate();
        }
    }

    List<TrainingStaff> staffTrainingList;

    public List<TrainingStaff> getStaffTrainingList() {
        return staffTrainingList;
    }

    public void setStaffTrainingList(List<TrainingStaff> staffTrainingList) {
        this.staffTrainingList = staffTrainingList;
    }

    public List<TrainingStaff> listTrainingStaff() {
        String sql;
        Map m = new HashMap();
        m.put("tr", getTraining());
        sql = "select ts.staff.id from TrainingStaff ts "
                + " where ts.training =:tr";

        staffTrainingList = getEjbFacade().findBySQL(sql, m);
        System.out.println("staffTrainingList = " + staffTrainingList);
        return staffTrainingList;
    }

    List<Staff> staffToTrainngList;

    public List<Staff> getStaffToTrainngList() {
        return staffToTrainngList;
    }

    public void setStaffToTrainngList(List<Staff> staffToTrainngList) {
        this.staffToTrainngList = staffToTrainngList;
    }

    public List<Staff> createStaffToTraining() {
        String sql;
        List<TrainingStaff> trs = listTrainingStaff();
        System.out.println("trs = " + trs);
        Map m = new HashMap();
        m.put("tr", trs);
        sql = "select s from Staff s "
                + " where s.id not in :tr";

        staffToTrainngList = getStaffFacade().findBySQL(sql, m);
        System.out.println("staffToTrainngList = " + staffToTrainngList);

        return staffToTrainngList;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TrainingStaffCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TrainingStaffUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TrainingStaffDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<TrainingStaff> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public List<TrainingStaff> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<TrainingStaff> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public Date getStartOfTrainingPeriod() {
        if (startOfTrainingPeriod == null) {
            startOfTrainingPeriod = new Date();
        }
        return startOfTrainingPeriod;
    }

    public void setStartOfTrainingPeriod(Date startOfTrainingPeriod) {
        this.startOfTrainingPeriod = startOfTrainingPeriod;
    }

    public Date getEndOfTrainingPeriod() {
        if (endOfTrainingPeriod == null) {
            endOfTrainingPeriod = new Date();
        }
        return endOfTrainingPeriod;
    }

    public void setEndOfTrainingPeriod(Date endOfTrainingPeriod) {
        this.endOfTrainingPeriod = endOfTrainingPeriod;
    }

    public Training getTraining() {
        if (training == null) {
            training = new Training();
        }
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public Training getTrainingSelect() {
        return trainingSelect;
    }

    public void setTrainingSelect(Training trainingSelect) {
        this.trainingSelect = trainingSelect;
    }

    public Staff getStaffSelect() {
        return staffSelect;
    }

    public void setStaffSelect(Staff staffSelect) {
        this.staffSelect = staffSelect;
    }

    public Date getSelectTSD() {
        return selectTSD;
    }

    public void setSelectTSD(Date selectTSD) {
        this.selectTSD = selectTSD;
    }

    public StaffFacade getStaffFacade() {
        return staffFacade;
    }

    public void setStaffFacade(StaffFacade staffFacade) {
        this.staffFacade = staffFacade;
    }

    @FacesConverter(forClass = TrainingStaff.class)
    public static class TrainingStaffControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TrainingStaffController controller = (TrainingStaffController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "trainingStaffController");
            return controller.getFacade().find(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof TrainingStaff) {
                TrainingStaff o = (TrainingStaff) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TrainingStaff.class.getName()});
                return null;
            }
        }

    }

}