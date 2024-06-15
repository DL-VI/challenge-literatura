package com.alura.literatura.principal;

import com.alura.literatura.moduls.*;
import com.alura.literatura.repository.AutorRepository;
import com.alura.literatura.repository.LibroRepository;
import com.alura.literatura.service.ConsumoApi;
import com.alura.literatura.service.ConvierteDatos;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Principal {
    private final Scanner sc;
    private final String URL = "https://gutendex.com/books/";
    private final ConsumoApi api;
    private final ConvierteDatos convierte;
    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.sc = new Scanner(System.in);
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
        this.api = new ConsumoApi();
        this.convierte = new ConvierteDatos();
    }

    public void menu() throws JsonProcessingException {
        int opc;

        do {
            System.out.println("""
                    \n----------------------LITERATURA----------------------

                    1 - Registrar libro por titulo
                    2 - Buscar autor por nombre
                    3 - Listar libros registrados
                    4 - Listar autores registrados
                    5 - Listar autores vivos en un determinado año
                    6 - Listar libros por idioma
                    7 - Top 10 libros mas descargados
                    8 - Estadistica de libros
                    9 - Estadistica de autores
                    0 - Salir
                    """);

            System.out.println("Ingrese el numero de la opcion:");

            try {
                opc = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                sc.nextLine(); //limpia el bufer de entrada
                opc = -1; //opcion para repetir el bucle
            }

            switch (opc) {
                case 0:
                    System.out.println("\nCerrando la aplicacion...\n");
                    break;
                case 1:
                    registrarLibroPorTitulo();
                    break;
                case 2:
                    buscarAutorPorNombre();
                    break;
                case 3:
                    if (listarLibrosRegistrados().isEmpty()) System.out.println("\nNo hay libros registrados.");
                    else listarLibrosRegistrados().forEach(System.out::print);
                    break;
                case 4:
                    if (listarAutoresRegistrados().isEmpty()) System.out.println("\nNo hay autores registrados.");
                    else listarAutoresRegistrados().forEach(System.out::print);
                    break;
                case 5:
                    listarAutoresPorAño();
                    break;
                case 6:
                    listarPorIdioma();
                    break;
                case 7:
                    topDescargas();
                    break;
                case 8:
                    estadisticaLibros();
                    break;
                case 9:
                    estadisticaAutores();
                    break;
                default:
                    System.out.println("\nError: Ingrese un número válido.");
            }
        } while (opc != 0);
    }

    public void estadisticaAutores() {
        // Verifica si no hay autores registrados utilizando listarAutoresRegistrados().
        if (listarAutoresRegistrados().isEmpty()) {
            System.out.println("\nNo hay autores registrados.");
            return;// Sale del método si no hay autores registrados.
        }

        int cantidadAutores = listarAutoresRegistrados().size();
        int edadMayor = autorRepository.edadMayor();
        // Obtiene el nombre del autor que vivió más años.
        String nombreAutorMayor = autorRepository.nombreAutorMayor(edadMayor);
        int edadMenor = autorRepository.edadMenor();
        // Obtiene el nombre del autor que vivió menos años.
        String nombreAutorMenor = autorRepository.nombreAutorMenor(edadMenor);
        // Obtiene la edad promedio al fallecer de los autores.
        double media = autorRepository.mediaEdad();

        System.out.println("""
                \nEstadistica de los autores registrados:
                
                Cantidad de autores: %d
                Autor mas longevo en fallecer: '%s' con %d años
                Autor mas joven en fallecer: '%s' con %d años
                Media de edad: %.0f años
                """.formatted(cantidadAutores, nombreAutorMayor, edadMayor, nombreAutorMenor, edadMenor, media));
    }

    public void estadisticaLibros() {
        if (listarLibrosRegistrados().isEmpty()) {
            System.out.println("\nNo hay libros registrados.");
            return;// Sale del método si no hay libros registrados.
        }

        // Utiliza un flujo de datos (Stream) para obtener estadísticas de las descargas de los libros.
        DoubleSummaryStatistics stdc = listarLibrosRegistrados().stream()
                .collect(Collectors.summarizingDouble(Libro::getDescargas));

        String tituloMayorDescargas = libroRepository.tituloDellibroConMayorDescarga();
        String tituloMenorDescargas = libroRepository.tituloDellibroConMenorDescarga();

        System.out.println("""
                        \nEstadistica de los libros registrados:
                        
                        Cantidad de libros: %d
                        Total de descargas: %.0f
                        Libro mas descargado: '%s' con %.0f descargas
                        Libro menos descargado: '%s' con %.0f descargas
                        Media de descarga: %.2f
                        """.formatted(stdc.getCount(), stdc.getSum(), tituloMayorDescargas, stdc.getMax(),
                tituloMenorDescargas, stdc.getMin(), stdc.getAverage()));
    }

    public void topDescargas() {
        // Obtiene la lista de libros registrados y la ordena por la cantidad de descargas en orden descendente,
        // luego limita la lista a los primeros 10 libros y la colecciona en una lista.
        List<Libro> libros = listarLibrosRegistrados().stream().sorted(Comparator.
                comparing(Libro::getDescargas).reversed()).limit(10).collect(Collectors.toList());

        if (libros.isEmpty()) {
            System.out.println("\nNo hay libros registrados.");return;
        }

        if (libros.size() < 10) {
            System.out.println("\nNo hay la cantidad de libros registrados para el top 10.");return;
        }
        libros.forEach(System.out::print);
    }

    public void listarPorIdioma() {
        System.out.println("""
                \n----------Idiomas----------
                es - español
                en - ingles
                ---------------------------
                Sigla del idioma:""");

        var sigla = sc.nextLine();

        if (sigla.equalsIgnoreCase("es") || sigla.equalsIgnoreCase("en")) {
            List<Libro> libros = listarLibrosRegistrados();
            // Filtra y cuenta la cantidad de libros que corresponden al idioma ingresado.
            long cantidadLibrosEncontrados = libros.stream()
                    .filter(libro -> libro.getIdioma().equals(Idioma.getIdioma(sigla)))
                    .count();

            System.out.println("\nCantidad de libros encontrados: " + cantidadLibrosEncontrados);
            // Obtiene y muestra todos los libros que corresponden al idioma ingresado.
            libroRepository.findByIdioma(Idioma.getIdioma(sigla)).forEach(System.out::print);
        } else System.out.println("\nSigla incorrecta: " + sigla);
    }

    public void listarAutoresPorAño() {
        System.out.println("\nIngrese el año: ");

        try {
            int fecha = sc.nextInt();
            // Busca en el repositorio de autores aquellos que estén vivos en el año especificado.
            List<Autor> listaAutores = autorRepository.buscarAutorPorFecha(fecha);
            if (listaAutores.isEmpty()) System.out.println("\nNo se encontraron autores.");
            else listaAutores.forEach(System.out::print);
        } catch (InputMismatchException e) { // Captura una excepción si el usuario ingresa un valor no numérico.
            System.out.println("\nIngrese valores numericos.");
            sc.nextLine(); // Limpia el buffer de entrada.
        }
    }

    public List<Autor> listarAutoresRegistrados() {
        return autorRepository.findAll();
    }

    public List<Libro> listarLibrosRegistrados() {
        return libroRepository.findAll();
    }

    public void buscarAutorPorNombre() {
        System.out.println("\nNombre del autor:");
        var nombre = sc.nextLine();

        if (listarAutoresRegistrados().isEmpty()) {
            System.out.println("\nNo hay autores registrados.");
            return; // Sale del método porque no hay autores para buscar.
        }

        // Busca los autores por nombre ignorando mayúsculas y minúsculas en el repositorio de autores.
        List<Autor> autor = autorRepository.findByNombreIgnoreCaseContaining(nombre);
        if (!autor.isEmpty() ){
            System.out.println("\nCantidad de autores con la palabra '" + nombre + "' en sus nombres: " + autor.size());
            Stream<String> resultado = autor.stream().map(Autor::toString);//Convierte cada objeto a String usando su método toString()
            resultado.forEach(System.out::print);
            return;
        }
         System.out.println("\nNo se encontro el autor: " + nombre);
    }

    public void registrarLibroPorTitulo() throws JsonProcessingException {
        System.out.println("\nIngrese el nombre del libro que desea buscar:");
        var texto = sc.nextLine();

        // Realiza una solicitud a la API usando el texto de búsqueda y obtiene la respuesta en formato JSON.
        var json = api.consumirApi(URL + "?search=" + texto.replace(" ", "+"));
        // Convierte el JSON obtenido en un objeto de la clase Datos usando el método convertir de la instancia convierte.
        Datos datosJson = convierte.convertir(json, Datos.class);
        // Filtra los datos obtenidos para encontrar el primer libro cuyo título coincida (ignorando mayúsculas y minúsculas) con el texto ingresado.
        Optional<DatosLibro> datos = datosJson.resultados().stream().
                filter(item -> item.titulo().toUpperCase().contains(texto.toUpperCase())).findFirst();

        // Verifica si se encontró algún dato de libro que coincida con el título buscado.
        if (datos.isPresent()) {
            // Obtiene los detalles del libro encontrado.
            DatosLibro datosLibro = datos.get();
            // Obtiene la lista de autor asociado al libro encontrado.
            List<DatosAutor> datosAutor = datosLibro.autor();

            // Verifica si el libro encontrado no tiene autor asociado.
            if (datosAutor.isEmpty()) {
                System.out.println("\nNo se pude registrar un libro sin autor.");
                return;  // Sale del método porque no se puede procesar un libro sin autor.
            }
            // Verifica si el libro ya está registrado en la base de datos.
            if (validarLibro(datosLibro.titulo())) {
                System.out.println("\nNo se puede registrar el libro mas de una vez.");
                return; // Sale del método porque no se puede registrar un libro más de una vez.
            }

            // Crea un nuevo objeto Libro usando los detalles obtenidos del libro encontrado.
            Libro libro = new Libro(datosLibro);

            // Verifica si el autor del libro ya está registrado en la base de datos.
            if (!validarAutor(datosAutor.get(0).nombre())) {
                // Si el autor no está registrado, crea un nuevo objeto Autor y lo guarda en el repositorio de autores.
                Autor autor = new Autor(datosAutor.get(0).nombre(), datosAutor.get(0).nacimiento(), datosAutor.get(0).fallecimiento());
                autorRepository.save(autor); // Guarda el autor en la base de datos.
                libro.setAutor(autor); // Asigna el autor al libro.
            } else {
                // Si el autor ya está registrado, obtiene el objeto Autor desde el repositorio de autores.
                libro.setAutor(autorRepository.findByNombreIgnoreCase(datosAutor.get(0).nombre()));
            }

            libroRepository.save(libro); // Guarda el libro en el repositorio de libros.
            System.out.println(libro); // Imprime los detalles del libro registrado.

        } else System.out.println("\nLibro no encontrado: " + texto);
    }

    private boolean validarLibro(String titulo) {
        // Utiliza el método listarLibrosRegistrados para obtener la lista actual de libros registrados en la aplicación.
        return listarLibrosRegistrados().stream()
                // Utiliza el método anyMatch para verificar si algún libro en la lista coincide con el título proporcionado.
                .anyMatch(libro -> libro.getTitulo().equalsIgnoreCase(titulo));
    }

    private boolean validarAutor(String nombre) {
        // Utiliza el método listarAutoresRegistrados para obtener la lista actual de autores registrados en la aplicación.
        return listarAutoresRegistrados().stream()
                // Utiliza el método anyMatch para verificar si algún autor en la lista coincide con el nombre proporcionado.
                .anyMatch(autor -> autor.getNombre().equalsIgnoreCase(nombre));
    }
}
