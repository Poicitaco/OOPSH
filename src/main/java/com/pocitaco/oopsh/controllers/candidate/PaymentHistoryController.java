package com.pocitaco.oopsh.controllers.candidate;

import com.pocitaco.oopsh.controllers.BaseController;
import com.pocitaco.oopsh.dao.ExamTypeDAO;
import com.pocitaco.oopsh.dao.PaymentDAO;
import com.pocitaco.oopsh.models.ExamType;
import com.pocitaco.oopsh.models.Payment;
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

public class PaymentHistoryController extends BaseController {

    @FXML
    private MFXTableView<Payment> tblPayments;

    private PaymentDAO paymentDAO;
    private ExamTypeDAO examTypeDAO;
    private User currentUser;

    @Override
    public void initializeComponents() {
        this.paymentDAO = new PaymentDAO();
        this.examTypeDAO = new ExamTypeDAO();
        this.currentUser = SessionManager.getCurrentUser();
        setupTable();
    }

    @Override
    protected void loadInitialData() {
        if (currentUser != null) {
            List<Payment> allPayments = paymentDAO.getAll();
            List<Payment> myPayments = allPayments.stream()
                    .filter(payment -> payment.getUserId() == currentUser.getId())
                    .collect(Collectors.toList());
            tblPayments.setItems(FXCollections.observableArrayList(myPayments));
        }
    }

    private void setupTable() {
        MFXTableRowCell<Payment, String> examNameCell = new MFXTableRowCell<>(payment -> getExamName(payment.getExamTypeId()));
        MFXTableRowCell<Payment, Double> amountCell = new MFXTableRowCell<>(Payment::getAmount);
        MFXTableRowCell<Payment, String> paymentDateCell = new MFXTableRowCell<>(payment -> new SimpleDateFormat("yyyy-MM-dd").format(payment.getPaymentDate()));
        MFXTableRowCell<Payment, String> statusCell = new MFXTableRowCell<>(payment -> payment.getStatus().toString());

        // examNameCell.setComparator(...); // Commented out due to MaterialFX compatibility
        // amountCell.setComparator(...); // Commented out due to MaterialFX compatibility
        // paymentDateCell.setComparator(...); // Commented out due to MaterialFX compatibility
        // statusCell.setComparator(...); // Commented out due to MaterialFX compatibility

        tblPayments.getTableColumns().addAll(
                // new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Exam Name", true, examNameCell),
                // new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Amount", true, amountCell),
                // new io.github.palexdev.materialfx.controls.MFXTableColumn<>("Payment Date", true, paymentDateCell),
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

