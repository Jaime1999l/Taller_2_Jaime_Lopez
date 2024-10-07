# Taller 2 - Jaime López Díaz

https://github.com/Jaime1999l/Taller_2_Jaime_Lopez.git

## Descripción del Proyecto

La aplicación implementa varias funcionalidades, entre las cuales se incluyen tareas de red simuladas, manejo de configuración de fondo de pantalla, actualización de mensajes de saludo dependiendo de la hora del día, y el uso de `ViewModel` para la gestión de datos del usuario.

### Funcionalidades Principales

- **Saludo Automático**: La aplicación muestra un saludo personalizado dependiendo de la hora del día (Buenos días, Buenas tardes, Buenas noches).
- **Cambio de Fondo de Pantalla**: Los usuarios pueden cambiar el fondo de la aplicación a través de la `ConfiguracionActivity`, eligiendo entre colores e imágenes.
- **Simulación de Tareas de Red**: Implementación de una tarea de red simulada con `SimulatedNetworkTask` usando `ExecutorService` para simular una operación de red en segundo plano.
- **Gestión de Usuario**: Uso de `UserViewModel` para manejar y almacenar el nombre del usuario ingresado a través de la `RedActivity`.

### Descripción de las Clases

- **MainActivity**: 
  - Muestra un saludo personalizado dependiendo de la hora del día.
  - Inicia el proceso de verificación del saludo con `ExecutorService` para actualizar el saludo en segundo plano.
  - Aplica el fondo de pantalla configurado a través de `BackgroundUtil`.
  - Permite la navegación a `RedActivity` y `ConfiguracionActivity`.
  
- **RedActivity**: 
  - Permite al usuario ingresar su nombre y guarda este dato usando `UserViewModel`.
  - Muestra el nombre guardado y permite la simulación de una tarea de red usando `SimulatedNetworkTask`.
  - Aplica el fondo de pantalla configurado a través de `BackgroundUtil`.

- **ConfiguracionActivity**: 
  - Permite al usuario seleccionar el fondo de la aplicación, ya sea un color o una imagen.
  - Guarda la configuración del fondo en `SharedPreferences` y aplica el fondo de pantalla mediante `BackgroundUtil`.

- **UserViewModel**: 
  - Administra el nombre del usuario a través de `LiveData`.
  - Guarda y recupera el nombre del usuario utilizando `SharedPreferences`.

- **FetchGreetingTask**: 
  - Simula una tarea de red que obtiene un mensaje de saludo. 
  - Usa `AsyncTask` para realizar la simulación y actualizar la UI.

- **SimulatedNetworkTask**: 
  - Simula una llamada de red que devuelve un JSON con un saludo y la hora.
  - Usa `ExecutorService` para ejecutar la tarea en segundo plano y actualizar la UI en el hilo principal.

- **BackgroundUtil**: 
  - Administra la aplicación del fondo de pantalla en las actividades, ya sea un color o una imagen, según la configuración del usuario.
 
 ### Notas Adicionales
- La simulación de tareas de red en SimulatedNetworkTask no requiere una conexión a Internet real, ya que la respuesta está simulada.
- La configuración del fondo de pantalla se guarda localmente en SharedPreferences, por lo que persiste incluso si la aplicación se cierra.
- Se ha utilizado ViewModel y LiveData para una mejor gestión del ciclo de vida de los datos del usuario, asegurando que la UI se actualice de manera reactiva.


