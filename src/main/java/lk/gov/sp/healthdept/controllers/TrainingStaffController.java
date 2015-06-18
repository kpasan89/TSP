package lk.gov.sp.healthdept.controllers;

import lk.gov.sp.healthdept.entity.TrainingStaff;
import lk.gov.sp.healthdept.controllers.util.JsfUtil;
import lk.gov.sp.healthdept.controllers.util.JsfUtil.PersistAction;
import lk.gov.sp.healthdept.facade.TrainingStaffFacade;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    public Date createEndDate(){
        endOfTrainingPeriod = addDays(getStartOfTrainingPeriod(), getTraining().getDuration());
        return endOfTrainingPeriod;
    }

    public Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);

        return cal.getTime();
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
        int a = getStaffs().size();

        for (Staff s : getStaffs()) {
            TrainingStaff ts = new TrainingStaff();
            ts.setStaff(s);
            ts.setTraining(getTraining());
            ts.setStartOfTrainingPeriod(getStartOfTrainingPeriod());
            ts.setEndOfTrainingPeriod(getEndOfTrainingPeriod());
            getEjbFacade().create(ts);
            System.out.println("Added");
        }
        JsfUtil.addSuccessMessage(+a + " Record(s) Succesfully Added");
        prepareCreate();
    }

    List<TrainingStaff> staffTrainingList;

    public List<TrainingStaff> getStaffTrainingList() {
        return staffTrainingList;
    }

    public void setStaffTrainingList(List<TrainingStaff> staffTrainingList) {
        this.staffTrainingList = staffTrainingList;
    }

    public List<Staff> listTrainingStaff() {
        String sql;
        Map m = new HashMap();
        List<Staff> staffs = new ArrayList<Staff>();
        m.put("tr", getTraining());
        sql = "select ts.staff from TrainingStaff ts "
                + " where ts.training =:tr";

        staffs = getStaffFacade().findBySQL(sql, m);
        System.out.println("staffs = " + staffs);
        return staffs;
    }

    List<Staff> staffToTrainngList;

    public List<Staff> getStaffToTrainngList() {
        if (staffToTrainngList == null) {
            staffToTrainngList = new ArrayList<Staff>();
        }
        return staffToTrainngList;
    }

    public void setStaffToTrainngList(List<Staff> staffToTrainngList) {
        this.staffToTrainngList = staffToTrainngList;
    }

    public List<Staff> createStaffToTraining() {
        if (getTraining() == null) {
            JsfUtil.addErrorMessage("Please Select Training");
        }
        if (getStaffs() == null) {
            JsfUtil.addErrorMessage("Please Select Staff(s)");
        }
        String sql;
        List<Staff> trs = listTrainingStaff();
        System.out.println("trs = " + trs);
        Map m = new HashMap();
//        m.put("tr", trs);
        sql = "select s from Staff s ";
//        sql = "select s from Staff s "
//                + " where s.id not in :tr";

        staffToTrainngList = getStaffFacade().findBySQL(sql, m);
        System.out.println("staffToTrainngList = " + staffToTrainngList);
        staffToTrainngList.removeAll(trs);
        System.out.println("staffToTrainngList = " + staffToTrainngList);

        return staffToTrainngList;
    }

    public void showTable() {
        String sql;
        sql = "select ts from TrainingStaff ts";
        staffTrainingList = getTrainingStaffFacade().findBySQL(sql);
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
        endOfTrainingPeriod = addDays(getStartOfTrainingPeriod(), getTraining().getDuration());
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
