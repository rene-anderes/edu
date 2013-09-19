A Server object acts as a network database server and is one way of using the 
client-server mode of HSQLDB Database Engine. Instances of this class handle 
native HSQL protocol connections exclusively, allowing database queries to be 
performed efficienly across the network. Server's direct descendent, WebServer,
handles HTTP protocol connections exclusively, allowing HSQL protocol to be 
tunneled over HTTP to avoid sandbox and firewall issues, albeit less 
efficiently.

There are a number of ways to configure and start a Server instance.

When started from the command line or programatically via the main(String[]) 
method, configuration occurs in three phases, with later phases overriding 
properties set by previous phases:
- Upon construction, a Server object is assigned a set of default properties.
- If it exists, properties are loaded from a file named 'server.properties' 
  in the present working directory.
- The command line arguments (alternatively, the String[] passed to main()) 
  are parsed and used to further configure the Server's properties. 
 
 From the command line, the options are as follows:

 +-----------------+-------------+----------+------------------------------+
 |    OPTION       |    TYPE     | DEFAULT  |         DESCRIPTION          |
 +-----------------+-------------+----------+------------------------------|
 | --help          |             |          | prints this message          |
 | --address       | name|number | any      | server inet address          |
 | --port          | number      | 9001/544 | port at which server listens |
 | --database.i    | [type]spec  | 0=test   | path of database i           |
 | --dbname.i      | alias       |          | url alias for database i     |
 | --silent        | true|false  | true     | false => display all queries |
 | --trace         | true|false  | false    | display JDBC trace messages  |
 | --tls           | true|false  | false    | TLS/SSL (secure) sockets     |
 | --no_system_exit| true|false  | false    | do not issue System.exit()   |
 | --remote_open   | true|false  | false    | can open databases remotely  |
 | --props         | filepath    |          | file path of properties file |
 +-----------------+-------------+----------+------------------------------+
 
 
 More Informations: http://hsqldb.org/doc/src/org/hsqldb/server/Server.html