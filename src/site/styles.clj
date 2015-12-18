(ns site.styles
  (:require
   [garden.core :refer [css]]
   [garden.units :refer [px px*]]))

(defn style [cnt] [:style {:type "text/css"} (css cnt)])


(def vh 18)

(def style-vars
  {:color {:main       {:text      {:color "#333356"}
                        :selection {:color "#a23835"
                                    :border-color "#a23835"
                                    :cursor "pointer"
                                    :background-color "#f5f5f5"}
                        :em   {:color "#a23835"}}
           :inverse    {:text {:color "white"
                               :background-color "#a23835"}
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

(defn pbox [& xs]
  [:& {:padding (apply box xs)}])

(defn &pbox [& xs]
  [:& {:padding (apply box xs)}])

(defn &center-block [width]
  [:& {:margin "0 auto" :max-width width}])

(defn &center-text [] [:& {:text-align "center"}])
