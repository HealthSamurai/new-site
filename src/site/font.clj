(ns site.font
  (:require [clojure.string :as str]))

(def fontello
  {:mapper     "\\e800" 
   :foodtaster "\\e801"
   :aidbox     "\\e806"
   :fhirbase   "\\e802"
   :formstamp  "\\e803"
   :choice     "\\e804"
   :samurai    "\\e805"})

(defn fontello-icon-name [k] (str "icon-" (name (or k "ups"))))

(def font-face
  {:font-family "fontello"
   :src (str/join "," ["url('./fonts/fontello.eot') format('embedded-opentype')"
                       "url('./fonts/fontello.woff') format('woff')"
                       "url('./fonts/fontello.ttf') format('truetype')"
                       "url('./fonts/fontello.svg') format('svg')"])})

(def garden-font
  (into [:.hs-icon {:font-family "fontello"
                    :font-style "normal"
                    :font-weight "normal"
                    :speak "none"
                    :display "inline-block"
                    :text-decoration "inherit"
                    :margin-right "0.2em"
                    :text-align "center"
                    :font-variant "normal"
                    :text-transform "none"
                    :line-height "1em"
                    :margin-left "0.2em"
                    :-webkit-font-smoothing "antialiased"
                    :-moz-osx-font-smoothing "grayscale"}]
        (for [[k v] fontello] [(keyword (str "&." (fontello-icon-name k)))
                               [:&:before {:content (str "'" v "'")}]])))



(defn icon [x] [:i.hs-icon {:class (fontello-icon-name x)}])
