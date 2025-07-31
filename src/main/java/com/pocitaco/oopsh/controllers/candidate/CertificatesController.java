package com.pocitaco.oopsh.controllers.candidate;

import com.pocitaco.oopsh.controllers.BaseController;
import com.pocitaco.oopsh.dao.CertificateDAO;
import com.pocitaco.oopsh.dao.ExamTypeDAO;
import com.pocitaco.oopsh.models.Certificate;
import com.pocitaco.oopsh.models.ExamType;
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

public class CertificatesController extends BaseController {

    @FXML
    private MFXTableView<Certificate> tblCertificates;

    private CertificateDAO certificateDAO;
    private ExamTypeDAO examTypeDAO;
    private User currentUser;

    @Override
    public void initializeComponents() {
        this.certificateDAO = new CertificateDAO();
        this.examTypeDAO = new ExamTypeDAO();
        this.currentUser = SessionManager.getCurrentUser();
        setupTable();
    }

    @Override
    protected void loadInitialData() {
        if (currentUser != null) {
            List<Certificate> allCertificates = certificateDAO.getAll();
            List<Certificate> myCertificates = allCertificates.stream()
                    .filter(cert -> cert.getUserId() == currentUser.getId())
                    .collect(Collectors.toList());
            tblCertificates.setItems(FXCollections.observableArrayList(myCertificates));
        }
    }

    private void setupTable() {
        MFXTableRowCell<Certificate, String> examNameCell = new MFXTableRowCell<>(cert -> getExamName(cert.getExamTypeId()));
        MFXTableRowCell<Certificate, String> issueDateCell = new MFXTableRowCell<>(cert -> new SimpleDateFormat("yyyy-MM-dd").format(cert.getIssueDate()));

        // examNameCell.setComparator(...); // Commented out due to MaterialFX compatibility
        // issueDateCell.setComparator(...); // Commented out due to MaterialFX compatibility

        tblCertificates.getTableColumns().addAll(
                // new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Exam Name", true, examNameCell),
                // new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Issue Date", true, issueDateCell)
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

