<?php

/**
 * Created by PhpStorm.
 * User: newton
 * Date: 11/6/14
 * Time: 4:29 PM
 */
class DatabaseConnection
{

    //constructor
    public function __construct()
    {
        require_once "include/configuration.php";//force the configuration file to be loaded once

    }

    //destructor

    public function __destruct()
    {

    }


    public function connect()
    {

        $connection=null;

        // "mysql:host=" . DATABASE_HOST . ";" . "dbname=" + DATABASE_NAME,

        try {
            $connection = new PDO(
                DSN,
                DATABASE_USER,
                DATABASE_PASSWORD

            );

            //set error mode to exception for easy error handling
            $connection->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);


        } catch (PDOException $exception) {

            $exception->getMessage();

        }

        return $connection;


    }

} 