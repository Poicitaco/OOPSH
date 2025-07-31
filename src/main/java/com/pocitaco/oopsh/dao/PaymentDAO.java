package com.pocitaco.oopsh.dao;

import com.pocitaco.oopsh.models.Payment;
import com.pocitaco.oopsh.enums.PaymentStatus;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class PaymentDAO extends BaseDAO<Payment, Integer> {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public PaymentDAO() {
        super("data/payments.xml", "payments");
    }

    @Override
    protected String getElementName() {
        return "payment";
    }

    @Override
    protected Payment elementToEntity(Element element) {
        Payment payment = new Payment();
        payment.setId(Integer.parseInt(getElementText(element, "id")));
        payment.setUserId(Integer.parseInt(getElementText(element, "userId")));
        payment.setExamTypeId(Integer.parseInt(getElementText(element, "examTypeId")));
        payment.setAmount(Double.parseDouble(getElementText(element, "amount")));
        try {
            payment.setPaymentDate(DATE_FORMAT.parse(getElementText(element, "paymentDate")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        payment.setStatus(PaymentStatus.valueOf(getElementText(element, "status")));
        return payment;
    }

    @Override
    protected Element entityToElement(Document doc, Payment payment) {
        Element element = doc.createElement("payment");
        setElementText(doc, element, "id", String.valueOf(payment.getId()));
        setElementText(doc, element, "userId", String.valueOf(payment.getUserId()));
        setElementText(doc, element, "examTypeId", String.valueOf(payment.getExamTypeId()));
        setElementText(doc, element, "amount", String.valueOf(payment.getAmount()));
        setElementText(doc, element, "paymentDate", DATE_FORMAT.format(payment.getPaymentDate()));
        setElementText(doc, element, "status", payment.getStatus().name());
        return element;
    }

    @Override
    protected Integer getEntityId(Payment entity) {
        return entity.getId();
    }

    @Override
    public Payment create(Payment entity) {
        lock.writeLock().lock();
        try {
            Document doc = loadDocument();
            Element root = doc.getDocumentElement();
            entity.setId(generateNextId());
            root.appendChild(entityToElement(doc, entity));
            saveDocument(doc);
            return entity;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public Optional<Payment> get(Integer id) {
        lock.readLock().lock();
        try {
            Document doc = loadDocument();
            NodeList nodes = doc.getElementsByTagName(getElementName());
            for (int i = 0; i < nodes.getLength(); i++) {
                Element element = (Element) nodes.item(i);
                if (Integer.parseInt(getElementText(element, "id")) == id) {
                    return Optional.of(elementToEntity(element));
                }
            }
            return Optional.empty();
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public Payment update(Payment entity) {
        lock.writeLock().lock();
        try {
            Document doc = loadDocument();
            Element root = doc.getDocumentElement();
            NodeList nodes = root.getElementsByTagName(getElementName());
            for (int i = 0; i < nodes.getLength(); i++) {
                Element element = (Element) nodes.item(i);
                if (Integer.parseInt(getElementText(element, "id")) == entity.getId()) {
                    root.replaceChild(entityToElement(doc, entity), element);
                    saveDocument(doc);
                    return entity;
                }
            }
            return null; // Or throw an exception if not found
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void delete(Integer id) {
        lock.writeLock().lock();
        try {
            Document doc = loadDocument();
            Element root = doc.getDocumentElement();
            NodeList nodes = root.getElementsByTagName(getElementName());
            for (int i = 0; i < nodes.getLength(); i++) {
                Element element = (Element) nodes.item(i);
                if (Integer.parseInt(getElementText(element, "id")) == id) {
                    root.removeChild(element);
                    saveDocument(doc);
                    return;
                }
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public List<Payment> getAll() {
        lock.readLock().lock();
        try {
            List<Payment> payments = new ArrayList<>();
            Document doc = loadDocument();
            NodeList nodes = doc.getElementsByTagName(getElementName());
            for (int i = 0; i < nodes.getLength(); i++) {
                payments.add(elementToEntity((Element) nodes.item(i)));
            }
            return payments;
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public Optional<Payment> findById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<Payment> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public boolean deleteById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public Payment save(Payment entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }
}