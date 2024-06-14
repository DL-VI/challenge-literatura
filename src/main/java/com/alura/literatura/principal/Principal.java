package com.alura.literatura.principal;

import com.alura.literatura.moduls.*;
import com.alura.literatura.repository.AutorRepository;
import com.alura.literatura.repository.LibroRepository;
import com.alura.literatura.service.ConsumoApi;
import com.alura.literatura.service.ConvierteDatos;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.*;
import java.util.stream.Collectors;

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

                    1 - Buscar libro por titulo
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
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    buscarAutorPorNombre();
                    break;
                case 3:
                    if (listarLibrosRegistrados().isEmpty()) System.out.println("\nNo hay libros registrados.");
                    else listarLibrosRegistrados().forEach(System.out::println);
                    break;
                case 4:
                    if (listarAutoresRegistrados().isEmpty()) System.out.println("\nNo hay autores registrados.");
                    else listarAutoresRegistrados().forEach(System.out::println);
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
        if (listarLibrosRegistrados().isEmpty()) {
            System.out.println("\nNo hay datos registrados.");return;
        }

        int cantidadAutores = listarAutoresRegistrados().size();
        int edadMayor = autorRepository.edadMayor();
        String nombreAutorMayor = autorRepository.nombreAutorMayor(edadMayor);
        int edadMenor = autorRepository.edadMenor();
        String nombreAutorMenor = autorRepository.nombreAutorMenor(edadMenor);
        double media = autorRepository.mediaEdad();

        System.out.println("""
                \nEstadistica de autores:
                
                Cantidad de autores: %d
                Autor mas longevo en fallecer: '%s' con %d años
                Autor mas joven en fallecer: '%s' con %d años
                Media de edad: %.0f años
                """.formatted(cantidadAutores, nombreAutorMayor, edadMayor, nombreAutorMenor, edadMenor, media));
    }

    public void estadisticaLibros() {

        if (listarLibrosRegistrados().isEmpty()) {
            System.out.println("\nNo hay datos registrados.");return;
        }

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
        List<Libro> libros = listarLibrosRegistrados().stream().sorted(Comparator.
                comparing(Libro::getDescargas).reversed()).limit(10).collect(Collectors.toList());

        if (libros.isEmpty()) {
            System.out.println("\nNo hay libros registrados.");return;
        }

        if (libros.size() < 10) {
            System.out.println("\nNo hay la cantidad de libros registrados para el top 10.");return;
        }
        libros.forEach(System.out::println);
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

            System.out.println("\nCantidad de libros encontrados: " + libros.stream().filter(i -> i.getIdioma().equals(sigla)).count());
            libroRepository.findByIdioma(sigla).forEach(System.out::println);
        } else System.out.println("\nSigla incorrecta: " + sigla);
    }

    public void listarAutoresPorAño() {
        System.out.println("\nIngrese el año: ");

        try {
            int fecha = sc.nextInt();
            List<Autor> listaAutores = autorRepository.buscarAutorPorFecha(fecha);
            if (listaAutores.isEmpty()) System.out.println("\nNo se encontraron autores.");
            else listaAutores.forEach(System.out::println);
        } catch (InputMismatchException e) {
            System.out.println("\nIngrese valores numericos.");
            sc.nextLine();
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
            System.out.println("\nNo hay autores registrados.");return;
        }

        Autor autor = autorRepository.findByNombreIgnoreCaseContaining(nombre);
        if (autor != null){
            System.out.println(autor);return;
        }
         System.out.println("\nNo se encontro el autor: " + nombre);
    }

    public void buscarLibroPorTitulo() throws JsonProcessingException {
        System.out.println("\nIngrese el nombre del libro que desea buscar:");
        var texto = sc.nextLine();

        var json = api.consumirApi(URL + "?search=" + texto.replace(" ", "+"));
        Datos datosJson = convierte.convertir(json, Datos.class);
        Optional<DatosLibro> datos = datosJson.resultados().stream().
                filter(item -> item.titulo().toUpperCase().contains(texto.toUpperCase())).findFirst();

        if (datos.isPresent()) {
            DatosLibro datosLibro = datos.get();
            List<DatosAutor> datosAutor = datosLibro.autor();

            if (datosAutor.isEmpty()) {
                System.out.println("\nNo se pude registrar un libro sin autor.");return;
            }
            if (validarLibro(datosLibro.titulo())) {
                System.out.println("\nNo se puede registrar el libro mas de una vez.");return;
            }

            Libro libro = new Libro(datosLibro);

            if (!validarAutor(datosAutor.get(0).nombre())) {
                Autor autor = new Autor(datosAutor.get(0).nombre(), datosAutor.get(0).nacimiento(), datosAutor.get(0).fallecimiento());
                autorRepository.save(autor);
                libro.setAutor(autor);
            } else libro.setAutor(autorRepository.findByNombreIgnoreCase(datosAutor.get(0).nombre()));

            libroRepository.save(libro);
            System.out.println(libro);

        } else System.out.println("\nLibro no encontrado: " + texto);
    }

    private boolean validarLibro(String titulo) {
        return listarLibrosRegistrados().stream().anyMatch(libro -> libro.getTitulo().equalsIgnoreCase(titulo));
    }

    private boolean validarAutor(String nombre) {
        return listarAutoresRegistrados().stream().anyMatch(autor -> autor.getNombre().equalsIgnoreCase(nombre));
    }
}
