package com.avalon.emiya.example.controller;

import com.avalon.emiya.core.annotation.Autowired;
import com.avalon.emiya.core.annotation.Controller;
import com.avalon.emiya.example.dao.IExampleEmiyaDao;

/**
 *
 * @author huhao
 * @since 2019/3/11 15:00
 */
@Controller
public class ExampleEmiyaController {

    private String example = "example";

    @Autowired
    private IExampleEmiyaDao exampleEmiyaDao;

    public IExampleEmiyaDao getExampleEmiyaDao() {
        return exampleEmiyaDao;
    }

    public void setExampleEmiyaDao(IExampleEmiyaDao exampleEmiyaDao) {
        this.exampleEmiyaDao = exampleEmiyaDao;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }
}
