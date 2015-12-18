(ns site.component)

(defmacro defcomp [& x])

(defcomp widget [props & chld]
  [:div (style (&padding 10))
   [:ul (style (&unstyle))
    (for [x (range 10)]
      [:li (style (&unstyle))
       [:a (style (&link :opts))
        {:href (url "item" x)} x]])]])

(defcomp splash [{ts :typescale pl :color :as data}]
  (let [typescale (s-var :typescale (or ts :medium))
        palette (s-var :color (or pl :normal))]
    [:div (style (palette :text)
                 (&padding 4 0 4 0))
     [:div (style &container-fluid
                  (&center-block (h 4)))
      [:h1 (style (typescale :h1)
                  (&center-text)
                  [:em (palette :em)])
       (data :title)]
      [:h4 (style (typescale :h4)
                  {:padding-top (v 0.5)}
                  (&center-text))
       (data :title)]]]))
