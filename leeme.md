DS - PR1


Se define la clase Player que se encarga de almacenar la información de cada uno de los jugadores y gestionar una lista enlazada con los eventos a los que se inscribe cada jugador.

Se define un vector de jugadores en el TAD SportEvents4ClubImpl de tamaño MAX_NUM_PLAYER, junto con un contador numPlayers que almacena el número de jugadores que en cada momento contiene el vector.

Se declara una variable con una referencia al jugador más activo, que se actualiza cada vez que un jugador se inscribe en un evento. De esta manera, obtenemos el jugador más activo en tiempo constante.

Se define la clase OrganizingEntity que se encarga de almacenar la información de cada una de las entidades organizadoras de eventos y gestionar una lista enlazada con los eventos que ha organizado cada entidad.

Se define un vector de entidades organizadoras en el TAD de tamaño MAX_NUM_ORGANIZING_ENTITIES, junto con un contador numOrganizintEntities que almacena el número de entidades que en cada momento contiene el vector.

Se define la clase File que se encarga de almacenar la información de cada uno de los registros de solicitud de organización de eventos. A parte de toda la información que entra al sistema mediante su constructor, se añade un atributo LocalDate updated que almacena la fecha en la cual se actualiza el estado del registro a ENABLED o DISABLED.

Se define una cola de registros que se encarga de almacenar los registros y gestionar su entrada y su salida según el criterio FIFO.

Se definen tres atributos en el TAD: numTotalFiles, numPendingFiles y numRejectedFiles, que almacenan, respectivamente, el número de registros pendientes, el número de los aceptados y el número de los rechazados. Se actualizan cada vez que se actualiza el estado de un registro.

Se define la clase SportEvent que se encarga de almacenar la información de cada uno de los eventos deportivos. La clase implementa la interfaz Comparable y sobreescribe los métodos compareTo y equals para poder implementar el vector de eventos ordenado por rating. También contiene dos colas de inscripciones: una para los jugadores participantes y otra para los suplentes (los jugadores que se inscriben cuando ya se han superado las plazas máximas). Puesto que no se conoce el número máximo de jugadores suplentes, se ha optado por definir el TAD LinkedQueue (cola enlazada) para almacenarlos, se esta manera no se desperdicia espacio. También contiene una lista encadenada para almacenar las valoraciones de los jugadores del evento. Se ha definido el atributo sumRating que almacena la suma de las valoraciones para poder calcular en tiempo constante la media de las valoraciones.

Se define el TAD LinkedQueue que implementa la interfaz Queue y hereda de LinkedList para implementar el comportamiento de una cola enlazada.

Se define el TAD OrderedDictionaryArray que hereda de DictionaryArrayImpl que, a su vez, implementa Dictionary y FiniteContainer. Este TAD define un vector de estructuras clave-valor. Se implementa la funcionalidad necesaria para mantener el vector ordenado según su clave, utilizando el mergeSort. Se sobreescriben los métodos put, delete, y get para insertar, borrar y obtener nodos de forma que se mantenga el vector ordenado por clave con tiempos logarítmicos. También se implementa el algoritmo de búsqueda binaria de un elemento.

Se define el TAD OrderedArray que implementa la interfaz FiniteContainer para gestionar un vector ordenado. Los elementos del vector deben implementar la interfaz Comparable para poder permitir la ordenación del vector. Para la ordenación se utiliza la clase MergeSort.

Se implementan todas las excepciones, heredando de DSException.

Se implementan los métodos de la clase ResourceUtil mediante operadores bitwise a nivel de bits.

Se implementa un segundo juego de pruebas: SportEvents4ClubPR1Test2, para complementar pruebas no recogidas en el juego de pruebas inicial. En concreto, se prueban el lanzamiento de excepciones y el comportamiento más a fondo de algunos métodos.
