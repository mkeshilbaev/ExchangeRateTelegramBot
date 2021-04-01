package domain;

public class ExchangeRate {
    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getKzt() {
        return kzt;
    }

    public void setKzt(Double rates) {
        this.kzt = rates;
    }

    private String date;
    private String base;
    private Double kzt;
}
