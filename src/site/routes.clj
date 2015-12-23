(ns site.routes
  (:require [clojure.string :as str]
            [site.data :as d]))

(println "SITE_URL" (System/getenv "SITE_URL"))
(defn url [& parts]
  (let [root (or (System/getenv "SITE_URL") "/")
        last (last parts)
        path (if (map? last) (butlast parts) parts)
        lang (if d/*lang* (str (name d/*lang*) "/") "")
        params (if (map? last) last {})
        hash (if (map? last) (:# last) nil)]
    (str root lang (str/join "/" path) ".html" (when hash (str "#" hash)))))


(defn asset-path [& parts]
  (let [root (or (System/getenv "SITE_URL") "/")
        last (last parts)
        path (if (map? last) (butlast parts) parts)
        params (if (map? last) last {})
        hash (if (map? last) (:# last) nil)]
    (str root (str/join "/" path) (when hash (str "#" hash)))))

(url "a" "b" {:a 1 :# "ups"})


