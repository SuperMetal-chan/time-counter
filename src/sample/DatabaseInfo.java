package sample;

public class DatabaseInfo
{
    private String name;
    private int time;
    private String time_string;

    public DatabaseInfo(String name, int time, String time_string)
    {
        this.name = name;
        this.time = time;
        this.time_string = time_string;
    }

    public String getName()
    {
    return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getTime()
    {
        return time;
    }

    public void setTime(int time)
    {
        this.time = time;
    }

    public String getTime_string()
    {
        return time_string;
    }

    public void setTime_string(String time_string)
    {
        this.time_string = time_string;
    }
}
