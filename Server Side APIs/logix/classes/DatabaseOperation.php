<?php

/**
 * Created by PhpStorm.
 * User: newton
 * Date: 11/6/14
 * Time: 4:31 PM
 */
class DatabaseOperation
{

    private $connection;

    //constructor
    public function __construct()
    {

        require_once "include/configuration.php";
        require_once "classes/DatabaseConnection.php";


    }

    //destructor

    public function __destruct()
    {

    }

    /**
     * encrypts password
     * @param password
     * @return an array of salt and encrypted password
     */

    private function hashSSHA($password)
    {

        $salt = sha1(rand());//generate a random string
        $salt = substr($salt, 0, 10);//extract first 10 characters
        $encrypted_password = base64_encode(sha1($password . $salt, true) . $salt);//encode the password with base64-encode
        return array("salt" => $salt, "encrypted_password" => $encrypted_password);

    }


    private function verifySSHA($salt, $password)
    {


        return base64_encode(sha1($password . $salt, true) . $salt);

    }

    public function insertNewUser($name, $email, $password)
    {

        /**
         * inserts new user
         * returns user details
         */

       // require_once "include/configuration.php";
        $uniqueUserId = uniqid("", true);
        $hash = $this->hashSSHA($password);
        $encrypted_password = $hash["encrypted_password"];
        $salt = $hash["salt"];

        $databaseHandle = new DatabaseConnection();
        $connection = $databaseHandle->connect();//establish connection to the database
        $statement = $connection->prepare(
            "INSERT INTO " . TABLE_NAME . "(_unique_id,_name,_email,_encrypted_password,_salt,_created_at)
            VALUES (?,?,?,?,?,NOW())");

        $statement->bindParam(1, $uniqueUserId);
        $statement->bindParam(2, $name);
        $statement->bindParam(3, $email);

        $statement->bindParam(4, $encrypted_password);
        $statement->bindParam(5, $salt);

        $statement->execute();

        //check if the operation was successful


        if ($statement->rowCount() > 0) {
			$id = $connection->lastInsertId();
			$statement->closeCursor();
			 $statement = $connection->prepare("SELECT * FROM " . TABLE_NAME . " WHERE _user_id=?");

		     $statement->bindParam(1, $id);

		     $statement->execute();
			return $statement->fetch(PDO::FETCH_ASSOC);
			
            
        }



        return false;

    }



    public function getUser($email, $password)
    {

        /**
         * Get user by email and password
         */
        //check if the operation was successful

        if ($statement=$this->isAlreadyRegistered($email)) {

            $values = $statement->fetch(PDO::FETCH_ASSOC);
            $salt = $values["_salt"];
            $encrypted_password = $values["_encrypted_password"];
            $hash = $this->verifySSHA($salt, $password);

            //check for password equality
            if ($encrypted_password == $hash) {
                //authentification was successful
                return $values;
            } else {
                return false;
            }
        } else {
            //user not in the database
            return false;
        }


    }


	public function fetchUser($email){
		
		$databaseHandle = new DatabaseConnection();
        $connection = $databaseHandle->connect();//establish connection to the database
       
		
	}
	
    /**
     * Decrypts the password
     * @param salt ,password
     * @return encoded string
     *
     */



    /**
     * @param $email
     * @return bool
     */
    public function isAlreadyRegistered($email)
    {

        //require_once "classes/DatabaseConnection.php";
        /**
         * Get user by email and password
         */

        try {
            $databaseHandle = new DatabaseConnection();
            $connection = $databaseHandle->connect();//establish connection to the database
            $statement = $connection->prepare(
                "SELECT * FROM " . TABLE_NAME . " WHERE _email=?");

            $statement->bindParam(1, $email);

            $statement->execute();

            //check if the operation was successful

            if ($statement->rowCount() > 0) {
                return $statement;
            }

        } catch (PDOException $exception) {
            echo $exception->getMessage();

        }


        return false;
    }


} 