import java.util.Date;
import java.util.List;

public class MiniClass implements java.io.Serializable {
    private Date date;
    private String userName;
    private String password;
    private List<Integer> someNumbers;

    public MiniClass(Date date, String userName, String password, List<Integer> someNumbers) {
        this.date = date;
        this.userName = userName;
        this.password = password;
        this.someNumbers = someNumbers;
    }

    public Date getDate() {
        return date;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public List<Integer> getSomeNumbers() {
        return someNumbers;
    }
}