(ns cli.core-test
  (:refer-clojure :exclude [parse-opts])
  (:require [clojure.test :refer :all]
            [clojure.tools.cli :refer [parse-opts]]
            [clojure.string :as str]
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
   [nil "--sql paras" "sql file and parameters"
    :parse-fn #(if (re-find #"[, ]" %)
                             (str/split % #"[, ]") (vector %))
   ]
   ["-q" "--quiet"
    :id :verbose
    :default true
    :parse-fn not]
   [nil "--sno SNO" "sheet No."]
   [nil "--sname SName(s)" "sheet name(s) ::= sname|sn1,sn2.."
    :default []
    :assoc-fn (fn [m k v] (update-in m [k] into
                                     (if (re-find #"[, ]" v)
                                       (into [] (str/split v #"[, ]"))
                                       (vector v))))]
   ])

;(def args '( "--sql" "f1 20170202 20170504" "--sno" "1" "--sname" "sname04" "--sno" "2" "--sname" "sname02"))
(def args '( "--sname=abc,ddre" "--abc" "--sql" "f1" "20120101" "sno=1" "sname=sname04" "sno=2" "sname=sname02"))

(let
  [args (map (fn [arg]
               (str/replace arg #"(?i)^-(sql|ofile|sheet)$|^(sno|sname)="
                            #(if (nth %1 1)
                               (str "--" (nth %1 1))
                               (str "--" (nth %1 2) "=")))) args)
   opts (parse-opts args cli-options-test
          ;:in-order  true
          )]
  (if (or (nil? args) (-> opts :options :help))
    (do
      (println "usage: Query2xls")
      (println (-> opts :summary)))
    (if (-> opts :arguments)
      (assoc-in opts [:options :sql] (into (-> opts :options :sql) (-> opts :arguments))))
    )
  )
(map (fn [arg]
       (str/replace arg #"(?i)^-(sql|ofile|sheet)$|^(sno|sname)="
                    #(if (nth %1 1)
                       (str "--" (nth %1 1))
                       (str "--" (nth %1 2) "=")))) args)