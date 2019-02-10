package com.marek.MyApplication;

import com.marek.repository.WebContentRepository;
import com.marek.service.QueueService;
import com.marek.service.WebContentService;
import org.glassfish.jersey.internal.inject.AbstractBinder;

import javax.inject.Singleton;

public class MyApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(WebContentRepository.class).to(WebContentRepository.class);
        bind(QueueService.class).to(QueueService.class).in(Singleton.class);
        bind(WebContentService.class).to(WebContentService.class);
    }
}
