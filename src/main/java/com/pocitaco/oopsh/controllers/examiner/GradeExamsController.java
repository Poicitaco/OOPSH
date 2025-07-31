package com.pocitaco.oopsh.controllers.examiner;

import com.pocitaco.oopsh.controllers.BaseController;
import com.pocitaco.oopsh.dao.ExamScheduleDAO;
import com.pocitaco.oopsh.dao.ExamTypeDAO;
import com.pocitaco.oopsh.dao.ResultDAO;
import com.pocitaco.oopsh.models.ExamSchedule;
import com.pocitaco.oopsh.models.ExamType;
import com.pocitaco.oopsh.models.Result;
import com.pocitaco.oopsh.models.User;
import com.pocitaco.oopsh.utils.SessionManager;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GradeExamsController extends BaseController {

    @FXML
    private MFXTableView<Result> tblExamsToGrade;

    private ResultDAO resultDAO;
    private ExamTypeDAO examTypeDAO;
    private User currentUser;

    @Override
    public void initializeComponents() {
        this.resultDAO = new ResultDAO();
        this.examTypeDAO = new ExamTypeDAO();
        this.currentUser = SessionManager.getCurrentUser();
        setupTable();
    }

    @Override
    protected void loadInitialData() {
        if (currentUser != null) {
            List<Result> allResults = resultDAO.getAll();
            // In a real app, you would filter by exams assigned to the current examiner
            List<Result> examsToGrade = allResults.stream()
                    .filter(result -> result.getStatus() == null || result.getStatus().toString().equals("PENDING"))
                    .collect(Collectors.toList());
            tblExamsToGrade.setItems(FXCollections.observableArrayList(examsToGrade));
        }
    }

    private void setupTable() {
        MFXTableRowCell<Result, String> examNameCell = new MFXTableRowCell<>(result -> getExamName(result.getExamTypeId()));
        MFXTableRowCell<Result, String> examDateCell = new MFXTableRowCell<>(result -> new SimpleDateFormat("yyyy-MM-dd").format(result.getExamDate()));

        // examNameCell.setComparator(...); // Commented out due to MaterialFX compatibility
        // examDateCell.setComparator(...); // Commented out due to MaterialFX compatibility

        tblExamsToGrade.getTableColumns().addAll(
                // new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Exam Name", true, examNameCell),
                // new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Date", true, examDateCell),
                createActionsColumn()
        );
    }

    private io.github.palexdev.materialfx.controls.MFXTableColumn<Result> createActionsColumn() {
        io.github.palexdev.materialfx.controls.MFXTableColumn<Result> actionsColumn = // new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Actions", false, null);

        actionsColumn.setRowCellFactory(result -> new MFXTableRowCell<>(r -> "") {
            {
                Button btnGrade = new Button("Grade");
                btnGrade.getStyleClass().add("btn-primary");
                btnGrade.setOnAction(event -> handleGradeExam(result));
                setGraphic(btnGrade);
            }
        });

        return actionsColumn;
    }

    private void handleGradeExam(Result result) {
        // Logic to open the grading screen for the selected exam
        showInfo("Grade Exam", "Navigating to grading screen for exam: " + getExamName(result.getExamTypeId()));
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

