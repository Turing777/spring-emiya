package com.avalon.emiya.example.controller;

import com.avalon.emiya.core.annotation.Controller;

/**
 *
 * @author huhao
 * @since 2019/3/11 15:00
 */
@Controller
public class ExampleEmiyaController {

    private String example = "example";

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }
}
