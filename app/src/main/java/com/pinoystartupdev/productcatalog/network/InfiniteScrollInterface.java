package com.pinoystartupdev.productcatalog.network;

public interface InfiniteScrollInterface<T> {
    void beforeLoadingNextItems();
    void successLoadingNextItems(T response);
    void failLoadingNextItems();
}
