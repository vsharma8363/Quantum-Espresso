import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class NetworkTablesClient {

	private static String tableName;
	private NetworkTable table;
	
	public NetworkTablesClient(String ipAddress)
	{
		tableName = "vision";
		NetworkTable.setClientMode();
		NetworkTable.setIPAddress(ipAddress);
		table = NetworkTable.getTable(tableName);
	}
	
	public String getData()
	{
		return table.getString("data", null);
	}
	
}
