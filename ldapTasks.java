package ldaptaskfiles;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;

public class ldapTasks 

{

	public static void main(String[] args) throws NamingException 
	{
		InitialDirContext connection;
		ldapConn newconn = new ldapConn();
		String uid = "newton";
	    newconn.searchUser(uid);

	}

}
