(ns site.styles
  (:require
   [garden.core :refer [css]]
   [garden.units :refer [px px*]]))

(defn style [cnt] [:style {:type "text/css"} (css cnt)])


(def vh 18)

(defn vh* [num] (px* vh num))

(def colors
  {:dark-blue "#194A6F"
   :gray "#ddd"
   :red "#a23835"
   :violet "#B93369"
   :light-blue "#E2F1FC"
   :blue "#42A3EE"
   :dark-red "#551513"
   :black "#333356"
   :dark-green "#0B5524"
   :alter-green "#64AE68"
   :green "#35A25A"
   :orange "#D56530"})

(defn clr [key]
  (get colors key))

(defn random-clr []
  (nth 
   (vals colors)
   (rand-int
    (count colors))))

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
                        :em   {:color "#a23835"
                               :background-color "white"}}
           :inverse    {:text {:color "white"
                               :background-color "#a23835"}
                        :muted {:background-color "#a23835"}
                        :em   {:color "white"
                               :background-color "#a23835"}}
           :inverse-ex {:text {:color "white"
                               :background-color "rgba(46,48,58,0.96)"}
                        :em   {:color "white"
                               :background-color "rgba(46,48,58,0.96)"}}}
   :typescale {:large {:h1 {:font-size "41px"  :font-weight "300"  :line-height (vh* 3)}
                       :h2 {:font-size "24px"  :font-weight "bold" :line-height "1.5em"}
                       :h3 {:font-size  "21px" :font-weight "300"  :line-height "1.5em"}
                       :h4 {:font-size  "21px" :font-weight "600" :line-height (vh* 1.5)}
                       :p  {:font-size "18px"  :line-height (px* vh 1.5)}
                       :small {:font-size "16px"}}
               :medium {:h1 {:font-size "41px"  :line-height (vh* 3) :font-weight "300"}
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
   :left {:text-align "left"}
   :nowrap {:white-space "nowrap"}
   600 {:font-weight 600}
   300 {:font-weight 300}
   500 {:font-weight 500}
   :bold {:font-weight "bold"}
   :h1 {:font-size "41px"  :font-weight "300"  :line-height (vh* 3)}
   :h2 {:font-size "24px"  :font-weight "bold" :line-height (vh* 3)}
   :h3 {:font-size  "21px" :font-weight "300"  :line-height (vh* 2)}
   :h4 {:font-size  "21px" :font-weight "300" :line-height (vh* 1.5)}
   :p  {:font-size "18px"  :line-height (px* vh 1.5)}
   :small {:font-size "16px"}})

(defn &text [& ks]
  [:& (reduce (fn [acc k] (merge acc (get text-keys k))) {} ks)])


(defn &center-text [] [:& {:text-align "center"}])
(defn &middle [] [:& {:vertical-align "middle"}])

(defn &inline [] [:& {:display "inline-block"}])


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

(defn svg [path]
  (slurp (str  "resources/public/imgs/" (name path) ".svg")))
