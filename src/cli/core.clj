(ns cli.core
  (:refer-clojure :exclude [parse-opts])
  (:require [clojure.tools.cli :refer [parse-opts]]
            [clojure.string :as str])
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
   [nil "--sql paras" "sql file and parameters"
    ]
   ["-q" "--quiet"
    :id :verbose
    :default true
    :parse-fn not]
   [nil "--sno SNO" "sheet No."]
   [nil "--sname SName" "sheet name"
    :default []
    :assoc-fn (fn [m k v] (update-in m [k] into
                                     (if (re-find #"[, ]" v)
                                       (into [] (str/split v #"[, ]"))
                                       (vector v))))]
   ])

(defn -main [& args]
  (println (.toString (parse-opts args cli-options)))
  ;(parse-opts args cli-options)
  )

