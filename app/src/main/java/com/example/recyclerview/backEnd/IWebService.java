package com.example.recyclerview.backEnd;



import com.example.recyclerview.util.ICallback;

import java.util.List;

public interface IWebService<T> {

    void getEntities(final ICallback<List<T>> callback);

    void getEntities(final int startRange, final int endRange, final ICallback<List<T>> callback);

    void removeEntity(final Long id);

    void addEntity(final String name, final int hwCount);

    void editEntity(final Long id, final String name, final int hwCount);
}
