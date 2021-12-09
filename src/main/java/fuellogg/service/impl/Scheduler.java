package fuellogg.service.impl;

import fuellogg.service.StatsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class Scheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(Scheduler.class);

    private final StatsService statsService;

    @Autowired
    public Scheduler(StatsService statsService) {
        this.statsService = statsService;
    }


    @Scheduled(cron = "0 0 0 * * *")
    public void enableUsers() {
        LOGGER.info("Clean up is engaged!");
        statsService.cleanUpDb();
    }

}
