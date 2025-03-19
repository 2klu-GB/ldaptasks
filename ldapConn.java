package ldaptaskfiles;
import java.util.Hashtable;
import java.util.jar.Attributes;
import javax.naming.AuthenticationException;
import javax.naming.AuthenticationNotSupportedException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class ldapConn 

{
		
			public static void main(String[] arggs) 
			{
		    System.out.println("LDAP connections");
		    }

			InitialDirContext connection;
			
			private String úid;
			
			public void searchUser(String uid) throws NamingException
			{ 
				this.úid=uid;
				System.out.println("********************");
				System.out.println("UID to search is " + uid);
				Hashtable env = new Hashtable();
			    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			    env.put(Context.PROVIDER_URL, "ldap://ldap.forumsys.com:389");        //Address of the LDAP server
			    env.put(Context.SECURITY_AUTHENTICATION, "simple");             //Authentication mechanism
			    env.put(Context.SECURITY_PRINCIPAL, "cn=read-only-admin,dc=example,dc=com");     //credentials - admin's DN
			    env.put(Context.SECURITY_CREDENTIALS, "password");                //credentials - password (it is secret by default)
			    try 
			    {
			        DirContext connection = new InitialDirContext(env);
			        System.out.println("Connection established successfully");
			        System.out.println("Connection Details are as follows: " + connection);
			       // System.out.println(connection.getEnvironment());
			        
			        String searchFilter = "(&(objectClass=inetOrgPerson)(uid=" + uid + "))";
			       // String searchFilter = "(uid=" + uid + ")";
			        System.out.println("The Search Filter is " + searchFilter);
				    String[] requiredAttributes = {"cn"};
				    SearchControls controls = new SearchControls();
				    controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
				    controls.setReturningAttributes(requiredAttributes);
					NamingEnumeration users = connection.search("ou=scientists,dc=example,dc=com", searchFilter, controls);
					System.out.println("********************");
				    SearchResult result = null;
				    System.out.println(users);
				    while (users.hasMore()) 
				    {
				    	System.out.println("************************");
				        result = (SearchResult) users.next();
				        Attributes attr = (Attributes) result.getAttributes();
				        System.out.println("************************");
				        System.out.println(attr.get("cn"));
				    }
				    System.out.println("AFTER While loop");
				    
			    } 
			    catch (AuthenticationNotSupportedException ex) 
			    {
			        System.out.println("The authentication is not supported by the server");
			    } catch (AuthenticationException ex) {
			        System.out.println("incorrect password or username");
			    } catch (NamingException ex) {
			        System.out.println("error when trying to create the context");
			    }
			    	
			}

}

