package com.ssdd.kafka.ignite;

final class IgniteRepositoryFactory {

    private static IgniteRepository instance;

    static synchronized IgniteRepository getInstance() {
        if (instance == null) {
            instance = new IgniteRepository();
            instance.init();
        }
        return instance;
    }

}
