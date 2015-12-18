(ns site.formats
  (:require [markdown.core :as md]
            [clj-yaml.core :as yaml]))

(defn markdown [str]
  (md/md-to-html-string str))

(defn load-text [f]
  (markdown (slurp (str "resources/" f))))

(defn yaml [f]
  (yaml/parse-string (slurp (str "resources/" f))))
