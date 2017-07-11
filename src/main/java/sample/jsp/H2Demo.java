package sample.jsp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class H2Demo {
    private Server server;
   
    public static String port="8082";
    
    public static String dbDir="jdbc:h2:tcp://localhost:19200/~/test";
  
    public static String user="sa";
//    public static String port;
//    
//    public static String dbDir;
//  
//    public static String user;
    public static String password;
    
    @Value("${h2.port}")
	public  void setPort(String port) {
		H2Demo.port = port;
	}
    @Value("${h2.url}")
	public  void setDbDir(String dbDir) {
		H2Demo.dbDir = dbDir;
	}
    @Value("${h2.user}")
	public  void setUser(String user) {
		H2Demo.user = user;
	}
    @Value("${h2.password}")
	public  void setPassword(String password) {
		H2Demo.password = password;
	}


	public void startServer() {
        try {
            server = Server.createTcpServer(
                    new String[] { "-tcpPort", port }).start();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public void stopServer() {
        if (server != null) {
            server.stop();
        }
    }
    public static Connection getConnection(){
    	Connection conn =null;
    	try {
    		Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection(dbDir,user,password);
		  }catch (Exception e) {
			  e.printStackTrace();
		}
    	return conn;
    }
    public static Connection getConnection(String url,String user,String password){
    	Connection conn =null;
    	try {
    		Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection(url,user,password);
		  }catch (Exception e) {
			  e.printStackTrace();
		}
    	return conn;
    }
    public void useH2() {
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection(dbDir,user,password);
            Statement stat = conn.createStatement();
            // insert data
           stat.execute("create table meat(no int,sampleType varchar,checkItem varchar,checkDate varchar,checkPlace varchar,checkPerson varchar,concentration varchar,value varchar,result varchar,branchNO varchar,getTime varchar);");
           stat.execute("create table vegetables(no int,sampleName varchar,sampleType varchar,checkItem varchar,checkDate varchar,coverUnit varchar,checkPlace varchar,value varchar,result varchar,checkUnit varchar,branchNO varchar,getTime varchar);");
           //  stat.execute("INSERT INTO TESTZL VALUES('Hello World')");
            // use data
//            ResultSet result = stat.executeQuery("select name from TESTZL ");
//            int i = 1;
//            while (result.next()) {
//                System.out.println(i++ + ":" + result.getString("name"));
//            }
//            result.close();
            stat.close();
            conn.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        H2Demo h2 = new H2Demo();
      //  h2.startServer();
        h2.useH2();
        h2.stopServer();
        System.out.println("==END==");
    }
}
