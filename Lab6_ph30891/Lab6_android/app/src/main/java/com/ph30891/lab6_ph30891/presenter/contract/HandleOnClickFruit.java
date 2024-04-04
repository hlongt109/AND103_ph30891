package com.ph30891.lab6_ph30891.presenter.contract;

import com.ph30891.lab6_ph30891.model.Fruit;

public interface HandleOnClickFruit {
    void details(String id);
    void delete(String id);
    void update(Fruit fruit);
}
