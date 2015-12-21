(ns site.data
  (:require [me.raynes.fs :as fs]
            [site.formats :refer [yaml] :as fmt]))


(def strings
  {:title "Health Samurai"
   :text (:ru (yaml "text.yaml"))
   :contacts  (:ru (yaml "contacts.yaml"))
   :services  (:ru (yaml "services.yaml"))
   :trainings (:ru (yaml "trainings.yaml"))
   :products  (:ru (yaml "products.yaml"))
   :projects  (:ru (yaml "projects.yaml"))})

(fs/glob "resources/*.yaml")

(defn load []
  (reduce
   (fn [acc fl]
     (assoc acc (keyword (fs/base-name fl ".yaml"))  (:ru (fmt/from-yaml (slurp fl)))))
   {}
   (fs/glob "resources/*.yaml")))


(defn data [& ks]
  (get-in (load) ks))

(defn find-by-id [id & ks]
  (first (filter #(= id (:id %)) (apply data ks))))

