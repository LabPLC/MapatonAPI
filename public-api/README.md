Mapaton - API Pública
==================

##API pública

Para más información acerca del API es posible utilizar la herramienta
[API explorer](https://public-api-dot-mapaton-public.appspot.com/_ah/api/explorer) del proyecto, la cual contiene
una lista de los métodos utilizables, así como sus parámetros y retornos. El API explorer cuenta con un editor de JSON
para realizar las peticiones y ayuda a saber qué parámetros se utilizan en cada método.
Dentro de Mapaton Public API es posible ver los métodos expuestos:
 * mapatonPublicAPI.getAllGtfsTrails
 * mapatonPublicAPI.getAllTrails
 * mapatonPublicAPI.getAllValidTrails
 * mapatonPublicAPI.getTrailDetails
 * mapatonPublicAPI.getTrailRawPoints
 * mapatonPublicAPI.getTrailSnappedPoints
 * mapatonPublicAPI.getTrailsByStationName
 * mapatonPublicAPI.registerGtfsFullTask
 * mapatonPublicAPI.registerGtfsTask
 * mapatonPublicAPI.trailsNearPoint

## Descripción de cada método

### mapatonPublicAPI.getAllTrails, mapatonPublicAPI.getAllValidTrails, mapatonPublicAPI.getAllGtfsTrails,

Métodos utilizados para obtener todos los recorridos guardados, los recorridos se encuentran paginados y es necesario indicar al servidor el tamaño y el número de la página.

1. Parámetros:

   * __cursor__: Una cadena de texto que puede estar vacía que le indica al servidor el número de la página que se desea obtener.  Para obtener la primer página se llama con una cadena vacía, para páginas posteriores, se utiliza una cadena que el servidor regresa después de cada petición llamada también _cursor_
   * __numberOfElements__: Un entero que indica el número de elementos que uno desea que el servidor regrese

2. Retornos:

   * __cursor__: Una cadena de texto utilizada para mandar al servidor y obtener el siguiente grupo de elementos paginados.
   * __trails__: Un arreglo de recorridos del número de elementos solicitado por __numberOfElements__. Los recorridos tienen todos los datos de recorrido detallados en _mapatonPublicAPI.getTrailDetails_.


### mapatonPublicAPI.getTrailDetails

Método utilizado para obtener todos los detalles de un recorrido en particular

1. Parámetros:

   * __trailId__: El identificador único del recorrido

2. Retornos:

   * __trailId__: El identificador único del recorrido
   * __originPlatformId__: El identificador único de la plataforma de origen
   * __originPlatformName__: El nombre de la plataforma de origen
   * __originStationId__: El identificador único de la estación de origen
   * __originStationName__: El nombre de la estación de origen
   * __destinationPlatformId__: El identificador único de la plataforma de destino
   * __destinationPlatformName__: El nombre de la plataforma de destino
   * __destinationStationId__: El identificador único de la estación de origen
   * __destinationStationName__: El nombre de la estación de destino
   * __transportType__: El tipo de transporte registrado por el usuario
   * __maxTariff__: La tarifa máxima registrada por el usuario
   * __photoUrl__: El URL de la foto del autobús/vagoneta tomada por el usuario
   * __schedule__: El horario registrado por el usuario
   * __notes__: Una serie de notas registradas por el usuario
   * __creationDate__: La fecha de registro del recorrido
   * __trailStatus__: El status actual del recorrido registrado, puede ser:
     0. Válido
     1. Inválido por ser muy corto (distancia menor a 5 kilómetros o tiempo menor a 15 minutos)
     2. Inválido por ser registrado fuera de las fechas del concurso
     3. Inválido por otras razones
   * __invalidReason__: En caso de ser _Inválido por otras razones_, la justificación de esa invalidez
   * __totalMinutes__: Tiempo en minutos del recorrido (Para getAllTrails es 0 por causas de eficiencia)
   * __totalMeters__: Distancia en metros del recorrido (Para getAllTrails es 0 por causas de eficiencia)

### mapatonPublicAPI.getTrailRawPoints

Método utilizado para obtener todos los puntos registrados en MapatónCDMX por un usuario.

1. Parámetros:

   * __trailId__: El identificador único del recorrido del cual se quiere obtener los puntos
   * __cursor__: Una cadena de texto que puede estar vacía que le indica al servidor el número de la página que se desea obtener.
   Para obtener la primer página se llama con una cadena vacía, para páginas posteriores, se utiliza una cadena que el servidor regresa después de cada petición llamada también __cursor__
   * __numberOfElements__: Un entero que indica el número de elementos que uno desea que el servidor regrese

2. Retornos:
    * __cursor__: Una cadena de texto utilizada para mandar al servidor y obtener el siguiente grupo de elementos paginados.
    * __points__: Un arreglo de puntos del número de elementos solicitado por _numberOfElements_ y ordenados de manera cronológica. Los puntos tienen los siguientes datos:
     * __location__: Es la ubicación geográfica del punto registrado, contiene lo siguiente:
       * __latitude__: La latitud del punto registrado
       * __longitude__: La longitud del punto registrado
       * __radius__: El radio de precisión del punto registrado
    * __timeStamp__: Es el tiempo en el que el punto fue registrado, contiene lo siguiente:
      * __time__: La tiempo del día en la que el punto fue registrado, contiene lo siguiente:
        * __hour__: La hora en la que el punto fue registrado
        * __minute__: El minuto en el que el punto fue registrado
        * __second__: El segundo en el que el punto fue registrado
      * __date__: La fecha en la que el punto fue registrado
        * __day__: El día en el que el punto fue registrado
        * __month__: El mes en el que el punto fue registrado
        * __year__: El año en el que el punto fue registrado
    * __position__: La posición en del punto respecto a otros (no utilizado en _mapatonPublicAPI.getTrailRawPoints_)

### mapatonPublicAPI.getTrailSnappedPoints

Método utilizado para obtener todos los puntos registrados en MapatónCDMX por un usuario, y pegados a un camino o calle a través de Google SnapToRoad API.

1. Parámetros:
   * __trailId__: El identificador único del recorrido del cual se quiere obtener los puntos
   * __cursor__: Una cadena de texto que puede estar vacía que le indica al servidor el número de la página que se desea obtener.
   Para obtener la primer página se llama con una cadena vacía, para páginas posteriores, se utiliza una cadena que el servidor regresa después de cada petición llamada también __cursor__
   * __numberOfElements__: Un entero que indica el número de elementos que uno desea que el servidor regrese

2. Retornos:
    * __cursor__: Una cadena de texto utilizada para mandar al servidor y obtener el siguiente grupo de elementos paginados.
    * __points__: Un arreglo de puntos del número de elementos solicitado por _numberOfElements_ y ordenados de manera cronológica. Los puntos tienen los siguientes datos:
     * __location__: Es la ubicación geográfica del punto registrado, contiene lo siguiente:
       * __latitude__: La latitud del punto registrado
       * __longitude__: La longitud del punto registrado
       * __radius__: El radio de precisión del punto registrado
    * __position__: La posición en del punto respecto a otros (no utilizado en _mapatonPublicAPI.getTrailRawPoints_)


A skeleton application for Google Cloud Endpoints in Java.

## Products
- [App Engine][1]

## Language
- [Java][2]

## APIs
- [Google Cloud Endpoints][3]
- [Google App Engine Maven plugin][4]

## Setup Instructions

1. Update the value of `application` in `appengine-web.xml` to the app
   ID you have registered in the App Engine admin console and would
   like to use to host your instance of this sample.

1. Add your API method to `src/main/java/${packageInPathFormat}/YourFirstAPI.java`.

1. Optional step: These sub steps are not required but you need this
   if you want to have auth protected methods.

    1. Update the values in `src/main/java/${packageInPathFormat}/Constants.java`
       to reflect the respective client IDs you have registered in the
       [APIs Console][6]. 

    1. You also need to supply the web client ID you have registered
       in the [APIs Console][4] to your client of choice (web, Android,
       iOS).

1. Run the application with `mvn appengine:devserver`, and ensure it's
   running by visiting your local server's api explorer's address (by
   default [localhost:8080/_ah/api/explorer][5].)

1. Get the client library with

   $ mvnappengine:endpoints_get_client_lib

   It will generate a client library jar file under the
   `target/endpoints-client-libs/<api-name>/target` directory of your
   project, as well as install the artifact into your local maven
   repository.

1. Deploy your application to Google App Engine with

   $ mvn appengine:update

[1]: https://developers.google.com/appengine
[2]: http://java.com/en/
[3]: https://developers.google.com/appengine/docs/java/endpoints/
[4]: https://developers.google.com/appengine/docs/java/tools/maven
[5]: https://localhost:8080/_ah/api/explorer
[6]: https://console.developers.google.com/
