package com.pocitaco.oopsh.controllers.admin;

import com.pocitaco.oopsh.controllers.BaseController;
import com.pocitaco.oopsh.dao.ExamTypeDAO;
import com.pocitaco.oopsh.models.ExamType;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.util.Comparator;

public class ExamManagementController extends BaseController {

    @FXML
    private MFXTextField txtSearch;

    @FXML
    private MFXButton btnSearch;

    @FXML
    private MFXButton btnAddExam;

    @FXML
    private MFXTableView<ExamType> tblExams;

    private ExamTypeDAO examTypeDAO;

    @Override
    public void initializeComponents() {
        this.examTypeDAO = new ExamTypeDAO();
        setupTable();
    }

    @Override
    protected void setupEventHandlers() {
        btnAddExam.setOnAction(event -> handleAddExam());
        btnSearch.setOnAction(event -> handleSearch());
    }

    @Override
    protected void loadInitialData() {
        tblExams.setItems(FXCollections.observableArrayList(examTypeDAO.getAll()));
    }

    private void setupTable() {
        MFXTableRowCell<ExamType, String> codeCell = new MFXTableRowCell<>(ExamType::getCode);
        MFXTableRowCell<ExamType, String> nameCell = new MFXTableRowCell<>(ExamType::getName);
        MFXTableRowCell<ExamType, Double> feeCell = new MFXTableRowCell<>(ExamType::getFee);
        MFXTableRowCell<ExamType, String> statusCell = new MFXTableRowCell<>(ExamType::getStatus);

        codeCell.setComparator(Comparator.comparing(ExamType::getCode));
        nameCell.setComparator(Comparator.comparing(ExamType::getName));
        feeCell.setComparator(Comparator.comparing(ExamType::getFee));
        statusCell.setComparator(Comparator.comparing(ExamType::getStatus));

        tblExams.getTableColumns().addAll(
                new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Code", true, codeCell),
                new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Name", true, nameCell),
                new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Fee", true, feeCell),
                new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Status", true, statusCell),
                createActionsColumn()
        );
    }

    private io.github.palexdev.materialfx.controls.MFXTableColumn<ExamType> createActionsColumn() {
        io.github.palexdev.materialfx.controls.MFXTableColumn<ExamType> actionsColumn = new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Actions", false, null);

        actionsColumn.setRowCellFactory(examType -> new MFXTableRowCell<>(et -> "") {
            {
                HBox buttons = new HBox(5);
                buttons.setAlignment(Pos.CENTER);
                Button btnEdit = new Button("Edit");
                btnEdit.getStyleClass().add("btn-secondary");
                btnEdit.setOnAction(event -> handleEditExam(examType));

                Button btnDelete = new Button("Delete");
                btnDelete.getStyleClass().add("btn-error");
                btnDelete.setOnAction(event -> handleDeleteExam(examType));

                buttons.getChildren().addAll(btnEdit, btnDelete);
                setGraphic(buttons);
            }
        });

        return actionsColumn;
    }

    private void handleAddExam() {
        // Logic to open the create exam screen
        showInfo("Add Exam", "Navigating to create exam screen...");
    }

    private void handleEditExam(ExamType examType) {
        // Logic to open the edit exam screen with the selected exam's data
        showInfo("Edit Exam", "Editing exam: " + examType.getName());
    }

    private void handleDeleteExam(ExamType examType) {
        if (showConfirmation("Delete Exam", "Are you sure you want to delete " + examType.getName() + "?")) {
            examTypeDAO.delete(examType.getId());
            loadInitialData();
            showInfo("Exam Deleted", examType.getName() + " has been deleted.");
        }
    }

    private void handleSearch() {
        String searchTerm = txtSearch.getText().toLowerCase();
        tblExams.setItems(FXCollections.observableArrayList(examTypeDAO.getAll().stream()
                .filter(exam -> exam.getName().toLowerCase().contains(searchTerm) ||
                               exam.getCode().toLowerCase().contains(searchTerm))
                .toList()));
    }

    @Override
    protected void clearForm() {

    }

    @Override
    protected void setFormEnabled(boolean b) {

    }
}
