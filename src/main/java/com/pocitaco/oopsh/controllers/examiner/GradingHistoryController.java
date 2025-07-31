package com.pocitaco.oopsh.controllers.examiner;

import com.pocitaco.oopsh.controllers.BaseController;
import com.pocitaco.oopsh.dao.ExamTypeDAO;
import com.pocitaco.oopsh.dao.ResultDAO;
import com.pocitaco.oopsh.dao.UserDAO;
import com.pocitaco.oopsh.models.ExamType;
import com.pocitaco.oopsh.models.Result;
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

public class GradingHistoryController extends BaseController {

    @FXML
    private MFXTableView<Result> tblGradingHistory;

    private ResultDAO resultDAO;
    private UserDAO userDAO;
    private ExamTypeDAO examTypeDAO;
    private User currentUser;

    @Override
    public void initializeComponents() {
        this.resultDAO = new ResultDAO();
        this.userDAO = new UserDAO();
        this.examTypeDAO = new ExamTypeDAO();
        this.currentUser = SessionManager.getCurrentUser();
        setupTable();
    }

    @Override
    protected void loadInitialData() {
        if (currentUser != null) {
            List<Result> allResults = resultDAO.getAll();
            // Filter for results graded by the current examiner (simplified logic)
            List<Result> gradedResults = allResults.stream()
                    .filter(result -> result.getStatus().toString().equals("GRADED")) // Assuming 'GRADED' status
                    .collect(Collectors.toList());
            tblGradingHistory.setItems(FXCollections.observableArrayList(gradedResults));
        }
    }

    private void setupTable() {
        MFXTableRowCell<Result, String> candidateNameCell = new MFXTableRowCell<>(result -> getCandidateName(result.getUserId()));
        MFXTableRowCell<Result, String> examNameCell = new MFXTableRowCell<>(result -> getExamName(result.getExamTypeId()));
        MFXTableRowCell<Result, Integer> theoryScoreCell = new MFXTableRowCell<>(Result::getTheoryScore);
        MFXTableRowCell<Result, Integer> practiceScoreCell = new MFXTableRowCell<>(Result::getPracticeScore);
        MFXTableRowCell<Result, String> statusCell = new MFXTableRowCell<>(result -> result.getStatus().toString());

        // candidateNameCell.setComparator(...); // Commented out due to MaterialFX compatibility
        // examNameCell.setComparator(...); // Commented out due to MaterialFX compatibility
        // theoryScoreCell.setComparator(...); // Commented out due to MaterialFX compatibility
        // practiceScoreCell.setComparator(...); // Commented out due to MaterialFX compatibility
        // statusCell.setComparator(...); // Commented out due to MaterialFX compatibility

        tblGradingHistory.getTableColumns().addAll(
                // new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Candidate", true, candidateNameCell),
                // new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Exam", true, examNameCell),
                // new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Theory Score", true, theoryScoreCell),
                // new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Practice Score", true, practiceScoreCell),
                // new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Status", true, statusCell)
        );
    }

    private String getCandidateName(int userId) {
        return userDAO.get(userId).map(User::getFullName).orElse("Unknown Candidate");
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

