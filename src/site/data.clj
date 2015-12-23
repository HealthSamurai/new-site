(ns site.data
  (:require [clojure.java.io :as io]
            [me.raynes.fs :as fs]
            [site.formats :refer [yaml] :as fmt]))


(def ^:dynamic *lang* :ru)

(defmacro with-lang [lang & body]
  `(binding [*lang* ~lang]
     ~@body))

(defn fs-join [& parts]
  (.getPath (apply io/file parts)))

(defn reload []
  (reduce
   (fn [acc fl]
     (assoc acc (keyword (fs/base-name fl ".yaml"))  (fmt/from-yaml (slurp fl))))
   {}
   (fs/glob (fs-join "resources" "*.yaml"))))


(defn data [& ks] (get-in (reload) ks))

(defn i [data & ks]
  (let [item (get-in data ks)]
    (if (map? item)
      (or (get item *lang*) item)
      item)))

(defn idata [& ks]
  (apply i (reload) ks))


(defn find-by-id [id & ks]
  (first (filter #(= id (:id %)) (apply data ks))))
