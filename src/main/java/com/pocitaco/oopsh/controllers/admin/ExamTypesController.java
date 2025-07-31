package com.pocitaco.oopsh.controllers.admin;

import com.pocitaco.oopsh.controllers.BaseController;
import com.pocitaco.oopsh.dao.ExamTypeDAO;
import com.pocitaco.oopsh.models.ExamType;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Comparator;

public class ExamTypesController extends BaseController {

    @FXML
    private MFXTextField txtSearch;

    @FXML
    private MFXButton btnSearch;

    @FXML
    private MFXButton btnAddExamType;

    @FXML
    private MFXTableView<ExamType> tblExamTypes;

    private ExamTypeDAO examTypeDAO;

    @Override
    public void initializeComponents() {
        this.examTypeDAO = new ExamTypeDAO();
        setupTable();
    }

    @Override
    protected void setupEventHandlers() {
        btnAddExamType.setOnAction(event -> handleAddExamType());
        btnSearch.setOnAction(event -> handleSearch());
    }

    @Override
    protected void loadInitialData() {
        tblExamTypes.setItems(FXCollections.observableArrayList(examTypeDAO.getAll()));
    }

    private void setupTable() {
        MFXTableRowCell<ExamType, String> codeCell = new MFXTableRowCell<>(ExamType::getCode);
        MFXTableRowCell<ExamType, String> nameCell = new MFXTableRowCell<>(ExamType::getName);
        MFXTableRowCell<ExamType, Double> feeCell = new MFXTableRowCell<>(ExamType::getFee);
        MFXTableRowCell<ExamType, String> statusCell = new MFXTableRowCell<>(ExamType::getStatus);

        // codeCell.setComparator(...); // Commented out due to MaterialFX compatibility
        // nameCell.setComparator(...); // Commented out due to MaterialFX compatibility
        // feeCell.setComparator(...); // Commented out due to MaterialFX compatibility
        // statusCell.setComparator(...); // Commented out due to MaterialFX compatibility

        tblExamTypes.getTableColumns().addAll(
                // new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Code", true, codeCell),
                // new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Name", true, nameCell),
                // new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Fee", true, feeCell),
                // new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Status", true, statusCell),
                createActionsColumn()
        );

        tblExamTypes.getFilters().addAll(
                new StringFilter<>("Code", ExamType::getCode),
                new StringFilter<>("Name", ExamType::getName)
        );
    }

    private io.github.palexdev.materialfx.controls.MFXTableColumn<ExamType> createActionsColumn() {
        io.github.palexdev.materialfx.controls.MFXTableColumn<ExamType> actionsColumn = // new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Actions", false, null);

        actionsColumn.setRowCellFactory(examType -> new MFXTableRowCell<>(et -> "") {
            {
                HBox buttons = new HBox(5);
                buttons.setAlignment(Pos.CENTER);
                Button btnEdit = new Button("Edit");
                btnEdit.getStyleClass().add("btn-secondary");
                btnEdit.setOnAction(event -> handleEditExamType(examType));

                Button btnDelete = new Button("Delete");
                btnDelete.getStyleClass().add("btn-error");
                btnDelete.setOnAction(event -> handleDeleteExamType(examType));

                buttons.getChildren().addAll(btnEdit, btnDelete);
                setGraphic(buttons);
            }
        });

        return actionsColumn;
    }

    private void handleAddExamType() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pocitaco/oopsh/admin/exam-create.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Create New Exam Type");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            loadInitialData(); // Refresh table
        } catch (IOException e) {
            showError("Error", "Could not load the create exam type screen.");
            e.printStackTrace();
        }
    }

    private void handleEditExamType(ExamType examType) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pocitaco/oopsh/admin/exam-edit.fxml"));
            Parent root = loader.load();

            ExamEditController controller = loader.getController();
            controller.setExamTypeToEdit(examType);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Edit Exam Type");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            loadInitialData(); // Refresh table
        } catch (IOException e) {
            showError("Error", "Could not load the edit exam type screen.");
            e.printStackTrace();
        }
    }

    private void handleDeleteExamType(ExamType examType) {
        if (showConfirmation("Delete Exam Type", "Are you sure you want to delete " + examType.getName() + "?")) {
            examTypeDAO.delete(examType.getId());
            loadInitialData();
            showInfo("Exam Type Deleted", examType.getName() + " has been deleted.");
        }
    }

    private void handleSearch() {
        String searchTerm = txtSearch.getText().toLowerCase();
        tblExamTypes.setItems(FXCollections.observableArrayList(examTypeDAO.getAll().stream()
                .filter(exam -> exam.getName().toLowerCase().contains(searchTerm) ||
                               exam.getCode().toLowerCase().contains(searchTerm))
                .toList()));
    }

    @Override
    protected void clearForm() {
        // Not applicable
    }

    @Override
    protected void setFormEnabled(boolean b) {
        // Not applicable
    }
}

