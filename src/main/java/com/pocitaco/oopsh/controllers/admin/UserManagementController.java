package com.pocitaco.oopsh.controllers.admin;

import com.pocitaco.oopsh.controllers.BaseController;
import com.pocitaco.oopsh.dao.UserDAO;
import com.pocitaco.oopsh.models.User;
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

public class UserManagementController extends BaseController {

    @FXML
    private MFXTextField txtSearch;

    @FXML
    private MFXButton btnSearch;

    @FXML
    private MFXButton btnAddUser;

    @FXML
    private MFXTableView<User> tblUsers;

    private UserDAO userDAO;

    @Override
    public void initializeComponents() {
        this.userDAO = new UserDAO();
        setupTable();
    }

    @Override
    protected void setupEventHandlers() {
        btnAddUser.setOnAction(event -> handleAddUser());
        btnSearch.setOnAction(event -> handleSearch());
    }

    @Override
    protected void loadInitialData() {
        tblUsers.setItems(FXCollections.observableArrayList(userDAO.getAll()));
    }

    private void setupTable() {
        MFXTableRowCell<User, String> nameCell = new MFXTableRowCell<>(User::getFullName);
        MFXTableRowCell<User, String> emailCell = new MFXTableRowCell<>(User::getEmail);
        MFXTableRowCell<User, String> roleCell = new MFXTableRowCell<>(user -> user.getRole().toString());
        MFXTableRowCell<User, String> statusCell = new MFXTableRowCell<>(user -> user.getStatus().toString());

        nameCell.setComparator(Comparator.comparing(User::getFullName));
        emailCell.setComparator(Comparator.comparing(User::getEmail));
        roleCell.setComparator(Comparator.comparing(user -> user.getRole().toString()));
        statusCell.setComparator(Comparator.comparing(user -> user.getStatus().toString()));

        tblUsers.getTableColumns().addAll(
                new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Full Name", true, nameCell),
                new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Email", true, emailCell),
                new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Role", true, roleCell),
                new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Status", true, statusCell),
                createActionsColumn()
        );

        tblUsers.getFilters().addAll(
                new StringFilter<>("Full Name", User::getFullName),
                new StringFilter<>("Email", User::getEmail)
        );
    }

    private io.github.palexdev.materialfx.controls.MFXTableColumn<User> createActionsColumn() {
        io.github.palexdev.materialfx.controls.MFXTableColumn<User> actionsColumn = new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Actions", false, null);

        actionsColumn.setRowCellFactory(user -> new MFXTableRowCell<>(u -> "") {
            {
                HBox buttons = new HBox(5);
                buttons.setAlignment(Pos.CENTER);
                Button btnEdit = new Button("Edit");
                btnEdit.getStyleClass().add("btn-secondary");
                btnEdit.setOnAction(event -> handleEditUser(user));

                Button btnDelete = new Button("Delete");
                btnDelete.getStyleClass().add("btn-error");
                btnDelete.setOnAction(event -> handleDeleteUser(user));

                buttons.getChildren().addAll(btnEdit, btnDelete);
                setGraphic(buttons);
            }
        });

        return actionsColumn;
    }

    private void handleAddUser() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pocitaco/oopsh/admin/user-create.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Create New User");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            // Refresh table after closing
            loadInitialData();
        } catch (IOException e) {
            showError("Error", "Could not load the create user screen.");
            e.printStackTrace();
        }
    }

    private void handleEditUser(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pocitaco/oopsh/admin/user-edit.fxml"));
            Parent root = loader.load();

            UserEditController controller = loader.getController();
            controller.setUserToEdit(user);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Edit User");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            // Refresh table after closing
            loadInitialData();
        } catch (IOException e) {
            showError("Error", "Could not load the edit user screen.");
            e.printStackTrace();
        }
    }

    private void handleDeleteUser(User user) {
        if (showConfirmation("Delete User", "Are you sure you want to delete " + user.getFullName() + "?")) {
            userDAO.delete(user.getId());
            loadInitialData();
            showInfo("User Deleted", user.getFullName() + " has been deleted.");
        }
    }

    private void handleSearch() {
        String searchTerm = txtSearch.getText().toLowerCase();
        tblUsers.setItems(FXCollections.observableArrayList(userDAO.getAll().stream()
                .filter(user -> user.getFullName().toLowerCase().contains(searchTerm) ||
                               user.getEmail().toLowerCase().contains(searchTerm))
                .toList()));
    }

    @Override
    protected void clearForm() {
        // Not applicable for this controller
    }

    @Override
    protected void setFormEnabled(boolean enabled) {
        // Not applicable for this controller
    }
}
