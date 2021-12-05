package fuellogg.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "admin_stats")
public class AdminStat extends BaseEntity{
    private Long logTime;
    private int authenticatedRequest;
    private int anonymousRequest;
    private int requestOnFuelAdd;
    private int getRequestOnExpenseAdd;

    @Column
    public int getRequestOnFuelAdd() {
        return requestOnFuelAdd;
    }

    public AdminStat setRequestOnFuelAdd(int requestOnFuelAdd) {
        this.requestOnFuelAdd = requestOnFuelAdd;
        return this;
    }
    @Column
    public int getGetRequestOnExpenseAdd() {
        return getRequestOnExpenseAdd;
    }

    public AdminStat setGetRequestOnExpenseAdd(int getRequestOnExpenseAdd) {
        this.getRequestOnExpenseAdd = getRequestOnExpenseAdd;
        return this;
    }

    @Column
    public Long getLogTime() {
        return logTime;
    }

    public AdminStat setLogTime(Long logTime) {
        this.logTime = logTime;
        return this;
    }
    @Column(name = "authenticated_request")
    public int getAuthenticatedRequest() {
        return authenticatedRequest;
    }

    public AdminStat setAuthenticatedRequest(int authenticatedRequest) {
        this.authenticatedRequest = authenticatedRequest;
        return this;
    }
    @Column(name = "anonymous_request")
    public int getAnonymousRequest() {
        return anonymousRequest;
    }

    public AdminStat setAnonymousRequest(int anonymousRequest) {
        this.anonymousRequest = anonymousRequest;
        return this;
    }
}
