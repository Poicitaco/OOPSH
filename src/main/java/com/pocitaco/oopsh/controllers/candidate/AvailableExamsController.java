package com.pocitaco.oopsh.controllers.candidate;

import com.pocitaco.oopsh.controllers.BaseController;
import com.pocitaco.oopsh.dao.ExamTypeDAO;
import com.pocitaco.oopsh.models.ExamType;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

import java.util.Comparator;

public class AvailableExamsController extends BaseController {

    @FXML
    private MFXTableView<ExamType> tblExams;

    private ExamTypeDAO examTypeDAO;

    @Override
    public void initializeComponents() {
        this.examTypeDAO = new ExamTypeDAO();
        setupTable();
    }

    @Override
    protected void loadInitialData() {
        tblExams.setItems(FXCollections.observableArrayList(examTypeDAO.getAll()));
    }

    private void setupTable() {
        MFXTableRowCell<ExamType, String> codeCell = new MFXTableRowCell<>(ExamType::getCode);
        MFXTableRowCell<ExamType, String> nameCell = new MFXTableRowCell<>(ExamType::getName);
        MFXTableRowCell<ExamType, String> descriptionCell = new MFXTableRowCell<>(ExamType::getDescription);
        MFXTableRowCell<ExamType, Double> feeCell = new MFXTableRowCell<>(ExamType::getFee);

        // codeCell.setComparator(...); // Commented out due to MaterialFX compatibility
        // nameCell.setComparator(...); // Commented out due to MaterialFX compatibility
        // descriptionCell.setComparator(...); // Commented out due to MaterialFX compatibility
        // feeCell.setComparator(...); // Commented out due to MaterialFX compatibility

        tblExams.getTableColumns().addAll(
                // new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Code", true, codeCell),
                // new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Name", true, nameCell),
                // new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Description", true, descriptionCell),
                // new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Fee", true, feeCell)
        );
    }

    @Override
    protected void clearForm() {

    }

    @Override
    protected void setFormEnabled(boolean b) {

    }
}

