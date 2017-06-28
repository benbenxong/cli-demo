(ns cli.core-test
  (:refer-clojure :exclude [parse-opts])
  (:require [clojure.test :refer :all]
            [clojure.tools.cli :refer [parse-opts]]
            [cli.core :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 1))))

(def cli-options-test
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

(def args '( "--sql" "f1 20170202 20170504"))
(let [opts (parse-opts args cli-options-test )]
  (if (or (nil? args) (-> opts :options :help))
    (do
      (println "usage: Query2xls")
      (println (-> opts :summary)))
    opts))