(ns site.data
  (:require [clojure.java.io :as io]
            [me.raynes.fs :as fs]
            [site.formats :refer [yaml] :as fmt]))

(def default-lang :en)
(def ^:dynamic *lang* nil)

(defn current-lang []
  (or *lang* default-lang))

(defn next-lang []
  (get {:en :ru :ru :en} (current-lang)))

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
      (or (get item (or *lang* default-lang)) item)
      item)))

(defn idata [& ks]
  (apply i (reload) ks))


(defn find-by-id [id & ks]
  (first (filter #(= id (:id %)) (apply data ks))))
