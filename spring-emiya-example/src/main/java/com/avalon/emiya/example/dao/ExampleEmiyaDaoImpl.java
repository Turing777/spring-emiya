package com.avalon.emiya.example.dao;

import com.avalon.emiya.core.annotation.Repository;

/**
 *
 * @author huhao
 * @since 2019/3/11 15:01
 */
@Repository
public class ExampleEmiyaDaoImpl implements IExampleEmiyaDao {

    private String dao = "dao";

    @Override
    public String getDao() {
        return dao;
    }

    public void setDao(String dao) {
        this.dao = dao;
    }
}
