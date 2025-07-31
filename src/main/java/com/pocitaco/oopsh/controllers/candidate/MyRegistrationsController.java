package com.pocitaco.oopsh.controllers.candidate;

import com.pocitaco.oopsh.controllers.BaseController;
import com.pocitaco.oopsh.dao.ExamTypeDAO;
import com.pocitaco.oopsh.dao.RegistrationDAO;
import com.pocitaco.oopsh.models.ExamType;
import com.pocitaco.oopsh.models.Registration;
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

public class MyRegistrationsController extends BaseController {

    @FXML
    private MFXTableView<Registration> tblRegistrations;

    private RegistrationDAO registrationDAO;
    private ExamTypeDAO examTypeDAO;
    private User currentUser;

    @Override
    public void initializeComponents() {
        this.registrationDAO = new RegistrationDAO();
        this.examTypeDAO = new ExamTypeDAO();
        this.currentUser = SessionManager.getCurrentUser();
        setupTable();
    }

    @Override
    protected void loadInitialData() {
        if (currentUser != null) {
            List<Registration> allRegistrations = registrationDAO.getAll();
            List<Registration> myRegistrations = allRegistrations.stream()
                    .filter(reg -> reg.getUserId() == currentUser.getId())
                    .collect(Collectors.toList());
            tblRegistrations.setItems(FXCollections.observableArrayList(myRegistrations));
        }
    }

    private void setupTable() {
        MFXTableRowCell<Registration, String> examNameCell = new MFXTableRowCell<>(reg -> getExamName(reg.getExamTypeId()));
        MFXTableRowCell<Registration, String> registrationDateCell = new MFXTableRowCell<>(reg -> new SimpleDateFormat("yyyy-MM-dd").format(reg.getRegistrationDate()));
        MFXTableRowCell<Registration, String> statusCell = new MFXTableRowCell<>(Registration::getStatus);

        // examNameCell.setComparator(...); // Commented out due to MaterialFX compatibility
        // registrationDateCell.setComparator(...); // Commented out due to MaterialFX compatibility
        // statusCell.setComparator(...); // Commented out due to MaterialFX compatibility

        tblRegistrations.getTableColumns().addAll(
                // new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Exam Name", true, examNameCell),
                // new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Registration Date", true, registrationDateCell),
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

