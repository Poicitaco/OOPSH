package com.pocitaco.oopsh.controllers.examiner;

import com.pocitaco.oopsh.controllers.BaseController;
import com.pocitaco.oopsh.dao.UserDAO;
import com.pocitaco.oopsh.models.User;
import com.pocitaco.oopsh.utils.SessionManager;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CandidatesController extends BaseController {

    @FXML
    private MFXTableView<User> tblCandidates;

    private UserDAO userDAO;
    private User currentUser;

    @Override
    public void initializeComponents() {
        this.userDAO = new UserDAO();
        this.currentUser = SessionManager.getCurrentUser();
        setupTable();
    }

    @Override
    protected void loadInitialData() {
        if (currentUser != null) {
            // In a real application, you would filter candidates assigned to this examiner
            // For now, we'll just show all candidates
            List<User> allUsers = userDAO.getAllUsers();
            List<User> candidates = allUsers.stream()
                    .filter(user -> user.getRole().toString().equals("CANDIDATE"))
                    .collect(Collectors.toList());
            tblCandidates.setItems(FXCollections.observableArrayList(candidates));
        }
    }

    private void setupTable() {
        MFXTableRowCell<User, String> nameCell = new MFXTableRowCell<>(User::getFullName);
        MFXTableRowCell<User, String> emailCell = new MFXTableRowCell<>(User::getEmail);
        MFXTableRowCell<User, String> cccdCell = new MFXTableRowCell<>(User::getCccd);

        // nameCell.setComparator(...); // Commented out due to MaterialFX compatibility
        // emailCell.setComparator(...); // Commented out due to MaterialFX compatibility
        // cccdCell.setComparator(...); // Commented out due to MaterialFX compatibility

        tblCandidates.getTableColumns().addAll(
                // new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Full Name", true, nameCell),
                // new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Email", true, emailCell),
                // new io.github.palexdev.materialfx.controls.MFXTableColumn<>("CCCD", true, cccdCell)
        );
    }

    @Override
    protected void clearForm() {

    }

    @Override
    protected void setFormEnabled(boolean b) {

    }
}

