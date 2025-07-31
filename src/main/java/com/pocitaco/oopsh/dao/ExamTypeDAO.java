package com.pocitaco.oopsh.dao;

import com.pocitaco.oopsh.models.ExamType;

import java.util.ArrayList;
import java.util.List;

public class ExamTypeDAO extends BaseDAO<ExamType> {

    public ExamTypeDAO() {
        super(ExamType.class);
    }

    @Override
    protected String getFilePath() {
        return "data/exam_types.xml";
    }

    @Override
    protected int getNextId(List<ExamType> list) {
        return list.stream().mapToInt(ExamType::getId).max().orElse(0) + 1;
    }
}
