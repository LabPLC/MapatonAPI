set PATH=C:\Program Files\Java\jdk1.7.0_79\bin;%PATH%
call cd..
call mvn clean compile
call mvn appengine:endpoints_get_client_lib
call javac -d . src/main/java/mx/krieger/labplc/mapaton/utils/APIClientsUpdateHelper.java
call java -cp . mx.krieger.labplc.mapaton.utils.APIClientsUpdateHelper
call cd target/endpoints-client-libs/mapatonAPI 
call mvn install
call cd ../../..
call rmdir mx /S /Q 