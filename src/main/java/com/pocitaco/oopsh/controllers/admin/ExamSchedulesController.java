package com.pocitaco.oopsh.controllers.admin;

import com.pocitaco.oopsh.controllers.BaseController;
import com.pocitaco.oopsh.dao.ExamScheduleDAO;
import com.pocitaco.oopsh.dao.ExamTypeDAO;
import com.pocitaco.oopsh.dao.UserDAO;
import com.pocitaco.oopsh.models.ExamSchedule;
import com.pocitaco.oopsh.models.ExamType;
import com.pocitaco.oopsh.models.User;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

import java.text.SimpleDateFormat;
import java.util.Comparator;

public class ExamSchedulesController extends BaseController {

    @FXML
    private MFXTableView<ExamSchedule> tblSchedules;

    private ExamScheduleDAO examScheduleDAO;
    private ExamTypeDAO examTypeDAO;
    private UserDAO userDAO;

    @Override
    public void initializeComponents() {
        this.examScheduleDAO = new ExamScheduleDAO();
        this.examTypeDAO = new ExamTypeDAO();
        this.userDAO = new UserDAO();
        setupTable();
    }

    @Override
    protected void loadInitialData() {
        tblSchedules.setItems(FXCollections.observableArrayList(examScheduleDAO.getAll()));
    }

    private void setupTable() {
        MFXTableRowCell<ExamSchedule, String> examNameCell = new MFXTableRowCell<>(schedule -> getExamName(schedule.getExamTypeId()));
        MFXTableRowCell<ExamSchedule, String> examinerNameCell = new MFXTableRowCell<>(schedule -> getExaminerName(schedule.getExaminerId()));
        MFXTableRowCell<ExamSchedule, String> examDateCell = new MFXTableRowCell<>(schedule -> new SimpleDateFormat("yyyy-MM-dd").format(schedule.getExamDate()));
        MFXTableRowCell<ExamSchedule, String> timeSlotCell = new MFXTableRowCell<>(schedule -> schedule.getTimeSlot().toString());
        MFXTableRowCell<ExamSchedule, String> statusCell = new MFXTableRowCell<>(schedule -> schedule.getStatus().toString());

        // examNameCell.setComparator(...); // Commented out due to MaterialFX compatibility
        // examinerNameCell.setComparator(...); // Commented out due to MaterialFX compatibility
        // examDateCell.setComparator(...); // Commented out due to MaterialFX compatibility
        // timeSlotCell.setComparator(...); // Commented out due to MaterialFX compatibility
        // statusCell.setComparator(...); // Commented out due to MaterialFX compatibility

        tblSchedules.getTableColumns().addAll(
                // new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Exam Name", true, examNameCell),
                // new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Examiner", true, examinerNameCell),
                // new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Date", true, examDateCell),
                // new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Time Slot", true, timeSlotCell),
                // new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Status", true, statusCell)
        );
    }

    private String getExamName(int examTypeId) {
        return examTypeDAO.get(examTypeId).map(ExamType::getName).orElse("Unknown Exam");
    }

    private String getExaminerName(int examinerId) {
        return userDAO.get(examinerId).map(User::getFullName).orElse("Unknown Examiner");
    }

    @Override
    protected void clearForm() {

    }

    @Override
    protected void setFormEnabled(boolean b) {

    }
}

