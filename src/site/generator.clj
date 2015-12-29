(ns site.generator
  (:require [clojure.string :as str]
            [me.raynes.fs :as fs]
            [site.server :as s]))

(defn dump [path res]
  (let [dir (butlast path)
        dir-path (str "dist/" (str/join "/" dir))
        fl  (or (last path) "index")
        fl-path (str dir-path "/" fl ".html")]
    (println "page: " fl-path)
    (fs/mkdir dir-path)
    (spit fl-path res)))

(defn safe-dispatch [& args]
  (try
    (apply s/dispatch args)
    (catch Exception e
      (println "ERROR: " args (.toString e))
      {:body (str "<pre>" e "</pre>")})))

(defn *generate [{path :path params :params} routes]
  (doseq [[k v] routes]
    (println path k)
    (cond
      (= :GET k) (let [uri (str/join "/" path)
                       res (safe-dispatch {:uri uri :request-method k})]
                   (dump path (:body res)))
      (vector? k) (let [k (first k)
                        gen (get routes k)]
                    (println gen)
                    (doseq [pv (gen)]
                      (*generate {:path (conj path pv) :params (assoc params k pv)} v)))
      (keyword? k) "nop"
      :else (*generate {:path (conj path k) :params params} v))))


(defn generate []
  (println "Generating into dist:")
  (fs/delete-dir "dist")
  (fs/mkdir "dist")
  (doseq [f (fs/glob "resources/public/*")]
    (println "assets: " (fs/base-name f))
    ((if (fs/directory? f) fs/copy-dir fs/copy)
     (.getPath f) (str "dist/" (fs/base-name f))))

  (*generate {:path [] :params {}} s/routes)
  (println "Done!"))

(defn -main [] (generate))

(comment
  (generate)
  )
