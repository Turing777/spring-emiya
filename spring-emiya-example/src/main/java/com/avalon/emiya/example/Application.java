package com.avalon.emiya.example;

import com.avalon.emiya.core.context.ApplicationContext;
import com.avalon.emiya.core.context.DefaultApplicationContext;
import com.avalon.emiya.example.controller.ExampleEmiyaController;

/**
 *
 * @author huhao
 * @since 2019/3/11 14:57
 */
public class Application {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new DefaultApplicationContext("com.avalon.emiya.example");
        ExampleEmiyaController exampleEmiyaController = applicationContext.getBean(ExampleEmiyaController.class);
        System.out.println(exampleEmiyaController.getExample());
        System.out.println(exampleEmiyaController.getExampleEmiyaDao().getDao());
    }
}
