package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class DatabaseHandler extends Config
{
    private Connection getDbConnection() throws ClassNotFoundException, SQLException
    {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName
                + "?autoReconnect=true&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&" +
                "useLegacyDatetimeCode=false&serverTimezone=UTC";


        Class.forName("com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection(connectionString, dbUser, dbPass);
    }

    public void MakeNewRow(DatabaseInfo databaseInfo)
    {
        String insert = "INSERT INTO " + Constant.TIME_COUNTER_TABLE + "(" + Constant.DATABASE_NAME + "," +
                Constant.DATABASE_TIME_INT + "," + Constant.DATABASE_TIME_STRING + ") VALUES(?,?,?)";

        try
        {
            PreparedStatement prepStat = getDbConnection().prepareStatement(insert);
            prepStat.setString(1, databaseInfo.getName());
            prepStat.setInt(2, databaseInfo.getTime());
            prepStat.setString(3, databaseInfo.getTime_string());

            prepStat.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

    }


    public void ChangeRow(DatabaseInfo databaseInfo) throws SQLException, ClassNotFoundException
    {
        String select = "UPDATE " + Constant.TIME_COUNTER_TABLE + " SET " + Constant.DATABASE_TIME_INT + "=?,"
                + Constant.DATABASE_TIME_STRING + "=? WHERE " + Constant.DATABASE_NAME + "=?";

        PreparedStatement prepStat = getDbConnection().prepareStatement(select);

        prepStat.setString(3, databaseInfo.getName());
        prepStat.setInt(1, databaseInfo.getTime());
        prepStat.setString(2, databaseInfo.getTime_string());
        prepStat.executeUpdate();
    }

    public ResultSet CheckName(DatabaseInfo databaseInfo) throws SQLException, ClassNotFoundException
    {
        String select = "SELECT * FROM " + Constant.TIME_COUNTER_TABLE + " WHERE " +
                Constant.DATABASE_NAME + "=?";

        PreparedStatement prepStat = getDbConnection().prepareStatement(select);

        prepStat.setString(1, databaseInfo.getName());

        ResultSet resultSet = prepStat.executeQuery();

        return resultSet;
    }

/*
    public ResultSet CheckEmail(User user) throws SQLException, ClassNotFoundException
    {
        String select = "SELECT * FROM " + Constant.USER_TABLE + " WHERE " + Constant.USER_EMAIL + "=?";

        PreparedStatement prepStat = getDbConnection().prepareStatement(select);

        prepStat.setString(1, user.getEmail());

        ResultSet resultSet = prepStat.executeQuery();

        return resultSet;
    }

    public ResultSet CheckPhone(User user) throws SQLException, ClassNotFoundException
    {
        String select = "SELECT * FROM " + Constant.USER_TABLE + " WHERE " + Constant.USER_PHONE+ "=?";

        PreparedStatement prepStat = getDbConnection().prepareStatement(select);

        prepStat.setString(1, user.getPhone());

        ResultSet resultSet = prepStat.executeQuery();

        return resultSet;
    }

    public ResultSet CheckPassword(User user) throws SQLException, ClassNotFoundException
    {
        String select = "SELECT * FROM " + Constant.USER_TABLE + " WHERE " + Constant.USER_PASSWORD + "=?";

        PreparedStatement prepStat = getDbConnection().prepareStatement(select);

        prepStat.setString(1, user.getPassword());

        ResultSet resultSet = prepStat.executeQuery();

        return resultSet;
    }
*/

}
