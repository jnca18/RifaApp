package com.example.rifaapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * Clase que define la base de datos local de la aplicación utilizando Room.
 *
 * Esta clase debe ser abstracta y extender de RoomDatabase. Room se encargará
 * de generar automáticamente la implementación necesaria en tiempo de compilación.
 *
 * @Database: Especifica las entidades (tablas) que formarán parte de la base de datos
 * y su versión. En este caso, solo se incluye la entidad RifaEntity.
 *
 * @TypeConverters: Permite registrar convertidores personalizados para tipos de datos
 * no compatibles directamente con SQLite (como List<Int>).
 */

@Database(entities = [RifaEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Método abstracto que expone el DAO (Data Access Object) que Room implementará
     * automáticamente. Este DAO contiene las funciones necesarias para insertar,
     * eliminar, buscar y consultar rifas.
     *
     * Es el punto de acceso principal a las operaciones de base de datos relacionadas
     * con la entidad RifaEntity.
     *
     * @return Una instancia de RifaDao que permite acceder a las operaciones de base de datos.
     */
    abstract fun rifaDao(): RifaDao
}

