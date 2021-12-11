package fuellogg.service.impl;

import fuellogg.model.entity.AdminStat;
import fuellogg.model.view.StatisticView;
import fuellogg.repository.AdminStatRepository;
import fuellogg.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Service
public class StatsServiceImpl implements StatsService {
    public static final Long MILLISECONDS_FROM_24_HOUR = 86400000L;

    private final AdminStatRepository adminStatRepository;

    private int countFuelings;
    private int countExpenses;
    private int anonymousRequest;
    private int authRequest;

    @Autowired
    public StatsServiceImpl( AdminStatRepository adminStatRepository) {
        this.adminStatRepository = adminStatRepository;
    }

    @Override
    public void onRequest() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AdminStat adminStat = new AdminStat();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            authRequest++;
            adminStat.setAuthenticatedRequest(authRequest);
        } else {
            anonymousRequest++;
            adminStat.setAuthenticatedRequest(anonymousRequest);
        }
        this.adminStatRepository.save(adminStat);
    }

    @Override
    public void countFuelHits() {
        this.adminStatRepository.save(new AdminStat().setRequestOnFuelAdd(++countFuelings));
    }

    @Override
    public void countExpensesHits() {
        this.adminStatRepository.save(new AdminStat().setGetRequestOnExpenseAdd(++countExpenses));
    }


    @Override
    public void calculateTimeForLog(long endTime, long startTime) {
        AdminStat adminStat = new AdminStat();
        adminStat.setLogTime(endTime-startTime);
        this.adminStatRepository.save(adminStat);
    }

    @Override
    @Transactional
    public StatisticView getStatistic() {
        Instant newInst = Instant.now().minusMillis(MILLISECONDS_FROM_24_HOUR);
        AdminStat adminStat = this.adminStatRepository.findTopByCreatedAfterOrderByLogTimeDesc(newInst)
                .orElseThrow(()-> new UnsupportedOperationException("There is no log times"));
        if(adminStat.getLogTime() == null){
            return new StatisticView(authRequest, anonymousRequest, countFuelings, countExpenses, 0);
        }
        return new StatisticView(authRequest, anonymousRequest, countFuelings, countExpenses, adminStat.getLogTime());
    }

    @Transactional
    @Override
    public void cleanUpDb() {
        LocalDateTime now = LocalDateTime.now().minusMinutes(1);
        LocalDateTime before24Hours = now.minusHours(24);
        this.adminStatRepository.deleteAllByCreatedBetween(before24Hours.toInstant(ZoneOffset.UTC), now.toInstant(ZoneOffset.UTC));
    }


}
