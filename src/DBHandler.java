
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
public class DBHandler {

	
	    public Connection establishConnection() throws IOException, SQLException, ClassNotFoundException {
		Properties p=new Properties();
		FileInputStream f=new FileInputStream("db.properties");
		p.load(f);
		Class.forName(p.getProperty("db.classname"));
		String url=p.getProperty("db.url");
		String username=p.getProperty("db.username");
		String password=p.getProperty("db.password");
		Connection c=DriverManager.getConnection(url,username,password);
		return c;
	}
}
