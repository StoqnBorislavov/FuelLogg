package fuellogg.service;


import fuellogg.model.view.StatisticView;

public interface StatsService {

    void onRequest();

    void countFuelHits();

    void countExpensesHits();

    void calculateTimeForLog(long endTime, long startTime);

    StatisticView getStatistic();

    void cleanUpDb();

}
