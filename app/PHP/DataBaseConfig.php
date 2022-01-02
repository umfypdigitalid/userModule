<?php

class DataBaseConfig
{
    public $servername;
    public $username;
    public $password;
    public $databasename;

    public function __construct()
    {

        //webhost
        //$this->servername = 'localhost';
        //$this->username = 'id16886117_fypumdigitalid';
        //$this->password = 'xhmD3iN>#_9pp?cc';
        //$this->databasename = 'id16886117_umdigitalid';
        //local
        $this->servername = 'localhost';
        $this->username = 'root';
        $this->password = '';
        $this->databasename = 'digitalid';

    }
}

?>
