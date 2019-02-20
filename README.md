Execute program from UnitLanguage directory.

COMPILATION:
mvn clean install

RUN:
mvn exec:java -Dexec.mainClass="com.smomic.Interpreter" -Dexec.args="arg1 arg2"  
