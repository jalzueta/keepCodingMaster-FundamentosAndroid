# Ordering

###Práctica de *Fundamentos de Android*
#####KeepCoding Startup Engineering Master Boot Camp
-- 

**"Ordering"** es una app desarrollada para Android como práctica del módulo *Fundamentos de Android* del *KeepCoding Startup Engineering Master Boot Camp*.

Su funcionalidad se podría resumir en que la app permite gestionar los pedidos de las diferentes mesas de un restaurante, platos a escoger de un **menú que se descarga a través de un _servicio web_ de forma asíncrona** a través de la clase `PlatesDownloader`.

Para ello, se han creado diferentes *Fragments*, que se agruparán en *Activities* en función del tamaño de pantalla y orientación del dispositivo. Llamemos a partir de ahora a los 2 diferentes layouts, *portrait* y *landscape*.
</br></br>
Resumen de *Activities* y *Fragments*:

####Activities
-- 
* **`MainActivity`**: 

*Portrait*: muestra el fragment `TableListFragment`.
*Landscape*: muestra el fragment `TableListFragment` a la izquierda y a la derecha el fragment `TablePagerFragment`.

Posee un Floating Action Button, solo en modo *Landscape*, que da paso a la MenuActivity.

* **`TableDetailActivity`**: 

Portrait: muestra el fragment `TablePagerFragment`. No existe en modo Landscape.

Posee un Floating Action Button que da paso a la `MenuActivity`, añadiendo o actualizando el plato correspondiente.

* **`MenuActivity`**: 

Muestra el fragment `MenuFragment`, con diferente distribución de platos en columnas según el tamaño de la pantalla.

* **`PlateDetailActivity`**: 

Muestra el fragment `TableDetailFragment`, con diferente distribución de contenidos en función del tamaño de la pantalla. 

Posee un Floating Action Button que añade el plato a la mesa y vuelve a la activity anterior.
</br></br>
####Fragments
-- 
* **`TableListFragment`**: 

Fragment que muestra una lista con las mesas del restaurante. Utiliza un custom adapter que muestra el número de mesa, el número de comensales y el número de platos pedidos. 

El fragment tiene un **`Menú`** que permite *limpiar todas la mesas*. Esta acción, que requiere la confirmación del usuario a través del `ClearAllTablesDialogFragment`, borra todos los datos de platos y comensales de todas las mesas.

Además, cada item de la lista tiene un **`menú contextual`** que permite asignar el número de comensales de la mesa correspondiente a través de un DialogFragment llamado `SetFellowsDialogFragment`.

* **`TablePagerFragment`**:

Fragment que contiene un ViewPager que nos permite navegar entre los diferentes detalles de las mesas.

El fragment tiene un **`Menú`** con las acciones *Anterior y Siguiente* para navegar entre las diferentes mesas disponibles.

El ViewPager utiliza como **`Adapter`** un fragment llamado `TableDetailFragment`.

* **`TableDetailFragment`**:

Fragment que muestra los datos de una mesa: numero de comensales, número de platos pedidos, precio total y una lista de platos pedidos.

El fragment tiene un **`Menú`** que permite *limpiar la mesa* y *asignar el número de comensales*, esto último a través del `SetFellowsDialogFragment` nombrado anteriormente.

La lista de platos pedidos se presenta a través de un RecyclerView que hace uso de una View personalizada, **`PlateView`**, que hace uso de las `CardView` de material.

Cada item de la lista está dotado de un **`menú contextual`** que permite eliminar ese plato de la lista.

Además, pulsando sobre cualquiera de los items de la lista, se da paso a `PlateDetailActivity`, en la que se podrán cambiar las notas añadidas a ese plato.

* **`MenuFragment`**:

Fragment que representa la carta de platos disponibles (descargados al iniciar la App), mediante una estructura con RecyclerView que hace uso de la View personalizada nombrada anteriormente, **`PlateView`**.

El fragment tiene un **`Menú`** que permite volver a descargar la carta a través de un servicio web, por si ha habido algún cambio. Para ello se hace uso de la clase `PlatesDownloader`.

Cada item del RecyclerView está dotado de un **`menú contextual`** que permite añadir dicho plato a la mesa en cuestión, previa introducción de unas *notas* a través de un DialogFragment llamado `SetPlateNotesDialogFragment`.

La distribución de los platos en filas y columnas dependerá del tamaño de la pantalla del dispositivo y de su orientación.

Además, al pulsar sobre cualquiera de los platos, se dará paso al a la `PlateDetailActivity`, en la que se podrá ver información adicional del plato, así como introducir *notas personalizadas*.

* **`PlateDetailFragment`**:

Fragment que representa toda la información de un plato de la carta. La distribución del contenido varía en función del tamaño de pantalla del dispositivo.


####Pendiente
-- 

Descarga de imagenes del menu desde servidor y cacheado de las mismas `(Branch: images)`
