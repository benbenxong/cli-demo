;;;
;d:
;cd D:\coding\java\LeinProjects\cli
;lein localrepo install D:\downloads\oracle\instantclient_11_2_64\ojdbc6.jar oracle.jdbc/oracledriver "11.2.0.1"
;;;

(ns cli.db
   (:require [clojure.java.jdbc :as sql]))

(def db {:classname  "oracle.jdbc.OracleDriver"
         :subprotocol    "oracle:thin"
         :subname        "127.0.0.1:1521:orcl"
         :user               "alan"
         :password       "alan"})

(def db1 {:classname  "oracle.jdbc.OracleDriver"
         :subprotocol    "oracle:oci"
         :subname        "@alandb"
         :user               "alan"
         :password       "alan"})

(time (sql/query db ["select sysdate from dual"]))

(time (sql/query db1 ["select sysdate from dual"]))