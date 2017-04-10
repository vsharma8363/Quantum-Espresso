import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class NetworkTablesServer {

	private static NetworkTable table;
	private static String tableName;
	
	public NetworkTablesServer()
	{
		tableName = "vision";
		table = NetworkTable.getTable(tableName);
	}
	
	public void sendData(String data)
	{
		table.putString("data", data);
	}
}
