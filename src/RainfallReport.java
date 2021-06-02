
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RainfallReport {

	
	public List<AnnualRainfall> generateRainfallReport(String filePath) throws IOException {
	    
		List<AnnualRainfall> avgList=new ArrayList<>();
		FileReader fr = new FileReader(new File(filePath));
		BufferedReader br = new BufferedReader(fr);
		String l;
		while((l=br.readLine())!=null)
		{
			String[] a=l.split(",");
			String pincode=a[0];
			try
			{
				if(validate(pincode))
				{
					double[] monthlyRainFall=new double[12];
					for(int i=2;i<=13;i++)
					{
						monthlyRainFall[i-2]=Double.parseDouble(a[i]);	
					}
					AnnualRainfall ar=new AnnualRainfall();
					ar.calculateAverageAnnualRainfall(monthlyRainFall);
					ar.setCityName(a[1]);
					ar.setCityPincode(Integer.parseInt(pincode));
					avgList.add(ar);
				}
			}
			catch(InvalidCityPincodeException e)
			{
				System.out.println(e.getMessage());
			}
		}
		br.close();
		return avgList;
	}
	
	public List<AnnualRainfall>  findMaximumRainfallCities() throws SQLException, ClassNotFoundException, IOException  {
		DBHandler d=new DBHandler();
		List<AnnualRainfall> finalList=new ArrayList<>();
		Connection c=d.establishConnection();
		Statement s=c.createStatement();
		String sql = "SELECT * FROM ANNUALRAINFALL WHERE AVERAGE_ANNUAL_RAINFALL IN (SELECT MAX(AVERAGE_ANNUAL_RAINFALL) FROM ANNUALRAINFALL)";
		ResultSet rs=s.executeQuery(sql);
		while(rs.next())
		{
			AnnualRainfall ar=new AnnualRainfall();
			ar.setCityName(rs.getString(2));
			ar.setCityPincode(Integer.parseInt(rs.getString(1)));
			ar.setAverageAnnualRainfall(Double.parseDouble(rs.getString(3)));
			finalList.add(ar);
		}
		return finalList;
	}
	
	
	public boolean validate(String cityPincode) throws InvalidCityPincodeException {
		if(cityPincode.length()==5)
		{
			return true;
		}
		else
		{
			throw new InvalidCityPincodeException("Invalid CityPincode Exception");
		}	
	}
}

