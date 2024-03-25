package com.ph30891.lab5_retrofit.presenter.contract;

import com.ph30891.lab5_retrofit.model.Distributor;

public interface Item_Distributor_handle {
    void Delete(String id);
    void Update(String id, Distributor distributor);
}
