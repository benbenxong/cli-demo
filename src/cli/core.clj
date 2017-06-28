(ns cli.core
  (:refer-clojure :exclude [parse-opts])
  (:require [clojure.tools.cli :refer [parse-opts]])
  (:gen-class))

(def cli-options
  ;; An option with a required argument
  [["-p" "--port PORT" "Port number"
    :default 80
    :parse-fn #(Integer/parseInt %)
    :validate [#(< 0 % 0x10000) "Must be a number between 0 and 65536"]]
   ;; A non-idempotent option
   ["-v" nil "Verbosity level"
    :id :verbosity
    :default 0
    :assoc-fn (fn [m k _] (update-in m [k] inc))]
   ;; A boolean option defaulting to nil
   ["-h" "--help"]
   ["-s" "--sql paras" "sql file and parameters"
    :default ""]
   ["-q" "--quiet"
    :id :verbose
    :default true
    :parse-fn not]
   ])


(defn -main [& args]
  (println (.toString (parse-opts args cli-options)))
  ;(parse-opts args cli-options)
  )

(println "Cleanlines is next to godliness")
