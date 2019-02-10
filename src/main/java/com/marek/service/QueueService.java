package com.marek.service;

import com.marek.repository.WebContentRepository;
import com.marek.model.WebContent;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.LinkedBlockingQueue;

@Singleton
public class QueueService implements Runnable {

    private static LinkedBlockingQueue<String> tasks = new LinkedBlockingQueue<>();

    @Inject
    WebContentService webContentService;

    @Inject
    WebContentRepository webContentRepository;

    public QueueService() {
       new Thread(this).start();
    }

    public boolean addToQueue(String url) {
        return !tasks.contains(url) && tasks.offer(url);
    }

    @Override
    public void run() {
        while(true){
            try{
               String url = tasks.take();
                WebContent webContent = webContentService.getWebContent(url);
                webContentRepository.save(webContent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
