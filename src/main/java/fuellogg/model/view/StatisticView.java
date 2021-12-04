package fuellogg.model.view;

public class StatisticView {
    private final int authRequest;
    private final int anonymousRequest;
    private final long biggerLogTimeForLast24Hours;

    public StatisticView(int authRequest, int anonymousRequest, long biggerLogTimeForLast24Hours) {
        this.authRequest = authRequest;
        this.anonymousRequest = anonymousRequest;
        this.biggerLogTimeForLast24Hours = biggerLogTimeForLast24Hours;
    }
    public int getTotalRequest(){
        return getAnonymousRequest() + getAuthRequest();
    }

    public int getAuthRequest() {
        return authRequest;
    }


    public int getAnonymousRequest() {
        return anonymousRequest;
    }

    public long getBiggestTime() {
        return biggerLogTimeForLast24Hours;
    }
}
