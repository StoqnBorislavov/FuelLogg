package fuellogg.service.impl;

import fuellogg.model.Consts;
import fuellogg.model.entity.AdminStat;
import fuellogg.model.view.StatisticView;
import fuellogg.repository.AdminStatRepository;
import fuellogg.service.StatsService;
import fuellogg.web.interceptor.LogInterceptor;
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



    private final AdminStatRepository adminStatRepository;


    private int anonymousRequest;
    private int authRequest;

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
    public void calculateTimeForLog(long endTime, long startTime) {
        AdminStat adminStat = new AdminStat();
        adminStat.setLogTime(endTime-startTime);
        this.adminStatRepository.save(adminStat);
    }

    @Override
    public StatisticView getStatistic() {
        Instant newInst = Instant.now().minusMillis(Consts.MILLISECONDS_FROM_24_HOUR);;
        AdminStat adminStat = this.adminStatRepository.findTopByCreatedAfterOrderByLogTimeDesc(newInst)
                .orElseThrow(()-> new UnsupportedOperationException("There is no log times"));

        return new StatisticView(authRequest, anonymousRequest, adminStat.getLogTime());
    }

    @Transactional
    @Override
    public void cleanUpDb() {
        LocalDateTime now = LocalDateTime.now().minusMinutes(1);
        LocalDateTime before24Hours = now.minusHours(24);
        this.adminStatRepository.deleteAllByCreatedBetween(before24Hours.toInstant(ZoneOffset.UTC), now.toInstant(ZoneOffset.UTC));
    }


}
