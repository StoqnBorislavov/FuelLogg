package fuellogg.model.view;

public class StatisticView {
    private final int authRequest;
    private final int anonymousRequest;
    private final int addFuelings;
    private final int addExpenses;
    private final long biggerLogTimeForLast24Hours;

    public StatisticView(int authRequest, int anonymousRequest, int addFuelings, int addExpenses, long biggerLogTimeForLast24Hours) {
        this.authRequest = authRequest;
        this.anonymousRequest = anonymousRequest;
        this.addFuelings = addFuelings;
        this.addExpenses = addExpenses;
        this.biggerLogTimeForLast24Hours = biggerLogTimeForLast24Hours;
    }
    public int getTotalRequest(){
        return getAnonymousRequest() + getAuthRequest();
    }

    public int getAuthRequest() {
        return authRequest;
    }

    public int getAddFuelings() {
        return addFuelings;
    }

    public int getAddExpenses() {
        return addExpenses;
    }

    public int getAnonymousRequest() {
        return anonymousRequest;
    }

    public long getBiggestTime() {
        return biggerLogTimeForLast24Hours;
    }
}
