package clemadr06.litclub;

public class gdata {
    public static String token;
    public static int id;
public static Boolean check=false;

public void setBoolean(Boolean s)
{
    check=s;
}

    public Boolean getCheck() {
        return check;
    }

    public void setToken(String t) {
        token = t;
    }

    public void setId(int i) {
        id=i;
    }

    public String getToken() {
        return token;
    }
    public int getid(){return id;}
}
