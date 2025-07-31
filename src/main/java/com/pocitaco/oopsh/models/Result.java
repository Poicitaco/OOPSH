package com.pocitaco.oopsh.models;

import com.pocitaco.oopsh.enums.ResultStatus;

import java.util.Date;

public class Result {
    private int id;
    private int userId;
    private int examTypeId;
    private int theoryScore;
    private int practiceScore;
    private Date examDate;
    private ResultStatus status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getExamTypeId() {
        return examTypeId;
    }

    public void setExamTypeId(int examTypeId) {
        this.examTypeId = examTypeId;
    }

    public int getTheoryScore() {
        return theoryScore;
    }

    public void setTheoryScore(int theoryScore) {
        this.theoryScore = theoryScore;
    }

    public int getPracticeScore() {
        return practiceScore;
    }

    public void setPracticeScore(int practiceScore) {
        this.practiceScore = practiceScore;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public ResultStatus getStatus() {
        return status;
    }

    public void setStatus(ResultStatus status) {
        this.status = status;
    }
}
