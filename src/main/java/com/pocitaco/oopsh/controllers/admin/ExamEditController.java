package com.pocitaco.oopsh.controllers.admin;

import com.pocitaco.oopsh.controllers.BaseController;
import com.pocitaco.oopsh.dao.ExamTypeDAO;
import com.pocitaco.oopsh.models.ExamType;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;

public class ExamEditController extends BaseController {

    @FXML
    private MFXTextField txtCode;

    @FXML
    private MFXTextField txtName;

    @FXML
    private MFXTextField txtDescription;

    @FXML
    private MFXTextField txtFee;

    @FXML
    private MFXTextField txtTheoryPassScore;

    @FXML
    private MFXTextField txtPracticePassScore;

    @FXML
    private MFXButton btnCancel;

    @FXML
    private MFXButton btnSave;

    private ExamTypeDAO examTypeDAO;
    private ExamType examTypeToEdit;

    @Override
    public void initializeComponents() {
        this.examTypeDAO = new ExamTypeDAO();
    }

    @Override
    protected void setupEventHandlers() {
        btnSave.setOnAction(event -> handleSaveExamType());
        btnCancel.setOnAction(event -> handleCancel());
    }

    public void setExamTypeToEdit(ExamType examType) {
        this.examTypeToEdit = examType;
        loadExamTypeData();
    }

    private void loadExamTypeData() {
        if (examTypeToEdit != null) {
            txtCode.setText(examTypeToEdit.getCode());
            txtName.setText(examTypeToEdit.getName());
            txtDescription.setText(examTypeToEdit.getDescription());
            txtFee.setText(String.valueOf(examTypeToEdit.getFee()));
            txtTheoryPassScore.setText(String.valueOf(examTypeToEdit.getTheoryPassScore()));
            txtPracticePassScore.setText(String.valueOf(examTypeToEdit.getPracticePassScore()));
        }
    }

    private void handleSaveExamType() {
        if (validateInput()) {
            examTypeToEdit.setCode(txtCode.getText());
            examTypeToEdit.setName(txtName.getText());
            examTypeToEdit.setDescription(txtDescription.getText());
            examTypeToEdit.setFee(Double.parseDouble(txtFee.getText()));
            examTypeToEdit.setTheoryPassScore(Integer.parseInt(txtTheoryPassScore.getText()));
            examTypeToEdit.setPracticePassScore(Integer.parseInt(txtPracticePassScore.getText()));

            examTypeDAO.update(examTypeToEdit);

            showInfo("Exam Type Updated", "Exam Type " + examTypeToEdit.getName() + " has been updated successfully.");
            // Optionally, navigate back to the exam management screen
        }
    }

    private void handleCancel() {
        // Logic to navigate back to the exam management screen
        showInfo("Cancelled", "Edit exam type operation cancelled.");
    }

    private boolean validateInput() {
        if (txtCode.getText().isEmpty() || txtName.getText().isEmpty() || txtFee.getText().isEmpty() ||
            txtTheoryPassScore.getText().isEmpty() || txtPracticePassScore.getText().isEmpty()) {
            showError("Validation Error", "Please fill in all required fields.");
            return false;
        }
        try {
            Double.parseDouble(txtFee.getText());
            Integer.parseInt(txtTheoryPassScore.getText());
            Integer.parseInt(txtPracticePassScore.getText());
        } catch (NumberFormatException e) {
            showError("Validation Error", "Fee and scores must be numbers.");
            return false;
        }
        return true;
    }

    @Override
    protected void clearForm() {
        txtCode.clear();
        txtName.clear();
        txtDescription.clear();
        txtFee.clear();
        txtTheoryPassScore.clear();
        txtPracticePassScore.clear();
    }

    @Override
    protected void setFormEnabled(boolean enabled) {
        txtCode.setDisable(!enabled);
        txtName.setDisable(!enabled);
        txtDescription.setDisable(!enabled);
        txtFee.setDisable(!enabled);
        txtTheoryPassScore.setDisable(!enabled);
        txtPracticePassScore.setDisable(!enabled);
        btnSave.setDisable(!enabled);
        btnCancel.setDisable(!enabled);
    }
}

