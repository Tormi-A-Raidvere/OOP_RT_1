public class PakkEiEksisteeriErind extends RuntimeException {
    public PakkEiEksisteeriErind(String pakiNimi) {
        super("Kaardipakki " + pakiNimi + " ei eksisteeri!");
    }
}