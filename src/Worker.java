public class Worker extends User {
    private Options rank;
    public Worker(String firstName, String lastName, String userName, String password) {
        super(firstName, lastName, userName, password);
    }

    public Options getRank() {
        return rank;
    }

    public void setRank(Options rank) {
        this.rank = rank;
    }

}
