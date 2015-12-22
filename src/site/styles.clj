(ns site.styles
  (:require
   [garden.core :refer [css]]
   [garden.units :refer [px px*]]))

(defn style [cnt] [:style {:type "text/css"} (css cnt)])


(def vh 18)

(def style-vars
  {:color {:main       {:text      {:color "#333356"}
                        :muted     {:color "#ddd"}

                        :selection {:color "#a23835"
                                    :border-color "#a23835"
                                    :cursor "pointer"
                                    :background-color "#f5f5f5"}

                        :muted-selection {:color "#888"
                                          :border-color "#a23835"
                                          :cursor "pointer"
                                          :background-color "#f5f5f5"}
                        :em   {:color "#a23835"}}
           :inverse    {:text {:color "white"
                               :background-color "#a23835"}
                        :muted {:background-color "#a23835"}
                        :em   {:color "white"
                               :background-color "#a23835"}}
           :inverse-ex {:text {:color "white"
                               :background-color "rgba(46,48,58,0.96)"}
                        :em   {:color "white"
                               :background-color "rgba(46,48,58,0.96)"}}}
   :typescale {:large {:h1 {:font-size "41px"  :font-weight "600"  :line-height (px* vh 3)}
                       :h2 {:font-size "24px"  :font-weight "bold" :line-height "1.5em"}
                       :h3 {:font-size  "21px" :font-weight "300"  :line-height "1.5em"}
                       :h4 {:font-size  "21px" :font-weight "600" :line-height (px* 1.5 vh)}
                       :p  {:font-size "18px"  :line-height (px* vh 1.5)}
                       :small {:font-size "16px"}}
               :medium {:h1 {:font-size "41px"  :line-height "1.3em" :font-weight "normal"}
                        :h2 {:font-size "24px"  :font-weight "bold"  :line-height "1.5em"}
                        :h3 {:font-size  "18px" :font-weight "bold"  :line-height "1.5em"}
                        :h4 {:font-size  "18px"}
                        :p  {:font-size "18px"}
                        :small {:font-size "16px"}}}})

(defn s-var [& ks] (get-in style-vars ks))

(defn undecorate []
  [:& {:text-decoration "none"}
   [:&:hover {:text-decoration "none"}]])

(defn box [& [t r b l]]
  (->> {:top t :right r :bottom b :left l}
       (filter second )
       (map (fn [[k v]] [k (px* v vh)]))
       (into {})))

(defn mbox [& xs]
  [:& {:margin (apply box xs)}])

(defn &mbox [& xs]
  [:& {:margin (apply box xs)}])

(defn &margin
  ([x]
   [:& {:margin (box x x x x)}])
  ([tb rl]
   [:& {:margin (box tb rl tb rl)}])
  ([t r b l]
   [:& {:margin (box t r b l)}]))

(defn pbox [& xs]
  [:& {:padding (apply box xs)}])

(defn &pbox [& xs]
  [:& {:padding (apply box xs)}])

(defn &padding
  ([x]   [:& {:padding (box x x x x)}])
  ([tb rl]   [:& {:padding (box tb rl tb rl)}])
  ([t r b l] [:& {:padding (box t r b l)}]))

(defn &border
  ([] [:& {:border {:color "#ddd" :style "solid" :width "1px"}}])
  ([side] [:& {:border (assoc {} side {:color "#ddd"
                                       :style "solid"
                                       :width "1px"})}]))

(def text-keys
  {:right {:text-align "right"}
   :center {:text-align "center"}
   :bold {:font-weight "bold"}})

(defn &text [& ks]
  [:& (reduce (fn [acc k] (merge acc (k text-keys))) {} ks)])


(defn &center-text [] [:& {:text-align "center"}])
(defn &middle [] [:& {:vertical-align "middle"}])

(defn &inline [] [:& {:dislay "inline-block"}])

(defn vh* [num] (px* vh num))

(defn &unstyle-links []
  [:& {:text-decoration "none"
       :color "inherit"}
   [:&:hover {:text-decoration "none"
              :color "inherit"}]])

(defn color [k]
  (s-var :color :main k :color))

(defn typescale [k]
  (s-var :typescale :medium k))

(defn &center-block
  ([] [:& {:margin {:left "auto" :right "auto"}}])
  ([width] [:& {:margin "0 auto" :max-width width}]))


(defn &font-scale [font-size line-height]
  [:& {:font-size (vh* font-size) :line-height (vh* line-height)}])
