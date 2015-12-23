(ns site.routes
  (:require [clojure.string :as str]))

(defn url [& parts]
  (let [last (last parts)
        path (if (map? last) (butlast parts) parts)
        params (if (map? last) last {})
        hash (if (map? last) (:# last) nil)]
    (str (str/join path) ".html" (when hash (str "#" hash)))))

