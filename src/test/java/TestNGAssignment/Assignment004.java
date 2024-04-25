package TestNGAssignment;

import io.restassured.response.Response;

import java.sql.*;

import static io.restassured.RestAssured.get;

public class Assignment004 {
    Connection connection;
    public static void main(String[] args) {
        Assignment004 firstDB = new Assignment004();
        firstDB.createNewDB();
        firstDB.createEmptyTable();
        firstDB.insertRecordIntoTable();
        firstDB.readTable();
    }
    public void createNewDB(){
        try
        {
            //URL, DB username and DB password
            connection= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306","sunilk","password");
            if (connection!=null)
            {
                System.out.println("Database server is connected");
            }
            Statement statement = connection.createStatement(); // creating statement obj

            statement.execute("DROP DATABASE IF EXISTS CompaniesRecord;");
            statement.execute(" create database CompaniesRecord");//using that statement obj, creating database
            System.out.println("CompaniesRecord Database is created!");
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
    public void createEmptyTable(){
        try
        {
            //URL, DB username and DB password
            connection=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306","sunilk","password");
            if (connection!=null)
            {
                System.out.println("Database server is connected");
            }
            Statement statement = connection.createStatement(); // creating statement obj
            statement.execute(" use CompaniesRecord");//using that statement obj, to use database
            System.out.println("You are using CompaniesRecord DB ");

            String Companies_DATATableCreateQuery = "CREATE TABLE Companies_DATA (\n" +
                    "         id      INT   NOT NULL,  \n" +
                    "         name  VARCHAR(50)            NOT NULL,\n" +
                    "         address  VARCHAR(100)     NOT NULL,\n" +
                    "         zip   VARCHAR(50)     NOT NULL,\n" +
                    "         country      VARCHAR(50)  NOT NULL,  \n" +
                    "         employeeCount   BIGINT     NOT NULL,\n" +
                    "         industry      VARCHAR(50)  NOT NULL,  \n" +
                    "         marketCap   BIGINT     NOT NULL,\n" +
                    "         domain      VARCHAR(50)  NOT NULL,  \n" +
                    "         logo      VARCHAR(100)  NOT NULL,  \n" +
                    "         ceoName      VARCHAR(50)  NOT NULL  \n" +
                    "     );";
            statement.execute("DROP TABLE IF EXISTS Companies_DATA;");
            statement.execute(Companies_DATATableCreateQuery);
            System.out.println("Companies_DATA table is created successfully");

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public void insertRecordIntoTable(){
        try {
            //URL, DB username and DB password
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", "sunilk", "password");
            if (connection != null) {
                System.out.println("Database server is connected");
            }
            Statement statement = connection.createStatement(); // creating statement obj
            statement.execute(" use CompaniesRecord");

            Response response = get("https://fake-json-api.mock.beeceptor.com/companies");
            int id_path_count = response.getBody().jsonPath().getList("id").size();
            for (int i = 0; i < id_path_count; i++) {
                String id = response.getBody().jsonPath().getString("id["+i+"]");
                String name = response.getBody().jsonPath().getString("name["+i+"]");
                String address = response.getBody().jsonPath().getString("address[" + i + "]");
                String zip = response.getBody().jsonPath().getString("zip[" + i + "]");
                String country = response.getBody().jsonPath().getString("country[" + i + "]");
                String employeeCount = response.getBody().jsonPath().getString("employeeCount[" + i + "]");
                String industry = response.getBody().jsonPath().getString("industry[" + i + "]");
                String marketCap = response.getBody().jsonPath().getString("marketCap[" + i + "]");
                String domain = response.getBody().jsonPath().getString("domain[" + i + "]");
                String logo = response.getBody().jsonPath().getString("logo[" + i + "]");
                String ceoName = response.getBody().jsonPath().getString("ceoName[" + i + "]");


                String insertIntoTable = "INSERT INTO Companies_DATA (id, name, address, zip, country, employeeCount,industry,marketCap,domain,logo,ceoName)\n" +
                        "VALUES \n" +
                        "('"+id+"', '"+name+"', '"+address+"', '"+zip+"', '"+country+"', '"+employeeCount+"', '"+industry+"', '"+marketCap+"','"+domain+"','"+logo+"','"+ceoName+"');";
                statement.execute(insertIntoTable);

            }
            System.out.println(+id_path_count+" values inserted into Companies_DATA table successfully");
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
    public void readTable(){
        try {
            //URL, DB username and DB password
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", "sunilk", "password");
            if (connection != null) {
                System.out.println("Database server is connected");
            }
            Statement statement = connection.createStatement(); // creating statement obj
            statement.execute(" use CompaniesRecord");//using that statement obj, to use database
            System.out.println("You are using CompaniesRecord DB ");
            String id = "5";
            ResultSet Sqlresult = statement.executeQuery("select * from Companies_DATA where id =\""+id+"\";");
            while (Sqlresult.next())
            {
                String runtimeName = Sqlresult.getString("ceoName");
                System.out.println("if id is " +id+ " then CEO name is: " +runtimeName);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

    }

}

  /* Output
        Database server is connected
Database server is connected
You are using CompaniesRecord DB
Companies_DATA table is created successfully
Database server is connected
11 values inserted into Companies_DATA table successfully
         */
