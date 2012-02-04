(ns repl-utils.core
  (:use [clojure repl pprint reflect]))

(defmacro ?
  "Debugging macro that prints the result of the form before passing back the result of the form."
  [x] `(let [x# ~x] (println "dbg:" '~x "=" x#) x#))

(defn clear-ns []
  "Clear the namespace."
  (map #(ns-unmap *ns* %) (keys (ns-interns *ns*))))

(defn- show-methods* [c]
  (let [mds (fn[x] (map
                   #(vector x
                            (:name %)
                            (:parameter-types %)
                            (:return-type %))
                   (:members (reflect x))))]
    (sort-by second
             (apply concat
                    (map mds
                         (conj (ancestors (class c))
                               (class c)))))))

(defn show-methods [x]
  "Will provide the Java method names of whatever was passed in."
  ;; purely for side-effects so ignore return
  (let [_ (dorun (map prn (show-methods* x)))]))
