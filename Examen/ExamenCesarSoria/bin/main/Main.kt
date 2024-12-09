import java.io.*

data class Jugador(
    var nombre: String,
    var edad: Int,
    var altura: Float,
    var posicion: String,
    var esCapitan: Boolean
) : Serializable {
    fun mostrarJugador() {
        println("\tNombre: $nombre")
        println("\tEdad: $edad")
        println("\tAltura: $altura m")
        println("\tPosición: $posicion")
        println("\tCapitán: ${if (esCapitan) "Sí" else "No"}")
    }
}

data class EquipoBasket(
    var nombre: String,
    var anioFundacion: Int,
    var ciudad: String,
    var estadoCompeticion: Boolean
) : Serializable {
    val jugadores: MutableList<Jugador> = mutableListOf()

    fun agregarJugador(jugador: Jugador) {
        jugadores.add(jugador)
    }

    fun eliminarJugador(nombreJugador: String) {
        jugadores.removeIf { it.nombre.equals(nombreJugador, ignoreCase = true) }
    }

    fun mostrarEquipo() {
        println("Equipo: $nombre")
        println("Año de Fundación: $anioFundacion")
        println("Ciudad: $ciudad")
        println("Estado en Competición: ${if (estadoCompeticion) "Activo" else "Inactivo"}")
        println("Jugadores:")
        jugadores.forEach { it.mostrarJugador() }
    }
}

object CrudBasket {
    private const val ARCHIVO = "equipos.dat"

    fun guardarDatos(equipos: List<EquipoBasket>) {
        try {
            ObjectOutputStream(FileOutputStream(ARCHIVO)).use { it.writeObject(equipos) }
        } catch (e: IOException) {
            println("Error al guardar los datos: ${e.message}")
        }
    }

    fun cargarDatos(): MutableList<EquipoBasket> {
        return try {
            ObjectInputStream(FileInputStream(ARCHIVO)).use {
                @Suppress("UNCHECKED_CAST")
                it.readObject() as MutableList<EquipoBasket>
            }
        } catch (e: IOException) {
            println("No se pudieron cargar los datos: ${e.message}")
            inicializarDatos() // Si no hay datos guardados, inicializamos ejemplos.
        } catch (e: ClassNotFoundException) {
            println("Clase no encontrada: ${e.message}")
            inicializarDatos()
        }
    }

    private fun inicializarDatos(): MutableList<EquipoBasket> {
        val equipos = mutableListOf<EquipoBasket>()

        val equipo1 = EquipoBasket("Tigres del Norte", 1990, "Monterrey", true)
        equipo1.agregarJugador(Jugador("Juan Pérez", 25, 1.80f, "Base", true))
        equipo1.agregarJugador(Jugador("Carlos Sánchez", 22, 1.90f, "Escolta", false))
        equipo1.agregarJugador(Jugador("Luis Ramírez", 28, 2.05f, "Ala-Pívot", false))

        val equipo2 = EquipoBasket("Águilas Doradas", 1985, "Guadalajara", true)
        equipo2.agregarJugador(Jugador("Roberto García", 30, 1.85f, "Pívot", false))
        equipo2.agregarJugador(Jugador("Javier López", 26, 1.95f, "Ala", true))
        equipo2.agregarJugador(Jugador("Miguel Torres", 24, 1.78f, "Base", false))

        val equipo3 = EquipoBasket("Halcones Negros", 2000, "Ciudad de México", false)
        equipo3.agregarJugador(Jugador("Fernando Gómez", 29, 2.10f, "Pívot", true))
        equipo3.agregarJugador(Jugador("Hugo Martínez", 21, 1.88f, "Escolta", false))
        equipo3.agregarJugador(Jugador("Diego Álvarez", 23, 1.92f, "Ala", false))

        val equipo4 = EquipoBasket("Pumas de Fuego", 1995, "Puebla", true)
        equipo4.agregarJugador(Jugador("Mario Castillo", 27, 1.82f, "Base", true))
        equipo4.agregarJugador(Jugador("Ricardo Vargas", 24, 1.96f, "Ala-Pívot", false))
        equipo4.agregarJugador(Jugador("Esteban Cruz", 22, 1.89f, "Escolta", false))

        val equipo5 = EquipoBasket("Lobos del Sur", 2010, "Cancún", false)
        equipo5.agregarJugador(Jugador("Pablo Ríos", 20, 1.79f, "Base", true))
        equipo5.agregarJugador(Jugador("Alberto Silva", 23, 2.00f, "Ala", false))
        equipo5.agregarJugador(Jugador("Rafael Ortega", 25, 1.87f, "Escolta", false))

        equipos.add(equipo1)


        return equipos
    }

    fun actualizarEquipo(equipos: MutableList<EquipoBasket>) {
        print("Nombre del equipo a actualizar: ")
        val nombreEquipo = readLine() ?: ""
        val equipo = equipos.find { it.nombre.equals(nombreEquipo, ignoreCase = true) }

        if (equipo != null) {
            println("Actualizando equipo: ${equipo.nombre}")
            print("Nuevo nombre (dejar vacío para no cambiar): ")
            val nuevoNombre = readLine()
            if (!nuevoNombre.isNullOrEmpty()) equipo.nombre = nuevoNombre

            print("Nuevo año de fundación (dejar vacío para no cambiar): ")
            val nuevoAnio = readLine()?.toIntOrNull()
            if (nuevoAnio != null) equipo.anioFundacion = nuevoAnio

            print("Nueva ciudad (dejar vacío para no cambiar): ")
            val nuevaCiudad = readLine()
            if (!nuevaCiudad.isNullOrEmpty()) equipo.ciudad = nuevaCiudad

            print("Nuevo estado de competición (true/false, dejar vacío para no cambiar): ")
            val nuevoEstado = readLine()?.toBooleanStrictOrNull()
            if (nuevoEstado != null) equipo.estadoCompeticion = nuevoEstado

            println("Equipo actualizado.")
        } else {
            println("Equipo no encontrado.")
        }
    }

    fun actualizarJugador(equipos: MutableList<EquipoBasket>) {
        print("Nombre del equipo al que pertenece el jugador: ")
        val nombreEquipo = readLine() ?: ""
        val equipo = equipos.find { it.nombre.equals(nombreEquipo, ignoreCase = true) }

        if (equipo != null) {
            print("Nombre del jugador a actualizar: ")
            val nombreJugador = readLine() ?: ""
            val jugador = equipo.jugadores.find { it.nombre.equals(nombreJugador, ignoreCase = true) }

            if (jugador != null) {
                println("Actualizando jugador: ${jugador.nombre}")
                print("Nuevo nombre (dejar vacío para no cambiar): ")
                val nuevoNombre = readLine()
                if (!nuevoNombre.isNullOrEmpty()) jugador.nombre = nuevoNombre

                print("Nueva edad (dejar vacío para no cambiar): ")
                val nuevaEdad = readLine()?.toIntOrNull()
                if (nuevaEdad != null) jugador.edad = nuevaEdad

                print("Nueva altura (dejar vacío para no cambiar): ")
                val nuevaAltura = readLine()?.toFloatOrNull()
                if (nuevaAltura != null) jugador.altura = nuevaAltura

                print("Nueva posición (dejar vacío para no cambiar): ")
                val nuevaPosicion = readLine()
                if (!nuevaPosicion.isNullOrEmpty()) jugador.posicion = nuevaPosicion

                print("¿Es capitán? (true/false, dejar vacío para no cambiar): ")
                val nuevoEsCapitan = readLine()?.toBooleanStrictOrNull()
                if (nuevoEsCapitan != null) jugador.esCapitan = nuevoEsCapitan

                println("Jugador actualizado.")
            } else {
                println("Jugador no encontrado.")
            }
        } else {
            println("Equipo no encontrado.")
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val equipos = cargarDatos()

        println("Bienvenido al sistema CRUD de Equipos de Basket")
        while (true) {
            println(
                """
                |1. Crear equipo
                |2. Mostrar equipos
                |3. Agregar jugador a un equipo
                |4. Eliminar jugador de un equipo
                |5. Actualizar equipo
                |6. Actualizar jugador
                |7. Guardar y salir
            """.trimMargin()
            )
            print("Seleccione una opción: ")
            when (readLine()?.toIntOrNull()) {
                1 -> {
                    print("Nombre del equipo: ")
                    val nombre = readLine() ?: ""
                    print("Año de fundación: ")
                    val anioFundacion = readLine()?.toIntOrNull() ?: 0
                    print("Ciudad: ")
                    val ciudad = readLine() ?: ""
                    print("Estado en competición (true/false): ")
                    val estadoCompeticion = readLine()?.toBooleanStrictOrNull() ?: false

                    equipos.add(EquipoBasket(nombre, anioFundacion, ciudad, estadoCompeticion))
                }

                2 -> equipos.forEach { it.mostrarEquipo() }

                3 -> {
                    print("Nombre del equipo: ")
                    val nombreEquipo = readLine() ?: ""
                    val equipo = equipos.find { it.nombre.equals(nombreEquipo, ignoreCase = true) }

                    if (equipo != null) {
                        print("Nombre del jugador: ")
                        val nombreJugador = readLine() ?: ""
                        print("Edad: ")
                        val edad = readLine()?.toIntOrNull() ?: 0
                        print("Altura (en metros): ")
                        val altura = readLine()?.toFloatOrNull() ?: 0.0f
                        print("Posición: ")
                        val posicion = readLine() ?: ""
                        print("¿Es capitán? (true/false): ")
                        val esCapitan = readLine()?.toBooleanStrictOrNull() ?: false

                        equipo.agregarJugador(Jugador(nombreJugador, edad, altura, posicion, esCapitan))
                    } else {
                        println("Equipo no encontrado.")
                    }
                }

                4 -> {
                    print("Nombre del equipo: ")
                    val nombreEquipo = readLine() ?: ""
                    val equipo = equipos.find { it.nombre.equals(nombreEquipo, ignoreCase = true) }

                    if (equipo != null) {
                        print("Nombre del jugador a eliminar: ")
                        val nombreJugador = readLine() ?: ""
                        equipo.eliminarJugador(nombreJugador)
                    } else {
                        println("Equipo no encontrado.")
                    }
                }

                5 -> actualizarEquipo(equipos)

                6 -> actualizarJugador(equipos)

                7 -> {
                    guardarDatos(equipos)
                    println("Datos guardados. ¡Hasta luego!")
                    break
                }

                else -> println("Opción no válida. Intente de nuevo.")
            }
        }
    }
}
