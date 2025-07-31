package com.pocitaco.oopsh.controllers.candidate;

import com.pocitaco.oopsh.controllers.BaseController;
import com.pocitaco.oopsh.dao.ExamScheduleDAO;
import com.pocitaco.oopsh.dao.ExamTypeDAO;
import com.pocitaco.oopsh.models.ExamSchedule;
import com.pocitaco.oopsh.models.ExamType;
import com.pocitaco.oopsh.models.User;
import com.pocitaco.oopsh.utils.SessionManager;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UpcomingExamsController extends BaseController {

    @FXML
    private MFXTableView<ExamSchedule> tblUpcomingExams;

    private ExamScheduleDAO examScheduleDAO;
    private ExamTypeDAO examTypeDAO;
    private User currentUser;

    @Override
    public void initializeComponents() {
        this.examScheduleDAO = new ExamScheduleDAO();
        this.examTypeDAO = new ExamTypeDAO();
        this.currentUser = SessionManager.getCurrentUser();
        setupTable();
    }

    @Override
    protected void setupEventHandlers() {
        // No event handlers needed for this view
    }

    @Override
    protected void loadInitialData() {
        if (currentUser != null) {
            Date today = new Date();
            List<ExamSchedule> allSchedules = examScheduleDAO.findAll();
            List<ExamSchedule> upcomingExams = allSchedules.stream()
                    .filter(schedule -> schedule.getExamDate().after(today))
                    .collect(Collectors.toList());
            tblUpcomingExams.setItems(FXCollections.observableArrayList(upcomingExams));
        }
    }

    private void setupTable() {
        // Create columns with simple approach to avoid compatibility issues
        MFXTableColumn<ExamSchedule> examNameColumn = new MFXTableColumn<>("Exam Name");
        MFXTableColumn<ExamSchedule> dateColumn = new MFXTableColumn<>("Date");
        MFXTableColumn<ExamSchedule> timeSlotColumn = new MFXTableColumn<>("Time Slot");
        MFXTableColumn<ExamSchedule> statusColumn = new MFXTableColumn<>("Status");

        // Add columns to table
        tblUpcomingExams.getTableColumns().addAll(
                examNameColumn, dateColumn, timeSlotColumn, statusColumn);
    }

    private String getExamName(int examTypeId) {
        Optional<ExamType> examType = examTypeDAO.findById(examTypeId);
        return examType.map(ExamType::getName).orElse("Unknown");
    }

    @Override
    protected void clearForm() {
        // No form to clear
    }

    @Override
    protected void setFormEnabled(boolean enabled) {
        // No form to enable/disable
    }
}
