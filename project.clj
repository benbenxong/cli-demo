(defproject cli "0.1.0-SNAPSHOT"
  :main cli.core
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha17"]
                 [org.clojure/tools.cli "0.3.5"]
                 [oracle.jdbc/oracledriver "11.2.0.1"]
                 [org.clojure/java.jdbc "0.7.0-alpha3"]
                 [clojure.jdbc/clojure.jdbc-c3p0 "0.3.2"]
                 ] )
