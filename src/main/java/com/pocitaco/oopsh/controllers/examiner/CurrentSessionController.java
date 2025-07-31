package com.pocitaco.oopsh.controllers.examiner;

import com.pocitaco.oopsh.controllers.BaseController;
import com.pocitaco.oopsh.dao.ExamScheduleDAO;
import com.pocitaco.oopsh.dao.ExamTypeDAO;
import com.pocitaco.oopsh.models.ExamSchedule;
import com.pocitaco.oopsh.models.ExamType;
import com.pocitaco.oopsh.models.User;
import com.pocitaco.oopsh.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class CurrentSessionController extends BaseController {

    @FXML
    private Label lblSessionId;

    @FXML
    private Label lblExamType;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTimeSlot;

    @FXML
    private Label lblStatus;

    private ExamScheduleDAO examScheduleDAO;
    private ExamTypeDAO examTypeDAO;
    private User currentUser;

    @Override
    public void initializeComponents() {
        this.examScheduleDAO = new ExamScheduleDAO();
        this.examTypeDAO = new ExamTypeDAO();
        this.currentUser = SessionManager.getCurrentUser();
    }

    @Override
    protected void loadInitialData() {
        if (currentUser != null) {
            // In a real application, you would determine the 'current' session more robustly
            // For now, let's just pick the first active session for the current examiner
            Optional<ExamSchedule> currentSession = examScheduleDAO.getAll().stream()
                    .filter(schedule -> schedule.getExaminerId() == currentUser.getId() && schedule.getStatus().toString().equals("ACTIVE"))
                    .findFirst();

            if (currentSession.isPresent()) {
                ExamSchedule session = currentSession.get();
                lblSessionId.setText(String.valueOf(session.getId()));
                examTypeDAO.get(session.getExamTypeId()).ifPresent(examType -> lblExamType.setText(examType.getName()));
                lblDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(session.getExamDate()));
                lblTimeSlot.setText(session.getTimeSlot().toString());
                lblStatus.setText(session.getStatus().toString());
            } else {
                lblSessionId.setText("N/A");
                lblExamType.setText("No active session");
                lblDate.setText("N/A");
                lblTimeSlot.setText("N/A");
                lblStatus.setText("N/A");
            }
        }
    }

    @Override
    protected void clearForm() {

    }

    @Override
    protected void setFormEnabled(boolean b) {

    }
}

