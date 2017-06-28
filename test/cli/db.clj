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