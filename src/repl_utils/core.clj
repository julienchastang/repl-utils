(ns repl-utils.core
  (:use [clojure repl pprint reflect]))

(defmacro ?
  "Debugging macro thar prints the results of the form before passing back the result."
  [x] `(let [x# ~x] (println "dbg:" '~x "=" x#) x#))

(defn clear-ns []
  "Clear the namespace"
  (map #(ns-unmap *ns* %) (keys (ns-interns *ns*))))

(defn- show-methods* [x]
  (map #(vector (:name %)
                (:parameter-types %) (:return-type %))  (:members (reflect x))))

(defn show-methods [x]
  "Will provide the Java method names of whatever was passed in"
  (pprint
   (sort (map str (show-methods* x)))))
