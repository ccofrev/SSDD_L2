# SSDD_L2


Desarrollo de laboratorio 2 Sistemas distribuidos
Se plantea un sistema dividido en tres aplicaciones para la obtención, 
procesamiento y extracción de datos de una api a Apache kafka y luego a caché de apache Ignite.



##kafka-ignite-api_consumer-kafka_producer
aplicativo que se conecta a una api de clima de OpenweatherMap
y publica en un topic predefinido de nombre "weather-data" repetidamente. Se utilizan otras configuraciones en sus valores por defecto.

##kafka-ignite-kafka_ignite
aplicativo que se suscribe al topic de kafka "weather-data" y publica los valores en caché de ignite.

##kafka-ignite-ignite_sql
aplicativo que realiza consultas a la caché de ignite, a través de SQL repetidamente, para demostrar su uso.








para ejecutar cualquiera de los tres desarrollos, primero se debe empaquetar con maven para luego ejecutar con comandos dispuestos para hacerlo fácilmente, así los comandos para todas las aplicaciones son

./mvn package

cd scripts/
./start-app.sh


