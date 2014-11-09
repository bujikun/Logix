<?php
/**
 * Created by PhpStorm.
 * User: newton
 * Date: 11/6/14
 * Time: 7:15 PM
 *
 * Handles all API requests
 * Accepts GET and POST
 *
 * Each request will be identified by TAG
 * Response will be JSON data
 *
 */

/**
 * Check for post request
 */


require_once "classes/DatabaseOperation.php";

if (isset($_POST["request"])&& $_POST["request"]!=""){
    
    //get request

    $request = $_POST["request"];

    $operation = new DatabaseOperation();

    //response array
    $response = array(
        "request"=>$request,
        "success"=>0,
        "error"=>0
    );


    //check for tag type

    if($request=="login"){
        //the user has requested to log in

        $email = $_POST["email"];
        $password = $_POST["password"];

        //check if the user has already been registered

        if($user = $operation->getUser($email,$password)){

            //user found

            //echo JSON with success

            $response["success"]=1;
            $response["unique_id"]=$user["_unique_id"];
            $response["user"]["name"]=$user["_name"];
            $response["user"]["email"] =$user["_email"];
            $response["user"]["created_at"] =$user["_created_at"];
            $response["user"]["updated_at"] = $user["_updated_at"];

            echo json_encode($response);


        }else{

            //user not  found
            //echo JSON with error 1
            $response["error"]=1;
            $response["error_message"]= "Incorrect email or password";
            echo json_encode($response);

        }




    }elseif ($request=="register"){

        //a new user wants to be registered

        $name =$_POST["name"];
        $email =$_POST["email"];
        $password = $_POST["password"];

        //check if the user exists

        if($operation->isAlreadyRegistered($email)){
            //a user has already been registered,send error response

            $response["error"]=2;
            $response["error_message"]="User already exists";
            echo json_encode($response);

        }else{
            //store user
            $user =$operation->insertNewUser($name,$email,$password);
            if($user){
	
                $response["success"]=1;
                $response["message"]="Successfully registered..!";
				$response["user"]["name"]=$user["_name"];
	            $response["user"]["email"] =$user["_email"];
	            $response["user"]["created_at"] =$user["_created_at"];
				
                echo json_encode($response);
            }else{
                //registration failed
                $response["error"]=-1;
                $response["error_message"]="Error occured during registration";
                echo json_encode($response);
            }
        }




    }else{
        //$_POST["request"] has an unknown value
        echo "Invalid Request";
    }



}else{
    //deny if $_POST is not set or is empty
    echo "Access Denied";

}

