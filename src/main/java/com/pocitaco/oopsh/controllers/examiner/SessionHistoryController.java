package com.pocitaco.oopsh.controllers.examiner;

import com.pocitaco.oopsh.controllers.BaseController;
import com.pocitaco.oopsh.dao.ExamScheduleDAO;
import com.pocitaco.oopsh.dao.ExamTypeDAO;
import com.pocitaco.oopsh.models.ExamSchedule;
import com.pocitaco.oopsh.models.ExamType;
import com.pocitaco.oopsh.models.User;
import com.pocitaco.oopsh.utils.SessionManager;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SessionHistoryController extends BaseController {

    @FXML
    private MFXTableView<ExamSchedule> tblSessionHistory;

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
    protected void loadInitialData() {
        if (currentUser != null) {
            List<ExamSchedule> allSchedules = examScheduleDAO.getAll();
            List<ExamSchedule> myPastSchedules = allSchedules.stream()
                    .filter(schedule -> schedule.getExaminerId() == currentUser.getId() && schedule.getStatus().toString().equals("COMPLETED"))
                    .collect(Collectors.toList());
            tblSessionHistory.setItems(FXCollections.observableArrayList(myPastSchedules));
        }
    }

    private void setupTable() {
        MFXTableRowCell<ExamSchedule, String> examNameCell = new MFXTableRowCell<>(schedule -> getExamName(schedule.getExamTypeId()));
        MFXTableRowCell<ExamSchedule, String> examDateCell = new MFXTableRowCell<>(schedule -> new SimpleDateFormat("yyyy-MM-dd").format(schedule.getExamDate()));
        MFXTableRowCell<ExamSchedule, String> timeSlotCell = new MFXTableRowCell<>(schedule -> schedule.getTimeSlot().toString());
        MFXTableRowCell<ExamSchedule, String> statusCell = new MFXTableRowCell<>(schedule -> schedule.getStatus().toString());

        // examNameCell.setComparator(...); // Commented out due to MaterialFX compatibility
        // examDateCell.setComparator(...); // Commented out due to MaterialFX compatibility
        // timeSlotCell.setComparator(...); // Commented out due to MaterialFX compatibility
        // statusCell.setComparator(...); // Commented out due to MaterialFX compatibility

        tblSessionHistory.getTableColumns().addAll(
                // new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Exam Name", true, examNameCell),
                // new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Date", true, examDateCell),
                // new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Time Slot", true, timeSlotCell),
                // new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Status", true, statusCell)
        );
    }

    private String getExamName(int examTypeId) {
        return examTypeDAO.get(examTypeId).map(ExamType::getName).orElse("Unknown Exam");
    }

    @Override
    protected void clearForm() {

    }

    @Override
    protected void setFormEnabled(boolean b) {

    }
}

