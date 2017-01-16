==========Installations================
Install Wildfly 8.x, 10.x, java 7 or higher
Install postgresql 9.x

===========Installing JDBC driver in Wildfly============
Download postgresql jbdc driver (postgresql-9.4-xxxx.jar) from http://jdbc.postgresql.org/download.html.

JBOSS_HOME/modules/org/postgresql/main/postgresql-9.4-xxxx.jar
JBOSS_HOME/modules/org/postgresql/main/module.xml

Create any needed directories to meet the above structure.

[module.xml]
<module xmlns="urn:jboss:module:1.1" name="org.postgresql">
	<resources>
		<resource-root path="postgresql-9.4.1212.jar"/>
	</resources>
	<dependencies>
		<module name="javax.api"/>
		<module name="javax.transaction.api"/>
		</dependencies>
</module>


==========Adding datasource to JBoss configuration===========
Below is the datasource section I added to 

 <datasource jndi-name="java:jboss/datasources/bookstoreDS" pool-name="bookstoreDS" enabled="true" use-java-context="true">
                    <connection-url>jdbc:postgresql://localhost:5433/bookstore</connection-url>
                    <driver>postgresql</driver>
                    <pool>
                        <min-pool-size>10</min-pool-size>
                        <max-pool-size>20</max-pool-size>
                        <prefill>true</prefill>
                    </pool>
                    <security>
                        <user-name>jboss</user-name>
                        <password>jboss</password>
                    </security>
                </datasource>
                <drivers>
                    <driver name="postgresql" module="org.postgresql">
                        <driver-class>org.postgresql.Driver</driver-class>
                    </driver>
                </drivers>

===========Running the application=======================
mvn clean package wildfly:deploy

