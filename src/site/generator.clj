(ns site.generator)

(comment
  (generate [] routes)
  (defn generate [{path :path params :params :as req} routes]
    (println path)
    (for [[k v] routes]
      (cond
        (= :GET k) (let [uri (str/join "/" path)]
                     (println "-> " uri ":")
                     (println (dispatch {:uri uri :request-method k})))
        (vector? k) (let [k (first k)
                          gen (get routes k)]
                      (doseq [pv (gen)]
                        (generate {:path (conj path pv)
                                   :params (assoc params k pv)}
                                  v)))
        (keyword? k) "nop"
        :else (generate {:path (conj path k) :params params} v)))))

