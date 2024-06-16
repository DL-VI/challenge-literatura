# LiterAlura

![STATUS](https://img.shields.io/badge/STATUS-FINALIZADO-blue)

### Descripción

* LiterAlura es una aplicación de catálogos de libros que consume la API de Gutendex para obtener información detallada
  sobre libros y autores, la cual se registra en una base de datos local. Esta aplicación permite al usuario interactuar
  a través de la consola con un menú interactivo, diseñado para realizar consultas y visualizar estadísticas de la
  información almacenada.


### Herramientas utilizadas
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)


### Requisitos

- Java 17
- Spring Boot 2.5 o superior
- PostgreSQL 13 o superior
- Maven 3.6 o superior


### Acceso al proyecto
- <p>Puedes <a href="https://github.com/DL-VI/challenge-literatura/tree/main/src/main/java/com/alura/literatura">acceder al código fuente del proyecto</a></p> 


## Instalación

Este proyecto utiliza Maven para gestionar sus dependencias. Al clonar este repositorio, asegúrate de tener Maven instalado en tu sistema

1. Clona el repositorio:
    ```bash
    git clone https://github.com/DL-VI/challenge-literatura.git
    cd challenge-literatura
    ```

2. Crea una base de datos en PostgreSQL.

3. Configura las variables en el archivo: `src/main/resources/application.properties`

      ```properties
      spring.application.name=literatura
      spring.datasource.url=jdbc:postgresql://localhost/tu_base_de_datos
      spring.datasource.username=tu_usuario
      spring.datasource.password=tu_contraseña
      spring.datasource.driver-class-name=org.postgresql.Driver
      hibernate.dialect=org.hibernate.dialect.HSQLDialect

      spring.jpa.hibernate.ddl-auto=update
      spring.jpa.show-sql=false
      spring.jpa.format-sql=false
      ```
   
4. Instalación de dependencias:
- Para construir este proyecto, asegúrate de tener las siguientes dependencias en tu archivo `pom.xml`:

```xml
<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>

		<dependency>
			<groupId>org.postgresql</groupId>
			<artifactId>postgresql</artifactId>
			<scope>runtime</scope>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>

		<dependency>
			<groupId>com.fasterxml.jackson.core</groupId>
			<artifactId>jackson-databind</artifactId>
			<version>2.16.0</version>
		</dependency>
</dependencies>
```

Para instalar las dependencias necesarias y compilar el proyecto, ejecuta el siguiente comando en la terminal:

```bash
./mvnw compile
```

5. Ejecuta la aplicación:

```bash
./mvnw spring-boot:run
```

## Uso

Una vez que la aplicación esté en funcionamiento, puedes interactuar con ella a través de la consola. El menú interactivo te permitirá realizar las siguientes acciones:
 
- **Buscar libro por título:** La aplicación solicita al usuario que ingrese el título (o parte del título) del libro
  que desea buscar. Luego realiza una consulta en el catalogo de la API para encontrar el libro que coincide con el
  título proporcionado y despues lo registra en la base de datos local si lo encuentra.

  - Si encuentra el libro:
    ```
    Ingrese el nombre del libro que desea buscar: torre

    Titulo: The Torrents of Spring
    Autor: Turgenev, Ivan Sergeevich
    Idioma: en
    Descargas: 405
    ```
  - Libro sin autor: ```No se pude registrar un libro sin autor.```
  - No encuentra el libro: ```Libro no encontrado: principito```
  - Intentar registrar el mismo libro: ```No se puede registrar el libro mas de una vez.```


- **Buscar autor por nombre:** La aplicación solicita al usuario que ingrese el nombre (o parte del nombre) del autor
  que desea buscar. Luego realiza una consulta en la base de datos local para encontrar el autor que coincide con el
  nombre proporcionado.

  - Si encuentra el autor:
    ```
    Nombre del autor: ll

    Cantidad de autores con la palabra 'll' en sus nombres: 5   
            
    Autor: Shakespeare, William
    Fecha nacimiento: 1564
    Fecha Fallecimiento: 1616
    Libros: [Romeo and Juliet, The Works of William Shakespeare [Cambridge Edition] [Vol. 7 of 9], Macbeth, Hamlet, Prince of Denmark]

    Autor: Melville, Herman
    Fecha nacimiento: 1819
    Fecha Fallecimiento: 1891
    Libros: [Moby Dick; Or, The Whale]

    Autores restantes ...
    ```
  - No lo encuentra: ```No se encontro el autor: roman```
  - No hay registros: ```No hay autores registrados.```


- **Listar libros registrados:** Permite al usuario ver una lista completa de todos los libros que están registrados en la base de datos local.

  - Lista:
    ```
    Titulo: Moby Dick; Or, The Whale
    Autor: Melville, Herman
    Idioma: en
    Descargas: 65191

    Titulo: Frankenstein; Or, The Modern Prometheus
    Autor: Shelley, Mary Wollstonecraft
    Idioma: en
    Descargas: 64482
    
    Libros restantes ...
    ```
  - No hay registros: ```No hay libros registrados.```


- **Listar autores registrados:** Permite al usuario ver una lista completa de todos los autores que están registrados en la base de datos local.

  - Lista:
      ```
      Autor: Quitard, P.-M. (Pierre-Marie)
      Fecha nacimiento: 1792
      Fecha Fallecimiento: 1882
      Libros: [Proverbes sur les femmes, l'amitié, l'amour et le mariage]

      Autor: Turgenev, Ivan Sergeevich
      Fecha nacimiento: 1818
      Fecha Fallecimiento: 1883
      Libros: [The Torrents of Spring]
    
      Autores restantes ...
      ```
  - No hay registros: ```No hay autores registrados.```


- **Listar autores vivos en un determinado año:** Permite al usuario obtener una lista de autores que estaban vivos en un año específico. Al seleccionar esta opción en el menú interactivo, la aplicación solicita al usuario que ingrese un año.
  - Lista:
    ```
    Ingrese el año:
    1600

    Autor: Shakespeare, William
    Fecha nacimiento: 1564
    Fecha Fallecimiento: 1616
    Libros: [Romeo and Juliet, The Works of William Shakespeare [Cambridge Edition] [Vol. 7 of 9], Macbeth, Hamlet, Prince of Denmark]

    Autor: Cervantes Saavedra, Miguel de
    Fecha nacimiento: 1547
    Fecha Fallecimiento: 1616
    Libros: [Don Quijote]
    
    Autores restantes ...
      ```
  - No hay registros o no hay en el año determinado: ```No se encontraron autores.```


- **Listar libros por idioma:** Permite al usuario obtener una lista de libros filtrados por el idioma en el que están escritos. Al seleccionar esta opción en el menú interactivo, la aplicación solicita al usuario que ingrese el código del idioma (por ejemplo, 'es' para español, 'en' para inglés, etc.).

  - Lista:
      ```
      Sigla del idioma: es

      Cantidad de libros encontrados: 2

      Titulo: Don Quijote
      Autor: Cervantes Saavedra, Miguel de
      Idioma: es
      Descargas: 14926

      Titulo: Vidas Ejemplares: Beethoven?Miguel Ángel?Tolstoi
      Autor: Rolland, Romain
      Idioma: es
      escargas: 1384
    
      Libros restantes ...
      ```
  - No hay registros: ```Sigla incorrecta: fr```


- **Top 10 libros más descargados** Permite al usuario ver una lista de los diez libros más populares en términos de número de descargas.

  - Lista:
    ```
    Titulo: Moby Dick; Or, The Whale
    Autor: Melville, Herman
    Idioma: en
    Descargas: 65191
    
    Libros restantes ...
    ```
  - No hay registros: ```No hay libros registrados.```
  - Lista incompleta: ```No hay la cantidad de libros registrados para el top 10.```


- **Estadística de libros** Permite al usuario obtener diversas estadísticas sobre los libros almacenados en la base de datos.

  ```
  Estadistica de los libros registrados:

  Cantidad de libros registrados: 20
  Total de descargas: 441408
  Libro mas descargado: 'Romeo and Juliet' con 67717 descargas
  Libro menos descargado: 'Minnie; or, The Little Woman: A Fairy Story' con 85 descargas
  Media de descarga: 22070,40
  ```
  - No hay registros: ```No hay libros registrados.```


- **Estadística de autores** Permite al usuario obtener diversas estadísticas sobre los autores almacenados en la base de datos.

  ```
  Estadistica de los autores registrados:

  Cantidad de autores: 16
  Autor mas longevo en fallecer: 'Quitard, P.-M. (Pierre-Marie)' con 90 años       
  Autor mas joven en fallecer: 'Brontë, Charlotte' con 39 años
  Media de edad: 66 años
  ```
  - No hay registros: ```No hay autores registrados.```
