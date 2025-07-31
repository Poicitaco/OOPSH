package com.pocitaco.oopsh.controllers.admin;

import com.pocitaco.oopsh.controllers.BaseController;
import com.pocitaco.oopsh.dao.PermissionDAO;
import com.pocitaco.oopsh.enums.UserRole;
import com.pocitaco.oopsh.models.Permission;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.util.Arrays;

public class PermissionEditController extends BaseController {

    @FXML
    private MFXComboBox<UserRole> cmbRole;

    @FXML
    private MFXTextField txtFunctionality;

    @FXML
    private MFXComboBox<Boolean> cmbHasAccess;

    @FXML
    private MFXButton btnCancel;

    @FXML
    private MFXButton btnSave;

    private PermissionDAO permissionDAO;
    private Permission permissionToEdit;

    @Override
    public void initializeComponents() {
        this.permissionDAO = new PermissionDAO();
        cmbHasAccess.setItems(FXCollections.observableArrayList(Arrays.asList(true, false)));
    }

    @Override
    protected void setupEventHandlers() {
        btnSave.setOnAction(event -> handleSavePermission());
        btnCancel.setOnAction(event -> handleCancel());
    }

    public void setPermissionToEdit(Permission permission) {
        this.permissionToEdit = permission;
        loadPermissionData();
    }

    private void loadPermissionData() {
        if (permissionToEdit != null) {
            cmbRole.setValue(permissionToEdit.getRole());
            txtFunctionality.setText(permissionToEdit.getFunctionality());
            cmbHasAccess.setValue(permissionToEdit.hasAccess());
        }
    }

    private void handleSavePermission() {
        if (validateInput()) {
            permissionToEdit.setHasAccess(cmbHasAccess.getValue());

            permissionDAO.update(permissionToEdit);

            showInfo("Permission Updated", "Permission for " + permissionToEdit.getRole() + " - " + permissionToEdit.getFunctionality() + " has been updated successfully.");
            ((Stage) btnSave.getScene().getWindow()).close();
        }
    }

    private void handleCancel() {
        ((Stage) btnCancel.getScene().getWindow()).close();
    }

    private boolean validateInput() {
        if (cmbHasAccess.getValue() == null) {
            showError("Validation Error", "Please select access status.");
            return false;
        }
        return true;
    }

    @Override
    protected void clearForm() {
        // Not applicable
    }

    @Override
    protected void setFormEnabled(boolean enabled) {
        cmbHasAccess.setDisable(!enabled);
        btnSave.setDisable(!enabled);
        btnCancel.setDisable(!enabled);
    }
}

