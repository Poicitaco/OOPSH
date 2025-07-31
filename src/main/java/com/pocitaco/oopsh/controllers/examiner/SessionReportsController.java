package com.pocitaco.oopsh.controllers.examiner;

import com.pocitaco.oopsh.controllers.BaseController;
import com.pocitaco.oopsh.dao.ExamScheduleDAO;
import com.pocitaco.oopsh.dao.ExamTypeDAO;
import com.pocitaco.oopsh.dao.SessionReportDAO;
import com.pocitaco.oopsh.models.ExamSchedule;
import com.pocitaco.oopsh.models.ExamType;
import com.pocitaco.oopsh.models.SessionReport;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

import java.text.SimpleDateFormat;
import java.util.Comparator;

public class SessionReportsController extends BaseController {

    @FXML
    private MFXTableView<SessionReport> tblSessionReports;

    private SessionReportDAO sessionReportDAO;
    private ExamScheduleDAO examScheduleDAO;
    private ExamTypeDAO examTypeDAO;

    @Override
    public void initializeComponents() {
        this.sessionReportDAO = new SessionReportDAO();
        this.examScheduleDAO = new ExamScheduleDAO();
        this.examTypeDAO = new ExamTypeDAO();
        setupTable();
    }

    @Override
    protected void loadInitialData() {
        tblSessionReports.setItems(FXCollections.observableArrayList(sessionReportDAO.getAll()));
    }

    private void setupTable() {
        MFXTableRowCell<SessionReport, String> sessionIdCell = new MFXTableRowCell<>(report -> String.valueOf(report.getSessionId()));
        MFXTableRowCell<SessionReport, String> examNameCell = new MFXTableRowCell<>(report -> getExamName(report.getSessionId()));
        MFXTableRowCell<SessionReport, String> reportDateCell = new MFXTableRowCell<>(report -> new SimpleDateFormat("yyyy-MM-dd").format(report.getReportDate()));
        MFXTableRowCell<SessionReport, String> summaryCell = new MFXTableRowCell<>(SessionReport::getSummary);

        // sessionIdCell.setComparator(...); // Commented out due to MaterialFX compatibility
        // examNameCell.setComparator(...); // Commented out due to MaterialFX compatibility
        // reportDateCell.setComparator(...); // Commented out due to MaterialFX compatibility
        // summaryCell.setComparator(...); // Commented out due to MaterialFX compatibility

        tblSessionReports.getTableColumns().addAll(
                // new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Session ID", true, sessionIdCell),
                // new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Exam Name", true, examNameCell),
                // new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Report Date", true, reportDateCell),
                // new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Summary", true, summaryCell)
        );
    }

    private String getExamName(int sessionId) {
        return examScheduleDAO.get(sessionId).flatMap(schedule -> examTypeDAO.get(schedule.getExamTypeId())).map(ExamType::getName).orElse("N/A");
    }

    @Override
    protected void clearForm() {

    }

    @Override
    protected void setFormEnabled(boolean b) {

    }
}

