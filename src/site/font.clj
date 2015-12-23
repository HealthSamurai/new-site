(ns site.font
  (:require [clojure.string :as str]
            [site.routes :refer [asset-path]]))

(def fontello
  {:mapper     "\\e800" 
   :foodtaster "\\e801"
   :aidbox     "\\e806"
   :fhirbase   "\\e802"
   :formstamp  "\\e803"
   :choice     "\\e804"
   :samurai    "\\e805"})

(defn fontello-icon-name [k] (str "icon-" (name (or k "ups"))))

(defn font-url [ext & [x]]
  (str "url('" (asset-path (str "fonts/fontello." ext)) "') format('" (or x ext) "')"))

(def font-face
  {:font-family "fontello"
   :src (str/join "," [(font-url "woff")
                       (font-url "eot" "embedded-opentype")
                       (font-url "woff")
                       (font-url "svg")
                       (font-url "ttf" "truetype")])})

(def garden-font
  (into [:.hs-icon {:font-family "fontello"
                    :font-style "normal"
                    :font-weight "normal"
                    :speak "none"
                    :display "inline-block"
                    :text-decoration "inherit"
                    :text-align "center"
                    :font-variant "normal"
                    :text-transform "none"
                    :-webkit-font-smoothing "antialiased"
                    :-moz-osx-font-smoothing "grayscale"}]
        (for [[k v] fontello] [(keyword (str "&." (fontello-icon-name k)))
                               [:&:before {:content (str "'" v "'")}]])))



(defn icon [x] [:i.hs-icon {:class (fontello-icon-name x)}])
