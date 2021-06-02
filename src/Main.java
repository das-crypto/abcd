
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Main {

   public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException { 
   
	   List<AnnualRainfall> li = new ArrayList<>();
		RainfallReport r = new RainfallReport();
		li = r.generateRainfallReport("AllCityMonthlyRainfall.txt");
		Connection con = null;
		Statement st = null;
		DBHandler d=new DBHandler();
		con = d.establishConnection();
		st = con.createStatement();

		for (int i = 0; i < li.size(); i++) {
            String sql = "INSERT INTO ANNUALRAINFALL VALUES(" + li.get(i).getCityPincode() + "," + "'" + li.get(i).getCityName() + "'" + "," + li.get(i).getAverageAnnualRainfall() + ")" + "on duplicate key update city_name=" + "'" + li.get(i).getCityName() + "'" + "," + "average_annual_rainfall = " + li.get(i).getAverageAnnualRainfall() + ";" ;
            st.executeUpdate(sql);
        }
		List<AnnualRainfall> finalList=new ArrayList<>();
		finalList = r.findMaximumRainfallCities();
		for (int i = 0; i < finalList.size(); i++) {
			System.out.println(finalList.get(i).getCityName());
		}
	   
   }
}

